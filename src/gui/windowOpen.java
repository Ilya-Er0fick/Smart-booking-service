package gui;

import javax.swing.*;
import java.awt.*;

public class windowOpen extends JFrame{
    private int quantilyDate = 0;
    private JLabel countLabel;
    private JButton bookNewMeeting;
    private JButton cancelMeeting;

    public windowOpen(){
        super("SmartBooking");

        countLabel = new JLabel("Book meeting:" + quantilyDate);
        bookNewMeeting = new JButton("Booking free employee");
        cancelMeeting = new JButton("Cancel meeting");

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(countLabel, BorderLayout.NORTH);

        buttonsPanel.add(bookNewMeeting);
        buttonsPanel.add(cancelMeeting);

        add(buttonsPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
