package Utils;

import com.example.wemove.Notification;

import java.util.Comparator;
import java.util.Date;

public class NotificationComparator implements Comparator<Notification> {
    @Override
    public int compare(Notification o1, Notification o2) {
        Date d1 = new Date (o1.getDate());
        Date d2 = new Date (o2.getDate());
        return d1.compareTo(d2);
    }
}
