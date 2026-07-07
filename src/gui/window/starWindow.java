package gui.window;
/**
 * В данном файле будет создаваться стартовое окно с возможностью выбора, кем является юзер: клиентом или сотрудником
 * компании.
 * В зависимости от выбора либо будет запускаться файл windowOpen для обычных пользователей, либо windowOpenAdmin для
 * потенциальных админов или сотрудников компании, имеющих доступ
 */

import gui.window.windowOpen;
import gui.window.windowOpenAdmin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class starWindow extends JFrame {
    private int chooseAdmine = 0;
    private int chooseClien = 0;
    private JButton iAmAdministrator;
    private JButton iAmClient;
    private volatile boolean isStopped = false;


    public starWindow(){

        super("Добро пожаловать! Вы сотрудник или клиент?");

        setBounds(200, 200, 200, 200);

        iAmAdministrator = new JButton("Сотрудник");
        iAmClient = new JButton("Клиент");

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonsPanel.add(iAmAdministrator);
        buttonsPanel.add(iAmClient);

        add(buttonsPanel, BorderLayout.CENTER);
        initListeners();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


    }

    private void initListeners(){
       iAmAdministrator.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               chooseAdmine = 1;
               windowOpenAdmin app = new windowOpenAdmin();
               app.setVisible(true);
               app.pack();
               isStopped = true;
           }
       });
       iAmClient.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               chooseClien = 1;
               windowOpen app = new windowOpen();
               app.setVisible(true);
               app.pack();
               isStopped = true;
           }
       });
    }
}
