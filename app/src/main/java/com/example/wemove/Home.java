package com.example.wemove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
    private View notificationBadge;
    private  FirebaseAuth mAuth;
    private  String userID;
    private boolean first = true;



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

        // Test des méthodes (validées)

        AccessData.db.getUser(userID);

        if (savedInstanceState!=null) {
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home_GroupsFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.groupButton);
        }


        addBadgeView();

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

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("frag",0);
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

    private void addBadgeView() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notifications_badge, menuView, false);

        itemView.addView(notificationBadge);
    }
}