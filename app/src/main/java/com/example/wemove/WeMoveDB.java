package com.example.wemove;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import Utils.AccessData;
import profile.ProfileActivity;


public class WeMoveDB {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("Users");
    private DatabaseReference mSportsRef = mRootRef.child("Sports");
    private DatabaseReference mEventRef = mRootRef.child("Event");
    private DatabaseReference mCuserRef;

    public WeMoveDB() {


        mEventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AccessData.events.add(dataSnapshot.getValue(Event.class));
                if(Home_EventsFragment.lv!=null) {
                    Home_EventsFragment.eventAdapter.notifyDataSetChanged();
                    Home_EventsFragment.hidePB();
                }
                Home_EventsFragment.isCharged=true;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addUser(User user) {
        DatabaseReference mParamUserRef = mUserRef.child(user.getId());
        mParamUserRef.setValue(user);
    }

    public void addUserToSport(Sport s, User user) {
        DatabaseReference mParamSportsRef = mSportsRef.child(s.getName());
        DatabaseReference mParamSportsInsideRef = mParamSportsRef.child(Float.toString(s.getInterest()).replace('.',':'));
        DatabaseReference mIdUser = mParamSportsInsideRef.child(user.getTag());
        mIdUser.setValue(user.getId());
    }

    public void implementSports(User user) {
        for(Sport s : user.getSports()) {
            this.addUserToSport(s, user);
        }
    }

    public void addEvent(Event e) {
        DatabaseReference mParamEventRef = mEventRef.child(e.getName());
        mParamEventRef.setValue(e);
    }

    public void completeProfil(User user, Map childUpdates) {
        DatabaseReference mCompleteRef = mUserRef.child(user.getId());
        mCompleteRef.updateChildren(childUpdates);
    }

    public void updateUser(User user, Map userUpdates) {
        DatabaseReference mUpdateUserRef = mUserRef.child(user.getId());
        mUpdateUserRef.updateChildren(userUpdates);
    }

    public void updateEvent(Event e, Map eventUpdates) {
        DatabaseReference mUpdateEventRef = mEventRef.child(e.getName());
        mUpdateEventRef.updateChildren(eventUpdates);
    }

    public void deleteEvent(Event e) {
        DatabaseReference mDeleteEventRef = mEventRef.child(e.getName());
        mDeleteEventRef.removeValue();
    }

    public void getEvent(String name) {
        DatabaseReference mGetEventRef = mEventRef.child(name);
        mGetEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccessData.currentEvent = dataSnapshot.getValue(Event.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Erreur", "Erreur");
            }
        });
    }


    public void getUser(String id) {
        DatabaseReference mGetUserRef = mUserRef.child(id);
        mGetUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccessData.currentUser = dataSnapshot.getValue(User.class);
                Log.d("EVENT", "USER");
                if (MySportsActivity.gridView!=null) {
                    MySportsActivity.adapter.setInput(AccessData.currentUser);
                    MySportsActivity.adapter.notifyDataSetChanged();
                    MySportsActivity.hidePB();
                    Log.d("EVENT", "notifyed");
                }
                MySportsActivity.isCharged=true;
                if (ProfileActivity.isRunning) {
                    ProfileActivity.getData();
                    ProfileActivity.horizontalSportAdapter.setInput(AccessData.currentUser);
                    ProfileActivity.horizontalSportAdapter.notifyDataSetChanged();
                    ProfileActivity.hidePB();
                }
                ProfileActivity.isCharged=true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Erreur", "Erreur");
            }
        });
    }



    /* public void addPhoto(User user, File path) {

        Uri file = Uri.fromFile(path);
        StorageReference mParamStorageRef = mStorageRef.child(user.getTag());

        mParamStorageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    } */

}
