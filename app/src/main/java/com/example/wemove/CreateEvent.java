package com.example.wemove;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Utils.AccessData;

public class CreateEvent extends AppCompatActivity {

    private EditText eventName;
    private Spinner eventLevel;
    private EditText eventDescription;
    private Spinner sport;
    private EditText eventPlace;
    private EditText nbParticipant;
    private Button datebutton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayList<String> sportsName= new ArrayList<String>();
    private TextView tvdate;
    private Date date;
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
        datebutton=findViewById(R.id.buttonDate);
        tvdate = findViewById(R.id.textViewdate);

        Calendar calendar = Calendar.getInstance();
        tvdate.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(calendar.get(Calendar.MONTH)+1)+"/" + String.valueOf(calendar.get(Calendar.YEAR)));
        date = new Date();
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();


                DatePickerDialog dialog = new DatePickerDialog(
                        CreateEvent.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date=new Date(year-1900,month,dayOfMonth);
                tvdate.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/" + String.valueOf(year));
            }
        };

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
        String numb = nbParticipant.getText().toString();

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



        Sport s= new Sport(sportName);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        date.setHours(12);
        Event createdEvent = new Event(ts,name,s,groupe,place,level,description,Integer.parseInt(numb),date.getTime());
        AccessData.db.addEvent(createdEvent);
        startActivity(new Intent(CreateEvent.this,Home.class));

    }

    public void onDismissEvent (View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

}
