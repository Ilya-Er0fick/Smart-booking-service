package timetable;
/*В дальнейшем планируется переход на SQL.
* Пока что на первых этапах развития проекта будут использоваться
* таблицы в формате .csv, из-за удобности и популярности данного формата,
* но они не подходят для полноценных баз данных
**/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readingDataBase {

    public void readFile(){
        try (Scanner scanner = new Scanner(new File("/home/ilyaerofick/smart-booking-service/database/example-data-base.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                for (String value : values) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
