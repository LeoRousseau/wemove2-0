package profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wemove.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private List<SportItem> sportItems;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private boolean isOwner;
    private boolean isFirst;

    private LinearLayout infoLL;
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
        setSports();
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

        infoLL = (LinearLayout) findViewById(R.id.infoLinearLayout);
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
            infoLL.setOrientation(LinearLayout.HORIZONTAL);
            selecLL.setOrientation(LinearLayout.HORIZONTAL);
        }

    }

    public void setSports () {
        sportItems.add(new SportItem("Football",(float)3.5,"Intermédiaire","Détente"));
        sportItems.add(new SportItem("Ping Pong",1,"Débutant","Détente"));
        sportItems.add(new SportItem("Rugby",5,"Confirmé","Tout"));
        sportItems.add(new SportItem("Natation",4,"Intermédiaire","Sérieux"));
        sportItems.add(new SportItem("Volleyball",2,"Débutant","Tout"));
        sportItems.add(new SportItem("Peche",4,"Intermédiaire","Détente"));
    }


    public void editProfile (View view) {
        Intent intent = new Intent(this,EditingProfileActivity.class);
        startActivity(intent);
    }

    public void getData() {

    }


}
