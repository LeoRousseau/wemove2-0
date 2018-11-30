package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator{

        public int calculateAge(Date birthDate) {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();

            dob.set(birthDate.getYear(),birthDate.getMonth(),birthDate.getDay());
            if (dob.after(now)) {
                return 0;
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            int age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }

            return age;
        }

}
