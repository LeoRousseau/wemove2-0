package Utils;

import com.example.wemove.R;

import java.util.ArrayList;
import java.util.List;

public class MenuItems extends ArrayList<MenuItem> {
    public MenuItems() {
        this.add(new MenuItem("Mon profil",R.drawable.ic_account_circle_black_24dp));
        this.add(new MenuItem("Mes évènements", R.drawable.ic_event_black_24dp));
        this.add(new MenuItem("Mes sports",R.drawable.sport));
        this.add(new MenuItem("Mes groupes",R.drawable.ic_group_black_24dp));
        this.add(new MenuItem("Paramètres",R.drawable.ic_settings_black_24dp));
        this.add(new MenuItem("Crédit",R.drawable.ic_star_border_black_24dp));
        this.add(new MenuItem("Déconnexion",R.drawable.ic_exit_to_app_black_24dp));
    }
}
