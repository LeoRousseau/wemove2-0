package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator{

        public int calculateAge(Date birthDate) {
            // Ne marche pas
            Date currentDate = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            int d1 = Integer.parseInt(formatter.format(birthDate));
            int d2 = Integer.parseInt(formatter.format(currentDate));
            int age = (d2 - d1) / 10000;
            return age;
        }

}
