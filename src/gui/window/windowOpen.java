package gui.window;
/**
 * После стартового окна, если пользователь окажется клиентом, то перед ним будет возможность выбора/отменить свободного
 * сотрудника в календаре. Перед этим заранее, указав район, в котором он живет. Если данного района нет, то будет
 * вывод сообщения: "Пока что данный район не обслуживается"
 */

import timetable.dateTimetable;

import javax.swing.*;
import java.awt.*;

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
    }
}
