package com.example.wemove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import Utils.AccessData;

public class CreateEvent extends AppCompatActivity {

    private EditText eventName;
    private Spinner eventLevel;
    private EditText eventDescription;
    private Spinner sport;
    private EditText eventPlace;
    private EditText nbParticipant;
    private ArrayList<String> sportsName= new ArrayList<String>();
    private boolean groupe =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        for ( String key : AccessData.table.keySet() ) {
            sportsName.add(key);
        }


        eventName=findViewById(R.id.NameEvent);
        eventLevel=findViewById(R.id.Niveau);
        eventDescription=findViewById(R.id.Description);
        eventPlace=findViewById(R.id.Place);
        sport=findViewById(R.id.NameSport);
        nbParticipant=findViewById(R.id.NbPersonne);

        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sportsName);
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sport.setAdapter(adapterName);
        String[] levels = {"Débutant","Intermédiaire","Confirmé"};
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,levels);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventLevel.setAdapter(adapterLevel);


    }

    public void onConfirmEvent(View view){
        String name= eventName.getText().toString();
        String place= eventPlace.getText().toString();
        String description=eventDescription.getText().toString();
        String level=eventLevel.getSelectedItem().toString();
        String sportName= sport.getSelectedItem().toString();
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
            eventLevel.requestFocus();
            return;
        }
        if(sportName.isEmpty()) {
            sport.requestFocus();
            return;
        }

        Log.d("TEST","o");

        Sport s= new Sport(sportName);

        Event createdEvent = new Event(name,s,nbParticipantAlpha,groupe,place,level,description);
        AccessData.events.add(createdEvent);

        AccessData.db.addEvent(createdEvent);
        startActivity(new Intent(CreateEvent.this,Home.class));

    }

    public void onDismissEvent (View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

}
