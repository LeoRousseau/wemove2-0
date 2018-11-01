package com.example.wemove;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Welcome extends AppCompatActivity {

    TextView mConditionTextView;
    Button sunny;
    Button foggy;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef =  mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mConditionTextView = findViewById(R.id.textViewCondition);
        sunny = findViewById(R.id.buttonSunny);
        foggy = findViewById(R.id.buttonFoggy);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("Sunny");
            }
        });

        foggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConditionRef.setValue("Foggy");
            }
        });
    }

}
