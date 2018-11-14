package com.example.wemove;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;



public class WeMoveDB {
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mUserRef = mRootRef.child("Users");
    private DatabaseReference mSportsRef = mRootRef.child("Sports");
    private DatabaseReference mEventRef = mRootRef.child("Event");

    public void addUser(User user) {
        DatabaseReference mParamUserRef = mUserRef.child(user.getTag());
        mParamUserRef.setValue(user);
    }

    public void addSports(String tag, String name, String userTag) {
        DatabaseReference mParamSportsRef = mSportsRef.child(tag);
        DatabaseReference mParamSportsInsideRef = mParamSportsRef.child("Users").child(name);
        mParamSportsInsideRef.setValue(userTag);
    }

    public void implementSports(User user) {
        for(Sport s : user.getSports()) {
            this.addSports(s.getName(), user.getName(), user.getTag());
        }
    }

    public void addEvent(Event e) {
        DatabaseReference mParamEventRef = mEventRef.child(e.getName());
        mParamEventRef.setValue(e);
    }

    public void completeProfil(User user, Map childUpdates) {
        DatabaseReference mCompleteRef = mUserRef.child(user.getTag());
        mCompleteRef.updateChildren(childUpdates);
    }

    public void updateUser(User user, Map userUpdates) {
        DatabaseReference mUpdateUserRef = mUserRef.child(user.getTag());
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
