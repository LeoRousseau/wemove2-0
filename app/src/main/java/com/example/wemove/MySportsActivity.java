package com.example.wemove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

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

    public void onAdd(View view) {
        adapter.notifyDataSetChanged();
        Log.d("FLOAT","oui");
    }


    @Override
    protected void onStop() {
        super.onStop();
        gridView=null;
    }

    public static void hidePB() {
        progressbarMySports.setVisibility(View.GONE);
    }

}
