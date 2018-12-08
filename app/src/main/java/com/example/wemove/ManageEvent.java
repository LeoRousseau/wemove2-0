package com.example.wemove;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.security.acl.AclEntry;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Utils.AccessData;
import profile.ProfileActivity;

public class ManageEvent extends AppCompatActivity {

    NumberPicker numberPicker;
    private EditText eventName;
    private EditText eventDescription;
    private EditText eventPlace;
    private Button datebutton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView tvdate;
    private Date date;
    private int position;
    private Button supp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_event);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);


        eventName=findViewById(R.id.NameEvent2);
        eventDescription=findViewById(R.id.Description2);
        eventPlace=findViewById(R.id.Place2);
        datebutton=findViewById(R.id.buttonDate2);
        tvdate = findViewById(R.id.textViewdate2);
        numberPicker = findViewById(R.id.numpick);
        supp = findViewById(R.id.supp);


        eventName.setText(AccessData.events.get(position).getName());
        eventDescription.setText(AccessData.events.get(position).getDescription());
        eventPlace.setText(AccessData.events.get(position).getPlace());

        numberPicker.setMinValue(AccessData.events.get(position).getNbPeople());
        numberPicker.setMaxValue(999);


        tvdate.setText(String.valueOf(AccessData.events.get(position).date.getDate())+"/"+String.valueOf(AccessData.events.get(position).date.getMonth()+1)+"/" + String.valueOf(AccessData.events.get(position).date.getYear()+1900));
        date = new Date();
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();


                DatePickerDialog dialog = new DatePickerDialog(
                        ManageEvent.this,
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


        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ManageEvent.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Supprimer l'événement")
                        .setMessage("Etes-vous sur de vouloir supprimer l'événement ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HashMap<String,Object> map = new HashMap<>();
                                map.put("name",null);
                                map.put("description",null);
                                map.put("place",null);
                                map.put("nbPeople",null);
                                map.put("date",null);
                                map.put("usersID",null);
                                map.put("sport",null);
                                map.put("niveau",null);
                                map.put("id",null);
                                map.put("groupe",null);
                                AccessData.db.updateEvent(AccessData.events.get(position),map);
                                Intent intent = new Intent(ManageEvent.this,Home.class);
                                startActivity(intent);


                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();
            }
        });


    }


    public void onConfirm (View view) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",eventName.getText().toString());
        map.put("description",eventDescription.getText().toString());
        map.put("place",eventPlace.getText().toString());
        map.put("nbPeople",numberPicker.getValue());
        map.put("date",date);
        AccessData.db.updateEvent(AccessData.events.get(position),map);
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    public void onDismiss (View view) {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}
