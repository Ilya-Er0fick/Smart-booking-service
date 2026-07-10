package gui.window;
/**
 * После стартового окна, если пользователь окажется клиентом, то перед ним будет возможность выбора/отменить свободного
 * сотрудника в календаре. Перед этим заранее, указав район, в котором он живет. Если данного района нет, то будет
 * вывод сообщения: "Пока что данный район не обслуживается"
 */

import timetable.dateTimetable;

import javax.swing.*;
import java.awt.*;
import timetable.algorithmTimetable;
import timetable.Employee;
import timetable.readingDataBase;

import java.sql.SQLException;
import java.util.List;

public class windowOpen extends JFrame{
    private int quantilyDate = 0;
    private JButton bookNewMeeting;
    private JButton cancelMeeting;

    public windowOpen(){
        dateTimetable dateTimetable = new dateTimetable();
        super(dateTimetable.nameMonth());

        bookNewMeeting = new JButton("Забронировать свободного сотрудника");
        cancelMeeting = new JButton("Отменить встречу");

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(bookNewMeeting);
        buttonsPanel.add(cancelMeeting);

        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookNewMeeting.addActionListener(e -> {
            readingDataBase db = new readingDataBase();
            algorithmTimetable algo = new algorithmTimetable();

            List<Employee> allEmployyes = null;
            try {
                allEmployyes = db.readingDataBase();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            List<String> districts = algo.getAllDistricts(allEmployyes);

            JComboBox<String> districtBox = new JComboBox<>(districts.toArray(new String[0]));
            String selectedDistrict = (String) districtBox.getSelectedItem();

            List<Employee> candidates = algo.findBestCandidates(allEmployyes, selectedDistrict);

            if (candidates.isEmpty()){
                JOptionPane.showMessageDialog(this,"К сожалению, данный район пока что не обслуживается");
            } else {
                Employee best = candidates.get(0);
                JOptionPane.showMessageDialog(this,
                        "Назначен сотрудник: " + best.getLastname() + " " + best.getFirstname() + " " + best.getMidlename() +
                        "\nРейтинг: " + best.getRating() +
                        "\nРайон: " + best.getDistrict());
            }
        });

        cancelMeeting.addActionListener(e ->{
            JOptionPane.showMessageDialog(this, "Встреча отменена");

        });


    }
}
