package gui;

import javax.swing.*;
import java.awt.*;

public class starWindow extends JFrame {
    private int chooseAdmine = 0;
    private int chooseClien = 0;
    private JButton iAmAdministrator;
    private JButton iAmClient;

    public starWindow(){
        super("Добро пожаловать! Вы сотрудник или клиент?");

        setBounds(200, 200, 200, 200);

        iAmAdministrator = new JButton("Сотрудник");
        iAmClient = new JButton("Клиент");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonsPanel.add(iAmAdministrator);
        buttonsPanel.add(iAmClient);

        add(buttonsPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
