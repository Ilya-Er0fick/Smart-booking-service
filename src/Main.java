import gui.window.starWindow;
import timetable.readingDataBase;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        starWindow app = new starWindow();
        readingDataBase test = new readingDataBase();
        app.setVisible(true);
        app.pack();
        try {
            test.readingDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            test.quantityLinesInDataBase();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}