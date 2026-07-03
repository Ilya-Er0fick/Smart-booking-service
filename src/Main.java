import gui.window.starWindow;
import timetable.readingDataBase;


public class Main {
    public static void main(String[] args) {
        starWindow app = new starWindow();
        readingDataBase test = new readingDataBase();
        app.setVisible(true);
        app.pack();
        test.readFile();
    }
}