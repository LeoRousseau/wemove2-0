package profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wemove.Home;
import com.example.wemove.R;
import com.example.wemove.Sport;

import java.util.ArrayList;
import java.util.List;

import Utils.AccessData;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private List<Sport> sportItems;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private boolean isOwner;
    private boolean isFirst;

    private LinearLayout selecLL;

    private CircleImageView image_profile;
    private TextView name_text;
    private TextView age_text;
    private RatingBar user_ratingBar;
    private TextView number_activities;
    private TextView number_sports;
    private TextView bio_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        isOwner=true;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init Content Variables
        initContentVariables();

        //Get Data from DataBase
        getData();

        //Set layout with screen size
        setLayout();

        // Sport
        //setSports();
        LinearLayout ll = (LinearLayout) findViewById(R.id.layoutselected);
        recyclerView = (RecyclerView) findViewById(R.id.sportRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        HorizontalSportAdapter  horizontalSportAdapter = new HorizontalSportAdapter(sportItems,this,ll);
        recyclerView.setAdapter(horizontalSportAdapter);

        // Editing
        RelativeLayout editingLayout = (RelativeLayout)findViewById(R.id.editingLayout);
        if (isOwner)
            editingLayout.setVisibility(View.VISIBLE);
        else
            editingLayout.setVisibility(View.GONE);


    }


    public void initContentVariables () {

        selecLL = (LinearLayout) findViewById(R.id.layoutselectedint);
        image_profile = (CircleImageView)findViewById(R.id.profile_image);
        name_text = (TextView) findViewById(R.id.nameText);
        age_text = (TextView) findViewById(R.id.ageText);
        user_ratingBar = (RatingBar) findViewById(R.id.userRatingBar);
        number_activities = (TextView) findViewById(R.id.number_activities);
        number_sports = (TextView) findViewById(R.id.number_sports);
        bio_content = (TextView) findViewById(R.id.bio_content);

        sportItems = new ArrayList<>();
    }

    public void setLayout () {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;

        if (dpWidth>500) {
            selecLL.setOrientation(LinearLayout.HORIZONTAL);
        }

    }

    public void setSports () {
        sportItems.add(new Sport("Football","Intermédiaire","Détente",(float)3.5));
        sportItems.add(new Sport("Ping Pong","Débutant","Détente",1));
        sportItems.add(new Sport("Rugby","Confirmé","Tout",5));
        sportItems.add(new Sport("Natation","Intermédiaire","Sérieux",4));
        sportItems.add(new Sport("Volleyball","Débutant","Tout",2));
        sportItems.add(new Sport("Peche","Intermédiaire","Détente",4));
    }


    public void editProfile (View view) {
        Intent intent = new Intent(this,EditingProfileActivity.class);
        startActivity(intent);
    }

    public void getData() {

        String name = new StringBuilder().append(AccessData.currentUser.getFirstname()).append(" ").append(AccessData.currentUser.getName()).toString();
        name_text.setText(name);
        age_text.setText(AccessData.currentUser.getAge());
        sportItems = new ArrayList<>(AccessData.currentUser.getSports());
        number_sports.setText(String.valueOf(sportItems.size()));
        bio_content.setText(AccessData.currentUser.getBio());
        Toast.makeText(this,AccessData.currentUser.getBio(),Toast.LENGTH_SHORT).show();
    }


}
