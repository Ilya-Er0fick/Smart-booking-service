package timetable;
/**
 * Данный файл необходим для чтения информации из sql базы данных, в дальнейшем полученная информация переносится
 * в использование файла bufferBetwenDataBaseAndCode.java с помощью методов через массивы.
 * Здесь есть следующие методы:
 * readingDataBase() -- производит чтение базы данных
 * quantityLinesInDataBase() -- производит вычисление длины, т.е. количества строк, в БД, эта информация будет
 * использована в bufferBetwenDataBaseAndCode.java
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class readingDataBase {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "postgres";
    String password = "1234";

    public void readingDataBase() throws SQLException  {

        /**
         * Первый try-with-resources нам необходим, чтобы изъять данные для дальнейшей работы с ними потому что в
         * дальнейших методах, как например, в alotithmTimetable необходимо иметь представление о количество строк,
         * потому что это влияет на раскрывающееся меню с сотрудниками.
         */
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

                /*System.out.printf("ID: %d | ФИО: %s %s %s | Рейтинг: %d | Район: %s | Макс. клиентов: %d%n",
                        id, lastname, firstname, midlename, rating, district, maxclients);*/
            }
        }catch(SQLException e) {
            System.err.println("Ошибка при чтении данных из PostgreSQL:");
            e.printStackTrace();
        }
    }

    public void quantityLinesInDataBase() throws SQLException {
        /**
         * Второй try-with-resources нам необходим, чтобы вычилить количество строк в самой БД, по аналогичной логике с
         * первым try-with-resources, но только данные будут храниться в массиве для комфортной работы
         */
        try (Connection con = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM example_data_base");
             ResultSet rs = stmt.executeQuery()){

            if (rs.next()){
                long length = rs.getLong(1);
                //System.out.println("Длина БД: " + length);
            }

        } catch (SQLException e){
            System.err.println("Ошибка при чтении данных из PostgreSQL:");
            e.printStackTrace();
        }
    }
}
