package gui.window;
/**
 * Окно сотрудника банка.
 * Пользователь выбирает своё имя из выпадающего списка сотрудников, вводит пароль и при успешном вводе
 * у него открывается окно с его персональными данными
 */

import timetable.Booking;
import timetable.BookingStorage;
import timetable.Employee;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class windowOpenAdmin extends JFrame{
    private final Employee adminEmployee;
    private List<Booking> currentView = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    private static final Color ROW_ODD    = new Color(250, 250, 252);
    private static final Color ROW_EVEN   = new Color(232, 240, 255);
    private static final Color ROW_SELECT = new Color(255, 215, 80);
    private static final Color HEADER_BG  = new Color(45, 75, 140);

    //Конструктор, принимающий сотрудника, который вошел
    public windowOpenAdmin(Employee adminEmployee){
        super("Расписание сотрудника — " + adminEmployee.getLastname()
                + " " + adminEmployee.getFirstname());
        this.adminEmployee = adminEmployee;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 460));

        buildUI();
        refreshTable();

        pack();
        setLocationRelativeTo(null);
    }

    //Интерфейс
    private void buildUI(){
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel headerPanel = new JPanel(new BorderLayout(0, 4));
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel nameLabel = new JLabel(
                "Добро пожаловать, " + adminEmployee.getEmployeeFullName(adminEmployee));
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setForeground(Color.WHITE);

        JLabel infoLabel = new JLabel(
                "Район: " + adminEmployee.getDistrict()
                        + "   |   Макс. клиентов: " + adminEmployee.getMaxclients()
                        + "   |   Рейтинг: " + adminEmployee.getRating());
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(200, 220, 255));

        headerPanel.add(nameLabel, BorderLayout.NORTH);
        headerPanel.add(infoLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"№", "Дата и время", "Район клиента", "ID брони"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 34));

        int[] widths = {40, 200, 200, 80};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(
                        t, val, sel, focus, row, col);
                if (sel) {
                    c.setBackground(ROW_SELECT);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    c.setForeground(new Color(30, 30, 30));
                }
                ((JLabel) c).setHorizontalAlignment(
                        (col == 0 || col == 3) ? SwingConstants.CENTER : SwingConstants.LEFT);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(170, 185, 215)));
        add(scroll, BorderLayout.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(100, 100, 110));

        JButton cancelBtn = new JButton("Отменить выбранную встречу");
        cancelBtn.setBackground(new Color(195, 65, 65));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        JButton refreshBtn = new JButton("Обновить");
        refreshBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        cancelBtn.addActionListener(e  -> handleCancelSelected());
        refreshBtn.addActionListener(e -> refreshTable());

        JPanel southPanel = new JPanel(new BorderLayout(10, 4));
        southPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btns.add(refreshBtn);
        btns.add(cancelBtn);
        southPanel.add(statusLabel, BorderLayout.WEST);
        southPanel.add(btns, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
    }

    //Обновления таблицы, демонстрируется только бронирования этого сотрудника
    private void refreshTable() {
        tableModel.setRowCount(0);
        currentView.clear();

        List<Booking> mine = BookingStorage.getInstance()
                .getByEmployee(adminEmployee.getId());
        //происходит сортировка по дате
        mine.sort((a, b) -> a.getDateTime().compareTo(b.getDateTime()));

        int rowNum = 1;
        for (Booking b : mine) {
            tableModel.addRow(new Object[]{
                    rowNum++,
                    b.getDateTimeStr(),
                    b.getClientDistrict(),
                    "#" + b.getBookingId()
            });
            currentView.add(b);
        }

        int total = BookingStorage.getInstance().getAll().size();
        statusLabel.setText("Ваших встреч: " + mine.size()
                + "   |   Всего в системе: " + total);
    }

    //Отмена выбранной строки
    private void handleCancelSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Выберите строку в таблице, которую хотите отменить.",
                    "Ничего не выбрано", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Booking toCancel = currentView.get(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Отменить встречу?\n\n"
                        + "Дата и время:  " + toCancel.getDateTimeStr() + "\n"
                        + "Район клиента: " + toCancel.getClientDistrict(),
                "Подтвердите отмену",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean removed = BookingStorage.getInstance().remove(toCancel.getBookingId());
        if (removed) {
            JOptionPane.showMessageDialog(this,
                    "Встреча отменена.\nВремя: " + toCancel.getDateTimeStr(),
                    "Отменено", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Не удалось найти бронирование. Обновите список.",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
