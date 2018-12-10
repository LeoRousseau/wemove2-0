package Utils;

import android.os.Debug;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AgeCalculator{

        public int calculateAge(Date birthDate) {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();

            dob.set(birthDate.getYear(),birthDate.getMonth(),birthDate.getDate());
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

    public long getDifferenceDays(Date d1) {
        Calendar d = Calendar.getInstance();
        Date d2 = new Date(d.get(Calendar.YEAR)-1900,d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
