package com.example.wemove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import Utils.AccessData;

public class CreateEvent extends AppCompatActivity {

    private EditText eventName;
    private EditText eventLevel;
    private EditText eventDescription;
    private EditText sport;
    private EditText eventPlace;
    private EditText nbParticipant;
    private Button save;
    private ArrayList<Sport> sports= new ArrayList<Sport>();
    private Sport football= new Sport("Football");
    private Sport basketball= new Sport("Basketball");
    private Sport bowling= new Sport("Bowling");
    private Sport rugby= new Sport("Rugby");
    private Sport baseball= new Sport("Baseball");
    private Sport ping= new Sport("Ping Pong");
    private Sport volleyball= new Sport("Volleyball");
    private Sport musculation= new Sport("Musculation");
    private Sport cyclisme= new Sport("Cyclisme");
    private Sport kayak= new Sport("Kayak");
    private Sport badminton= new Sport("Badminton");
    private Sport ski= new Sport("Ski");
    private Sport equitation= new Sport("Equitation");
    private Sport peche= new Sport("Peche");
    private Sport natation= new Sport("Natation");
    private boolean groupe =false;

    boolean isFound=false;

    //private WeMoveDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);



        sports.add(football);
        sports.add(natation);
        sports.add(basketball);
        sports.add(bowling);
        sports.add(rugby);
        sports.add(baseball);
        sports.add(ping);
        sports.add(volleyball);
        sports.add(musculation);
        sports.add(cyclisme);
        sports.add(kayak);
        sports.add(badminton);
        sports.add(ski);
        sports.add(equitation);
        sports.add(peche);
        sports.add(natation);


        eventName=findViewById(R.id.NameEvent);
        eventLevel=findViewById(R.id.Niveau);
        eventDescription=findViewById(R.id.Description);
        eventPlace=findViewById(R.id.Place);
        sport=findViewById(R.id.NameSport);
        nbParticipant=findViewById(R.id.NbPersonne);
        save=findViewById(R.id.sauvegarder);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClicked();
            }
        });


    }

    public void onSubmitClicked(){
        String name= eventName.getText().toString();
        String place= eventPlace.getText().toString();
        String description=eventDescription.getText().toString();
        String level=eventLevel.getText().toString();
        String sportName= sport.getText().toString();
        int nbParticipantAlpha =(int)nbParticipant.getAlpha();
        if(name.isEmpty()) {
            eventName.setError("Le nom est requis");
            eventName.requestFocus();
            return;
        }
        if(place.isEmpty()) {
            eventPlace.setError("L'endroit de pratique est requis");
            eventPlace.requestFocus();
            return;
        }
        if(level.isEmpty()) {
            eventLevel.setError("Le niveau demandé est requis");
            eventLevel.requestFocus();
            return;
        }

        for(int i=0; i<sports.size();i++) {

            if(sportName.matches(sports.get(i).getName())) {
                isFound=true;
                break;
            }
        }
        if(!isFound || sportName.isEmpty()) {
            sport.setError("Le sport n'est pas sur la liste des sports disponibles");
            sport.requestFocus();
            return;
        }



        Sport s= new Sport(sportName);

        Event createdEvent = new Event(name,s,nbParticipantAlpha,groupe,place,level,description);
        Home.events.add(createdEvent);

        AccessData.db.addEvent(createdEvent);
        startActivity(new Intent(CreateEvent.this,Home.class));

    }
}
