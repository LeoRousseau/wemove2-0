package com.example.wemove;

import android.content.Context;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Utils.AccessData;
import profile.EditingProfileActivity;
import profile.ProfileActivity;


public class WeMoveDB {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("Users");
    private DatabaseReference mSportsRef = mRootRef.child("Sports");
    private DatabaseReference mEventRef = mRootRef.child("Event");
    private DatabaseReference mCuserRef;
    private Uri photo;
    private String urlPhoto;
    public static Boolean isSuccess = false;

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
                //Log.d("test",String.valueOf(AccessData.events.get(AccessData.events.size()-1).getUsersID().size()));
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
        DatabaseReference mParamUserRef = mUserRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mParamUserRef.setValue(user);
    }

    public void addUserToSport(Sport s, User user) {
        DatabaseReference mParamSportsRef = mSportsRef.child(s.getName());
        DatabaseReference mParamSportsInsideRef = mParamSportsRef.child(s.getLevel());
        DatabaseReference mIdUser = mParamSportsInsideRef.child(user.getId());
        mIdUser.setValue(user.getId());
    }

    public void implementSports(User user) {
        for(Sport s : user.getSports()) {
            if(s.getInterest() >= 4) {
                this.addUserToSport(s, user);
            }
        }
    }

    public void deleteSports (List<Sport> list) {
        for (Sport s : list) {
            DatabaseReference mParamSportsRef = mSportsRef.child(s.getName());
            DatabaseReference mParamSportsInsideRef = mParamSportsRef.child(s.getLevel());
            mParamSportsInsideRef.child(AccessData.currentUser.getId()).removeValue();
        }
    }

    public void addEvent(Event e) {
        DatabaseReference mParamEventRef = mEventRef.child(e.getId());
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
        DatabaseReference mUpdateEventRef = mEventRef.child(e.getId());
        DatabaseReference mUserIdRef = mUpdateEventRef.child("usersID");
        mUserIdRef.updateChildren(eventUpdates);
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
                Log.d("EVENT",String.valueOf(Home.isRunning));
                if (Home.isRunning) {
                    boolean notifNotSeen=false;
                    if (AccessData.currentUser.notifications!=null) {
                        for (Notification value : AccessData.currentUser.notifications.values()) {
                            if (!value.isSeen()) {
                                notifNotSeen = true;
                            }
                        }
                        if (notifNotSeen) {
                            Home.notificationBadge.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Erreur", "Erreur");
            }
        });
    }


    public void addPhoto(Uri path) {

        Uri file = path;
        StorageReference mParamStorageRef = mStorageRef.child(AccessData.currentUser.getId());

        mParamStorageRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        isSuccess = true;
                        Log.d("photo", "onSuccess: Done");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("photo", "onFailure: " + exception.getMessage());
                    }
                });
    }

    public String getPhoto() {
        if(NewUser.firstTime) {
            NewUser.firstTime = false;
        }
        else {
            if(AccessData.currentUser.getId() == null) {

            }
            else {
                StorageReference mGetPhotoRef = mStorageRef.child(AccessData.currentUser.getId());

                mGetPhotoRef.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(ProfileActivity.isRunning) {
                                    String url = String.valueOf(uri);
                                    urlPhoto = url;
                                    Glide.with(ProfileActivity.ctx).load(url).into(ProfileActivity.image_profile);
                                }
                                if(EditingProfileActivity.isRunning) {
                                    String url = String.valueOf(uri);
                                    Glide.with(EditingProfileActivity.ctx).load(url).into(EditingProfileActivity.profilePicture);
                                }
                                Log.d("ok", "chemin :"+uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("photo", "onFailure: File doesn't exists");
                    }
                });
            }

        }
        return urlPhoto;
    }
}
