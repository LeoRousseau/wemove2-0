package com.example.wemove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Utils.AccessData;

public class EventDetails extends AppCompatActivity {

    TextView eventName;
    TextView eventDesc;
    TextView eventSport;
    TextView day;
    TextView month;
    TextView eventNumber;
    TextView nameSportEvent;
    Button join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent=getIntent();
        final int position = intent.getIntExtra("position",0);

        eventDesc = findViewById(R.id.textdesc);
        eventName = findViewById(R.id.textName);
        eventSport = findViewById(R.id.textSporte);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        eventNumber = findViewById(R.id.numb);
        join = findViewById(R.id.btnJoin);
        nameSportEvent = findViewById(R.id.nameSportEvent);


        if (AccessData.currentUser.getId().compareTo(AccessData.events.get(position).usersID.get(0))==0) {
            join.setText("GERER");
            join.setTextColor(this.getResources().getColor(R.color.blue));
        }
        else if (isJoined(position)){
            join.setText("QUITTER");
            join.setTextColor(this.getResources().getColor(R.color.OrangeNormal));
        }
        else if (AccessData.events.get(position).getNbPeople()<=AccessData.events.get(position).usersID.size()) {
            join.setText("COMPLET");
            join.setTextColor(this.getResources().getColor(R.color.notifRed));
        }
        else {
            join.setText("REJOINDRE");
            join.setTextColor(this.getResources().getColor(R.color.OrangeNormal));
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessData.events.get(position).usersID.get(0).compareTo(AccessData.currentUser.getId())==0) {
                    Intent intent = new Intent(EventDetails.this,ManageEvent.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                }
                else if (isJoined(position)){
                    Log.d("changed","1");
                    ArrayList<String> list = new ArrayList<>();
                    for (int i=0;i<AccessData.events.get(position).usersID.size();i++) {
                        list.add(new String(AccessData.events.get(position).usersID.get(i)));
                    }
                    int i;
                    for (i=0;i<list.size();i++) {
                        if (list.get(i).compareTo(AccessData.currentUser.getId())==0) {
                            list.remove(i);
                            break;
                        }
                    }
                    Map<String, Object> eventUpdates = new HashMap<>();
                    eventUpdates.put("usersID",list);
                    AccessData.db.updateEvent(AccessData.events.get(position),eventUpdates);
                    join.setText("REJOINDRE");
                    Log.d("changed",join.getText().toString());
                    join.setTextColor(EventDetails.this.getResources().getColor(R.color.OrangeNormal));

                }
                else if (AccessData.events.get(position).getNbPeople()<=AccessData.events.get(position).usersID.size()) {
                    //COMPLET
                }
                else {
                    Log.d("changed","2");
                    Map<String, Object> eventUpdates = new HashMap<>();
                    ArrayList<String> list = new ArrayList<>();
                    for (int i=0;i<AccessData.events.get(position).usersID.size();i++) {
                        list.add(new String(AccessData.events.get(position).usersID.get(i)));
                    }
                    list.add(AccessData.currentUser.getId());
                    eventUpdates.put("usersID",list);
                    AccessData.db.updateEvent(AccessData.events.get(position),eventUpdates);
                    join.setText("QUITTER");
                    Log.d("changed",join.getText().toString());
                    join.setTextColor(EventDetails.this.getResources().getColor(R.color.OrangeNormal));
                }
            }
        });

        eventDesc.setText(AccessData.events.get(position).getDescription());
        eventName.setText(AccessData.events.get(position).getName());
        eventSport.setText(AccessData.events.get(position).getSport().getName() + "-" + AccessData.events.get(position).getNiveau());
        Date date = new Date(AccessData.events.get(position).date);
        day.setText(String.valueOf(date.getDate()));
        month.setText(getMonthAccro(date.getMonth()));
        eventNumber.setText(AccessData.events.get(position).usersID.size() + "/" +AccessData.events.get(position).getNbPeople());
        nameSportEvent.setText(AccessData.events.get(position).getSport().getName());
    }

    public String getMonthAccro(int val) {
        String result="";
        switch (val) {
            case 0 :
                result="JAN";
                break;
            case 1 :
                result="FEV";
                break;
            case 2 :
                result="MARS";
                break;
            case 3 :
                result="AVR";
                break;
            case 4 :
                result="MAI";
                break;
            case 5 :
                result="JUIN";
                break;
            case 6 :
                result="JUIL";
                break;
            case 7 :
                result="AOUT";
                break;
            case 8 :
                result="SEPT";
                break;
            case 9 :
                result="OCT";
                break;
            case 10 :
                result="NOV";
                break;
            case 11 :
                result="DEC";
                break;
        }
        return result;
    }


    public boolean isJoined(int position) {
        boolean result=false;
        if (AccessData.events.get(position).usersID.size()>1) {
            for (int i=1;i<AccessData.events.get(position).usersID.size();i++) {
                if(AccessData.events.get(position).usersID.get(i).compareTo(AccessData.currentUser.getId())==0) {
                    result  = true;
                }
            }
        }
        return result;
    }
}
