package Utils;

import com.example.wemove.Event;
import com.example.wemove.R;
import com.example.wemove.User;
import com.example.wemove.WeMoveDB;

import java.util.HashMap;

public class AccessData {
    public static SITable table = new SITable();
    public static MenuItems menuItems = new MenuItems();
    public static User currentUser = new User();
    public static Event currentEvent = new Event();
    public static WeMoveDB db = new WeMoveDB();
    public static AgeCalculator ageCalculator = new AgeCalculator();
}

