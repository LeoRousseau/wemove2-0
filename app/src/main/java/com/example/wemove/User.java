package com.example.wemove;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class User {
    String id;
    String tag;
    String name;
    String firstname;
    String email;
    long age;
    String bio;
    ArrayList<Sport> sports;
    HashMap<String,Notification> notifications;

    public void setNotifications(HashMap<String,Notification> notifications) {
        this.notifications = notifications;
    }

    public HashMap<String,Notification> getNotifications() {

        return notifications;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<Sport> getSports() {
        if (sports==null) {
            return new ArrayList<Sport>();
        }
        return sports;
    }

    public void setSports(ArrayList<Sport> sports) {
        this.sports = sports;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public long getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "Tag='" + tag + '\'' +
                ", Nom ='" + name + '\'' +
                ", Prénom ='" + firstname + '\'' +
                ", Âge ='" + age + '\'' +
                ", Description ='" + bio + '\'' +
                '}';
    }

    public User() {
    }

    public User(String id, String tag, String name, String firstname, String bio, ArrayList<Sport> sports) {

        this.id = id;
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.bio = bio;
        this.sports = sports;
    }

    public User(String id, String tag, String name, String firstname, String email, long age, String bio, ArrayList<Sport> sports) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
        this.bio = bio;
        this.sports = sports;
    }

    public User(String tag, String name, String firstname, String email, long age) {
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
    }

    public User(String tag, String name, String firstname, String bio, ArrayList<Sport> sports, HashMap<String,Notification> notifications) {
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.bio = bio;
        this.sports = sports;
        this.notifications=notifications;
    }

    public User(String tag, String name, String firstname, String bio, ArrayList<Sport> sports) {
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.bio = bio;
        this.sports = sports;
    }

    public User(String id, String tag, String name, String firstname, String email, long age) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.age = age;
    }

    public User(String id, String name, String firstname, String email) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}