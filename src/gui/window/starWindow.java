package gui.window;

/**
 * starWindow — стартовое окно выбора роли.
 * Кнопка "Клиент":
 *   диалог выбора района
 * Кнопка "Сотрудник":
 *   диалог: выбор сотрудника из списка + поле пароля
 *   проверка пароля (пароль по умолчанию = ID сотрудника, например "4020")
 */

import timetable.Employee;
import timetable.algorithmTimetable;
import timetable.readingDataBase;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class starWindow extends JFrame {

    private JButton iAmAdministrator;
    private JButton iAmClient;

    public starWindow() {
        super("Добро пожаловать! Вы сотрудник или клиент?");
        setBounds(200, 200, 300, 140);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        iAmAdministrator = new JButton("Сотрудник");
        iAmClient        = new JButton("Клиент");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(iAmAdministrator);
        buttonsPanel.add(iAmClient);
        add(buttonsPanel, BorderLayout.CENTER);

        initListeners();
    }

    private void initListeners() {

        iAmClient.addActionListener(e -> {
            readingDataBase db = new readingDataBase();
            algorithmTimetable algo = new algorithmTimetable();
            List<Employee> employees;
            try {
                employees = db.readingDataBase();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Не удалось подключиться к базе данных:\n" + ex.getMessage(),
                        "Ошибка БД", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> districts = algo.getAllDistricts(employees);
            if (districts.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "База данных пуста. Обратитесь к администратору.",
                        "Нет данных", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<String> districtBox =
                    new JComboBox<>(districts.toArray(new String[0]));

            int res = JOptionPane.showConfirmDialog(this,
                    districtBox,
                    "Выберите ваш район",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (res != JOptionPane.OK_OPTION) return;

            String district = (String) districtBox.getSelectedItem();

            if (algo.findBestCandidates(employees, district).isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Пока что данный район не обслуживается.",
                        "Район недоступен", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            windowOpen clientWin = new windowOpen(district);
            clientWin.setVisible(true);
        });

        iAmAdministrator.addActionListener(e -> {
            readingDataBase db = new readingDataBase();
            List<Employee> employees;
            try {
                employees = db.readingDataBase();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Не удалось подключиться к базе данных:\n" + ex.getMessage(),
                        "Ошибка БД", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "В базе данных нет сотрудников.",
                        "Нет данных", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JComboBox<Employee> empBox =
                    new JComboBox<>(employees.toArray(new Employee[0]));
            empBox.setPreferredSize(new Dimension(260, 28));

            JPasswordField passwordField = new JPasswordField(20);

            JPanel loginPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill   = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 0;
            loginPanel.add(new JLabel("Выберите сотрудника:"), gbc);
            gbc.gridx = 1;
            loginPanel.add(empBox, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            loginPanel.add(new JLabel("Пароль:"), gbc);
            gbc.gridx = 1;
            loginPanel.add(passwordField, gbc);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            loginPanel.add(new JLabel(
                    "<html><font color='gray' size='2'>" +
                            "Подсказка: пароль = ID сотрудника (например 4020)" +
                            "</font></html>"), gbc);

            int res = JOptionPane.showConfirmDialog(this,
                    loginPanel,
                    "Вход для сотрудника",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (res != JOptionPane.OK_OPTION) return;

            Employee selected = (Employee) empBox.getSelectedItem();
            String entered    = new String(passwordField.getPassword());

            if (!entered.equals(String.valueOf(selected.getId()))) {
                JOptionPane.showMessageDialog(this,
                        "Неверный пароль. Попробуйте ещё раз.",
                        "Ошибка входа", JOptionPane.ERROR_MESSAGE);
                return;
            }

            windowOpenAdmin adminWin = new windowOpenAdmin(selected);
            adminWin.setVisible(true);
        });
    }
}