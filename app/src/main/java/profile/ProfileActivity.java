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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wemove.Home;
import com.example.wemove.R;
import com.example.wemove.Sport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.AccessData;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static boolean isRunning=false;
    public static boolean isCharged=false;
    public static ProgressBar progressBarProfile;
    public static LinearLayout linearLayoutProfile;

    public static HorizontalSportAdapter horizontalSportAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private boolean isOwner;
    private boolean isFirst;

    private LinearLayout selecLL;
    private RelativeLayout editinglayout;
    private RelativeLayout ratingLayout;

    private static CircleImageView image_profile;
    private static TextView name_text;
    private static TextView age_text;
    private static RatingBar user_ratingBar;
    private static TextView number_activities;
    private static TextView number_sports;
    private static TextView bio_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isRunning=true;
        isOwner=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init Content Variables
        initContentVariables();
        Log.d("EVENT", "profile");
        //Get Data from DataBase
        getData();

        //Set layout with screen size
        setLayout();

        // Sport
        //setSports();
        LinearLayout ll = (LinearLayout) findViewById(R.id.layoutselected);
        recyclerView = (RecyclerView) findViewById(R.id.sportRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        horizontalSportAdapter = new HorizontalSportAdapter(AccessData.currentUser,this,ll);
        recyclerView.setAdapter(horizontalSportAdapter);

        // Editing
        RelativeLayout editingLayout = (RelativeLayout)findViewById(R.id.editingLayout);
        if (isOwner)
            editingLayout.setVisibility(View.VISIBLE);
        else
            editingLayout.setVisibility(View.GONE);

        editingLayout.bringToFront();
        image_profile.bringToFront();
        ratingLayout.bringToFront();
        user_ratingBar.bringToFront();

        if (isCharged) {
            hidePB();
        }
        else {
            linearLayoutProfile.setVisibility(View.GONE);
        }
    }


    public void initContentVariables () {

        linearLayoutProfile = (LinearLayout)findViewById(R.id.llProfile);
        progressBarProfile = (ProgressBar)findViewById(R.id.progressbarProfile);
        ratingLayout = (RelativeLayout) findViewById(R.id.ratinglayout);
        editinglayout = (RelativeLayout) findViewById(R.id.editingLayout);
        selecLL = (LinearLayout) findViewById(R.id.layoutselectedint);
        image_profile = (CircleImageView)findViewById(R.id.profile_image);
        name_text = (TextView) findViewById(R.id.nameText);
        age_text = (TextView) findViewById(R.id.ageText);
        user_ratingBar = (RatingBar) findViewById(R.id.userRatingBar);
        number_activities = (TextView) findViewById(R.id.number_activities);
        number_sports = (TextView) findViewById(R.id.number_sports);
        bio_content = (TextView) findViewById(R.id.bio_content);

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



    public void editProfile (View view) {
        Intent intent = new Intent(this,EditingProfileActivity.class);
        startActivity(intent);
    }

    public static void getData() {
        String name = new StringBuilder().append(AccessData.currentUser.getFirstname()).append(" ").append(AccessData.currentUser.getName()).toString();
        name_text.setText(name);
        String date_ = new StringBuilder().append(String.valueOf(AccessData.ageCalculator.calculateAge(new Date(1990,12,07)))).append(" ans").toString();
        age_text.setText(date_);
        number_sports.setText(String.valueOf(AccessData.currentUser.getSports().size()));
        bio_content.setText(AccessData.currentUser.getBio());
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning=false;
    }

    public static void hidePB() {
        progressBarProfile.setVisibility(View.GONE);
        linearLayoutProfile.setVisibility(View.VISIBLE);
    }

}
