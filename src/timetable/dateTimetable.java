package timetable;
/**
 * Данный файл предназначен для получения данных о дате. Потому что в дальнейшем данные фукнции пригодятся не только
 * для формирования бэкэнд состовляющей календаря (вычисление даты, дня недели и т.д.), но и в формировании --
 * фронтенда.
 * В данном файле реализованы следующие методы:
 * numberMonth() -- необходим для вычисления номерного значения месяца с помощью библиотеки Calendare
 * nameMonth() -- необходим для вывода текстового наименования месяца, необходимость только эстетическая
 * quantityDaysInMonth() -- будет необходим для определения визуала календаря, т.е. сколько будет дней(квадратиков)
 * будет в календаре
 * functionForFebrary() -- метод необходимый только для quantityDaysInMonth(), потому что в високосные количество дней в
 * феврале различаются
 */

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class dateTimetable {
    public int numberMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }
     public String nameMonth(){
        LocalDate today = LocalDate.now();
        String nameMonth = today.getMonth()
                .getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
        return nameMonth;
     }

     public void quantityDaysInMonth(){
        int month = this.numberMonth();
        int quantityDaysInMonth = 0;
        switch (month){
            case 3, 5, 7, 9, 11 -> quantityDaysInMonth = 30;
            case 1, 4, 6, 8, 10, 12 -> quantityDaysInMonth = 31;
            case 2 -> functionForFebrary(quantityDaysInMonth);
        }
     }

     public int functionForFebrary(int quantityDaysInMonth){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if ((year % 400 == 0) || (year % 4 ==0) && (year % 100 != 0)){
            quantityDaysInMonth = 29;
            return quantityDaysInMonth;
         } else {
            quantityDaysInMonth = 28;
            return quantityDaysInMonth;
        }
     }

}
