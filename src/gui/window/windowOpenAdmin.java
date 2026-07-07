package gui.window;
/**
 * После стартового окна, если пользователь окажется сотрудником/админом, он будет должен ввести свой логин и пароль, то
 * перед ним будет возможность наблюдать все свободное и занятое время другими сотрудникам. Админ может назначать или
 * убирать сотрудников на данном времени.
 */

import javax.swing.*;

public class windowOpenAdmin extends JFrame{


    public windowOpenAdmin(){
        super("В разработке");
        setBounds(100, 100, 200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
