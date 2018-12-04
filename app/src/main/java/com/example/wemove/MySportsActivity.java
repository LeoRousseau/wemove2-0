package com.example.wemove;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.AccessData;





public class MySportsActivity extends AppCompatActivity {

    public static GridView gridView = null;
    FloatingActionButton floatingActionButton;
    public static MySportAdapter adapter;
    public static ProgressBar progressbarMySports;
    public static boolean isCharged=false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sports);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.AddSport);
        gridView = (GridView)findViewById(R.id.gridViewSport);
        adapter = new MySportAdapter(this, AccessData.currentUser);
        gridView.setAdapter(adapter);
        progressbarMySports = (ProgressBar)findViewById(R.id.progressbarMySports);
        adapter.notifyDataSetChanged();

        if (isCharged) {
            hidePB();
        }

    }




    @Override
    protected void onStop() {
        super.onStop();
        gridView=null;
    }

    public static void hidePB() {
        progressbarMySports.setVisibility(View.GONE);
    }




    public void addSport (View cview) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.add_sport,null);
        builder.setTitle("Nouveau sport");

        final Spinner nameSpinner = (Spinner) view.findViewById(R.id.namesSpinner);
        final Spinner levelSpinner = (Spinner) view.findViewById(R.id.levelSpinner);
        final Spinner styleSpinner = (Spinner) view.findViewById(R.id.styleSpinner);
        final RatingBar interestRating = (RatingBar) view.findViewById(R.id.interestEdit);
        String[] levels = {"Débutant","Intermédiaire","Confirmé"};
        String[] styles = {"Détente","Sérieux","Compétition","Tout"};
        List<String> names = getOtherSportsName();
        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,names);
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapterName);
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,levels);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapterLevel);
        ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,styles);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(adapterStyle);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Sport sportItem = new Sport(nameSpinner.getSelectedItem().toString(),levelSpinner.getSelectedItem().toString(),styleSpinner.getSelectedItem().toString(),interestRating.getRating());
                if ( AccessData.currentUser.sports==null) {
                    AccessData.currentUser.sports = new ArrayList<>();
                }
                AccessData.currentUser.sports.add(sportItem);
                HashMap<String,Object> map = new HashMap<>();
                map.put("sports",AccessData.currentUser.sports);
                AccessData.db.updateUser(AccessData.currentUser,map);
                AccessData.db.implementSports(AccessData.currentUser);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public List<String> getOtherSportsName () {
        List<String> otherSport = new ArrayList<String>();
        for ( String key : AccessData.table.keySet() ) {
            otherSport.add(key);
        }
        if (AccessData.currentUser.sports!=null) {
            for (int i =0; i<AccessData.currentUser.sports.size();i++) {
                otherSport.remove(AccessData.currentUser.sports.get(i).getName());
            }
        }

        return otherSport;
    }
}
