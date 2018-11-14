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

import profile.ProfileActivity;

public class Home extends AppCompatActivity {

    private Button mSignOutBtn;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        setupFirebaseListener();

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

    public void goToProfile (View view) {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    public void signOut (View view) {
        Log.d("ATTEMPT", "onClick: attempting to sign out the user");
        FirebaseAuth.getInstance().signOut();
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
