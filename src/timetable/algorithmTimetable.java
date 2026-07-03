package timetable;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class algorithmTimetable {
    public void numberMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
    }
     public String nameMonth(){
        LocalDate today = LocalDate.now();
        String nameMonth = today.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("ru"));
        return nameMonth;
     }


}
