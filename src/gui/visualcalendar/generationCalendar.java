package gui.visualcalendar;

/**
 *   getFirstDayOffset(year, month)              — смещение первого дня от Пн (0-6)
 *   getDaysInMonth(year, month)                 — количество дней в месяце
 *   hasBooksOnDay(year, month, day)             — есть ли бронь в этот день (подсветка)
 *   getBookedHoursOnDay(year, month, day)       — список занятых часов в день
 *   isBooked(year, month, day, hour)            — занят ли конкретный час
 *   addBooking(year, month, day, hour)          — добавить бронирование
 *   removeBooking(year, month, day, hour)       — удалить бронирование
 *   getAllBookings()                             — все бронирования (для кнопки "Отменить")
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class generationCalendar {
    private final List<LocalDateTime> bookedSlots = new ArrayList<>();

    public int getDaysInMonth(int year, int month){
        return YearMonth.of(year, month).lengthOfMonth();
    }

    public int getFirstDayOffset(int year, int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        return firstDay.getDayOfWeek().getValue() - 1;
    }

    public void addBooking(int year, int month, int day, int hour){
        LocalDateTime slot = LocalDateTime.of(year, month, day, hour, 0);
        if (!bookedSlots.contains(slot)){
            bookedSlots.add(slot);
        }
    }

    public boolean isBooked(int year, int month, int day, int hour){
        LocalDateTime slot = LocalDateTime.of(year, month, day, hour, 0);
        return bookedSlots.contains(slot);
    }

    public void removeBooking(int year, int month, int day, int hour) {
        LocalDateTime slot = LocalDateTime.of(year, month, day, hour, 0);
        bookedSlots.remove(slot);
    }

    public boolean hasBooksOnDay(int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        for (LocalDateTime slot : bookedSlots){
            if (slot.toLocalDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    public List<Integer> getBookedHoursOnDay(int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        List<Integer> hours = new ArrayList<>();
        for (LocalDateTime slot : bookedSlots){
            if (slot.toLocalDate().equals(date)){
                hours.add(slot.getHour());
            }
        }
        return hours;
    }

    public List<LocalDateTime> getAllBookings(){
        return new ArrayList<>(bookedSlots);
    }
}
