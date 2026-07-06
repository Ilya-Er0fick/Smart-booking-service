package timetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class readingDataBase {
    public void readingDataBase() throws SQLException  {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "1234";

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT \"ID\", \"Lastname\", \"Firstname\", \"Midlename\", \"Rating\", \"District\", \"MaxClients\" FROM example_data_base")) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String lastname = rs.getString("Lastname");
                String firstname = rs.getString("Firstname");
                String midlename = rs.getString("Midlename");
                int rating = rs.getInt("Rating");
                String district = rs.getString("District");
                int maxclients = rs.getInt("MaxClients");

                System.out.printf("ID: %d | ФИО: %s %s %s | Рейтинг: %d | Район: %s | Макс. клиентов: %d%n",
                        id, lastname, firstname, midlename, rating, district, maxclients);
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при чтении данных из PostgreSQL:");
            e.printStackTrace();
        }

    }
}
