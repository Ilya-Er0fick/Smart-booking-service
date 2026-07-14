package timetable;
/**
 * Хранилище всех бронирований.
 *
 * Методы:
 * add(booking) -- добавить
 * remove(bookingID) -- удалить по ID
 * getAll() -- все бронирования
 *   getByEmployee(employeeId) -- бронирования конкретного сотрудника
 *   hasBookingOnDay(y, m, d) -- есть ли бронь в этот день (для подсветки)
 *   getBookedHoursOnDay(y, m, d) -- занятые часы в день (для панели часов)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingStorage {

    private static final BookingStorage INSTANCE = new BookingStorage();
    private BookingStorage() {}
    public static BookingStorage getInstance() { return INSTANCE; }

    private final List<Booking> bookings = new ArrayList<>();

    public void add(Booking booking) {
        bookings.add(booking);
    }

    public boolean remove(int bookingId) {
        return bookings.removeIf(b -> b.getBookingId() == bookingId);
    }

    public List<Booking> getAll() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> getByEmployee(int employeeId) {
        return bookings.stream()
                .filter(b -> b.getEmployee().getId() == employeeId)
                .collect(Collectors.toList());
    }

    public boolean hasBookingOnDay(int year, int month, int day) {
        return bookings.stream().anyMatch(b ->
                b.getDateTime().getYear()       == year  &&
                        b.getDateTime().getMonthValue() == month &&
                        b.getDateTime().getDayOfMonth() == day);
    }

    public List<Integer> getBookedHoursOnDay(int year, int month, int day) {
        return bookings.stream()
                .filter(b ->
                        b.getDateTime().getYear()       == year  &&
                                b.getDateTime().getMonthValue() == month &&
                                b.getDateTime().getDayOfMonth() == day)
                .map(b -> b.getDateTime().getHour())
                .collect(Collectors.toList());
    }

}
