package com.example.wemove;

import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import Utils.AccessData;

public class MyEventsActivity extends AppCompatActivity {


    private BottomNavigationView eventNavigationView;
    private ListView listView;
    private ArrayList<Event> created;
    private ArrayList<Event> joined;
    private MyEventsAdapter createdAdapter;
    private MyEventsAdapter joinedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);


        eventNavigationView = (BottomNavigationView) findViewById(R.id.eventNavBar);
        eventNavigationView.setItemTextAppearanceActive(R.style.eventBarSelected);
        eventNavigationView.setItemTextAppearanceInactive(R.style.eventBar);
        eventNavigationView.setOnNavigationItemSelectedListener(navListener);
        listView = findViewById(R.id.lvEvent);

        init();

        createdAdapter = new MyEventsAdapter(this,created,0);
        joinedAdapter = new MyEventsAdapter(this,joined,1);
        listView.setAdapter(createdAdapter);

    }
    

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.createdButton :
                            listView.setAdapter(createdAdapter);
                            break;
                        case R.id.joinedButton :
                            listView.setAdapter(joinedAdapter);
                            break;
                    }
                    return true;
                }

            };

    public void init() {
        created= new ArrayList<>();
        joined = new ArrayList<>();
        for (int i=0;i< AccessData.events.size();i++)
        {
            if (AccessData.events.get(i).usersID.get(0).compareTo(AccessData.currentUser.getId())==0) {
                created.add(AccessData.events.get(i));
            }
            else {
                for (int j=0;j<AccessData.events.get(i).usersID.size();j++) {
                    if (AccessData.events.get(i).usersID.get(j).compareTo(AccessData.currentUser.getId())==0) {
                        joined.add(AccessData.events.get(i));
                    }
                }
            }
        }


    }
}
