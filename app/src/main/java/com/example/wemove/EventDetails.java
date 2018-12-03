package com.example.wemove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        TextView nom=findViewById(R.id.nom);
        TextView place=findViewById(R.id.endroit);
        TextView niveau=findViewById(R.id.niveau);
        TextView sport=findViewById(R.id.sport);
        TextView desc=findViewById(R.id.description);

        Intent intent=getIntent();

        nom.setText(intent.getStringExtra("nom"));
        place.setText(intent.getStringExtra("place"));
        niveau.setText(intent.getStringExtra("niveau"));
        sport.setText(intent.getStringExtra("sport"));
        desc.setText(intent.getStringExtra("description"));
    }
}
