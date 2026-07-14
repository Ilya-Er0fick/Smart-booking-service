package gui.window;

import gui.visualcalendar.generationCalendar;
import timetable.Booking;
import timetable.BookingStorage;
import timetable.Employee;
import timetable.algorithmTimetable;
import timetable.readingDataBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class windowOpen extends JFrame {

    private int currentYear;
    private int currentMonth;
    private int selectedDay  = -1;
    private int selectedHour = -1;

    private final String clientDistrict;
    private final generationCalendar calendar = new generationCalendar();

    private JLabel monthLabel;
    private JPanel calendarGrid;
    private JPanel timePanel;
    private JLabel selectedDayLabel;

    private static final int HOUR_START = 9;
    private static final int HOUR_END   = 18;

    private static final Color COLOR_DAY_NORMAL   = new Color(245, 245, 245);
    private static final Color COLOR_DAY_BOOKED   = new Color(173, 216, 230);
    private static final Color COLOR_DAY_SELECTED = new Color(255, 220, 100);
    private static final Color COLOR_DAY_TODAY    = new Color(200, 230, 200);
    private static final Color COLOR_HOUR_FREE    = new Color(220, 240, 220);
    private static final Color COLOR_HOUR_BUSY    = new Color(250, 200, 200);
    private static final Color COLOR_HOUR_CHOSEN  = new Color(255, 200, 50);

    public windowOpen(String clientDistrict) {
        super("Календарь бронирования");
        this.clientDistrict = clientDistrict;

        LocalDate today = LocalDate.now();
        this.currentYear  = today.getYear();
        this.currentMonth = today.getMonthValue();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 480));
        buildUI();
        rebuildCalendarGrid();
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        setLayout(new BorderLayout(8, 8));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 4));
        JButton prevBtn = new JButton("◀");
        JButton nextBtn = new JButton("▶");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        monthLabel.setPreferredSize(new Dimension(180, 28));

        prevBtn.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 1) { currentMonth = 12; currentYear--; }
            selectedDay = -1; selectedHour = -1;
            rebuildCalendarGrid();
            rebuildTimePanel();
        });
        nextBtn.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 12) { currentMonth = 1; currentYear++; }
            selectedDay = -1; selectedHour = -1;
            rebuildCalendarGrid();
            rebuildTimePanel();
        });

        navPanel.add(prevBtn);
        navPanel.add(monthLabel);
        navPanel.add(nextBtn);
        add(navPanel, BorderLayout.NORTH);

        calendarGrid = new JPanel();
        add(calendarGrid, BorderLayout.CENTER);

        timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBorder(BorderFactory.createTitledBorder("Время"));
        timePanel.setPreferredSize(new Dimension(130, 400));

        selectedDayLabel = new JLabel("Выберите день");
        selectedDayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectedDayLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        timePanel.add(selectedDayLabel);
        timePanel.add(Box.createVerticalStrut(8));

        add(timePanel, BorderLayout.EAST);

        JButton bookBtn   = new JButton("Забронировать");
        JButton cancelBtn = new JButton("Отменить встречу");

        bookBtn.setBackground(new Color(100, 180, 100));
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("SansSerif", Font.BOLD, 13));

        cancelBtn.setBackground(new Color(200, 100, 100));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 13));

        bookBtn.addActionListener(e -> handleBooking());
        cancelBtn.addActionListener(e -> handleCancel());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        southPanel.add(bookBtn);
        southPanel.add(cancelBtn);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void rebuildCalendarGrid() {
        String monthName = LocalDate.of(currentYear, currentMonth, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        monthLabel.setText(monthName + " " + currentYear);

        calendarGrid.removeAll();
        calendarGrid.setLayout(new GridLayout(0, 7, 4, 4));

        String[] dayNames = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
        for (String name : dayNames) {
            JLabel lbl = new JLabel(name, SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            lbl.setForeground(new Color(80, 80, 80));
            calendarGrid.add(lbl);
        }

        int offset      = calendar.getFirstDayOffset(currentYear, currentMonth);
        int daysInMonth = calendar.getDaysInMonth(currentYear, currentMonth);
        LocalDate today = LocalDate.now();

        for (int i = 0; i < offset; i++) {
            calendarGrid.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            final int d = day;
            JButton btn = new JButton(String.valueOf(day));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            btn.setOpaque(true);

            if (d == selectedDay) {
                btn.setBackground(COLOR_DAY_SELECTED);
            } else if (calendar.hasBooksOnDay(currentYear, currentMonth, d)) {
                btn.setBackground(COLOR_DAY_BOOKED);
                btn.setToolTipText("Есть бронирование");
            } else if (today.getYear() == currentYear
                    && today.getMonthValue() == currentMonth
                    && today.getDayOfMonth() == d) {
                btn.setBackground(COLOR_DAY_TODAY);
                btn.setToolTipText("Сегодня");
            } else {
                btn.setBackground(COLOR_DAY_NORMAL);
            }

            btn.addActionListener(e -> {
                selectedDay  = d;
                selectedHour = -1;
                rebuildCalendarGrid();
                rebuildTimePanel();
            });

            calendarGrid.add(btn);
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private void rebuildTimePanel() {
        timePanel.removeAll();
        timePanel.add(selectedDayLabel);
        timePanel.add(Box.createVerticalStrut(8));

        if (selectedDay == -1) {
            selectedDayLabel.setText("Выберите день");
            timePanel.revalidate();
            timePanel.repaint();
            return;
        }

        String dayStr = selectedDay + " " +
                LocalDate.of(currentYear, currentMonth, selectedDay)
                        .getMonth()
                        .getDisplayName(TextStyle.FULL, new Locale("ru"));
        selectedDayLabel.setText(dayStr);

        List<Integer> bookedHours =
                calendar.getBookedHoursOnDay(currentYear, currentMonth, selectedDay);

        for (int h = HOUR_START; h <= HOUR_END; h++) {
            final int hour = h;
            boolean busy = bookedHours.contains(h);

            JButton hBtn = new JButton(String.format("%02d:00", h));
            hBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
            hBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            hBtn.setFocusPainted(false);
            hBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
            hBtn.setOpaque(true);

            if (busy) {
                hBtn.setBackground(COLOR_HOUR_BUSY);
                hBtn.setToolTipText("Занято");
                hBtn.setEnabled(false);
            } else if (hour == selectedHour) {
                hBtn.setBackground(COLOR_HOUR_CHOSEN);
            } else {
                hBtn.setBackground(COLOR_HOUR_FREE);
            }

            hBtn.addActionListener(e -> {
                if (!busy) {
                    selectedHour = hour;
                    rebuildTimePanel();
                }
            });

            timePanel.add(hBtn);
            timePanel.add(Box.createVerticalStrut(3));
        }

        timePanel.revalidate();
        timePanel.repaint();
    }

    private void handleBooking() {
        if (selectedDay == -1) {
            JOptionPane.showMessageDialog(this,
                    "Сначала выберите день в календаре.",
                    "Выбор даты", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedHour == -1) {
            JOptionPane.showMessageDialog(this,
                    "Выберите время справа.",
                    "Выбор времени", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (calendar.isBooked(currentYear, currentMonth, selectedDay, selectedHour)) {
            JOptionPane.showMessageDialog(this,
                    "Этот час уже занят. Выберите другое время.",
                    "Занято", JOptionPane.WARNING_MESSAGE);
            return;
        }

        readingDataBase db = new readingDataBase();
        algorithmTimetable algo = new algorithmTimetable();
        List<Employee> allEmployees;
        try {
            allEmployees = db.readingDataBase();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка подключения к базе данных:\n" + ex.getMessage(),
                    "Ошибка БД", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee best = algo.getBestEmployee(allEmployees, clientDistrict);
        if (best == null) {
            JOptionPane.showMessageDialog(this,
                    "Нет доступных сотрудников в вашем районе (" + clientDistrict + ").",
                    "Нет кандидатов", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        LocalDateTime meetingTime =
                LocalDateTime.of(currentYear, currentMonth, selectedDay, selectedHour, 0);
        Booking booking = new Booking(meetingTime, best, clientDistrict);
        BookingStorage.getInstance().add(booking);

        String dateStr = selectedDay + "." +
                String.format("%02d", currentMonth) + "." + currentYear;
        JOptionPane.showMessageDialog(this,
                "Бронирование успешно!\n\n" +
                        "Дата:      " + dateStr + "\n" +
                        "Время:     " + String.format("%02d:00", selectedHour) + "\n" +
                        "Сотрудник: " + best.getLastname() + " " +
                        best.getFirstname() + " " +
                        best.getMidlename() + "\n" +
                        "Рейтинг:   " + best.getRating() + "\n" +
                        "Район:     " + best.getDistrict(),
                "Забронировано", JOptionPane.INFORMATION_MESSAGE);

        selectedHour = -1;
        rebuildCalendarGrid();
        rebuildTimePanel();
    }

    private void handleCancel() {
        List<LocalDateTime> all = calendar.getAllBookings();
        if (all.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "У вас нет активных бронирований.",
                    "Отмена", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = new String[all.size()];
        for (int i = 0; i < all.size(); i++) {
            LocalDateTime slot = all.get(i);
            options[i] = slot.getDayOfMonth() + "." +
                    String.format("%02d", slot.getMonthValue()) + "." +
                    slot.getYear() + "  " +
                    String.format("%02d:00", slot.getHour());
        }

        String chosen = (String) JOptionPane.showInputDialog(
                this,
                "Выберите бронирование для отмены:",
                "Отмена встречи",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (chosen == null) return;

        int idx = java.util.Arrays.asList(options).indexOf(chosen);
        LocalDateTime toRemove = all.get(idx);
        calendar.removeBooking(
                toRemove.getYear(),
                toRemove.getMonthValue(),
                toRemove.getDayOfMonth(),
                toRemove.getHour()
        );

        JOptionPane.showMessageDialog(this,
                "Бронирование " + chosen + " отменено.",
                "Отменено", JOptionPane.INFORMATION_MESSAGE);

        selectedHour = -1;
        rebuildCalendarGrid();
        rebuildTimePanel();
    }
}