package timetable;

/**
 * Booking -- это объект одного бронирования, хранящий уникальный ID бронирования, дату и час встречи, назначенного
 * сотрудника и район клиента
 *
 * В дальнейшем может получить стать рудиментарным, если добавить в БД таблицу booking
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private static int nextId = 1;
    private final int bookingId;
    private final LocalDateTime dateTime;
    private final Employee employee;
    private final String clinetDistrict;


    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:00");

    public Booking(LocalDateTime dateTime, Employee  employee, String clinetDistrict){
        this.bookingId = nextId++;
        this.dateTime = dateTime;
        this.employee = employee;
        this.clinetDistrict = clinetDistrict;
    }

    public int getBookingId() {
        return bookingId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getClientDistrict() {
        return clinetDistrict;
    }

    public String getDateTimeStr() {
        return dateTime.format(FMT);
    }

    public String getEmployeeFullName(){
        return employee.getLastname() + " " + employee.getFirstname() + " " + employee.getMidlename();
    }
}
