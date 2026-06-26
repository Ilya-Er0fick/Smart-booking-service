package gui;

import timetable.algorithmTimetable;
import javax.swing.*;
import java.awt.*;

public class windowOpen extends JFrame{
    private int quantilyDate = 0;
    private JButton bookNewMeeting;
    private JButton cancelMeeting;

    public windowOpen(){
        algorithmTimetable algorithmTimetable = new algorithmTimetable();
        super(algorithmTimetable.nameMonth());

        bookNewMeeting = new JButton("Забронировать свободного сотрудника");
        cancelMeeting = new JButton("Отменить встречу");

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(bookNewMeeting);
        buttonsPanel.add(cancelMeeting);

        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
