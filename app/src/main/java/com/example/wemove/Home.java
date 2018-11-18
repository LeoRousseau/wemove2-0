package com.example.wemove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Utils.AccessData;
import profile.ProfileActivity;

public class Home extends AppCompatActivity {

    private Button mSignOutBtn;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private BottomNavigationView bottomNavigationView;
    private  FirebaseAuth mAuth;
    private  String userID;
   // private WeMoveDB db;
    public static User currentUser;
    public static Event currentEvent;
    public static ArrayList<Event> events= new ArrayList<Event>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        setupFirebaseListener();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_EventsFragment()).commit();

        // Test des méthodes (validées)
        //db.getUser(userID);
        AccessData.db.getEvents();
    }


    private void setupFirebaseListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("SIGNIN", "onAuthStateChanged:  signed_in: " + user.getUid());
                } else {
                    Log.d("SIGNOUT", "onAuthStateChanged: signed_out");
                    Toast.makeText(getBaseContext(), "Signed out", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Home.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragmentSelected=null;
                    switch (menuItem.getItemId()) {
                        case R.id.eventButton :
                            fragmentSelected = new Home_EventsFragment();
                            break;
                        case R.id.groupButton :
                            fragmentSelected = new Home_GroupsFragment();
                            break;
                        case R.id.notificationsButton :
                            fragmentSelected = new Home_NotificationsFragment();
                            break;
                        case R.id.menuButton :
                            fragmentSelected = new Home_MenuFragment();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentSelected).commit();
                    return true;
                }
            };
}