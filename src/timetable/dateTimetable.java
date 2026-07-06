package timetable;

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
