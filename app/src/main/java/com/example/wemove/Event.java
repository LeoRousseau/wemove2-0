package com.example.wemove;

import android.util.Log;

import java.util.ArrayList;

import Utils.AccessData;

public class Event {
    String id;
    String name;
    Sport sport;
    int nbPeople;
    boolean groupe = false;
    String place;
    String niveau;
    String description;
    ArrayList<String> usersID;


    public Event(String id, String name, Sport sport, boolean groupe, String place, String niveau, String description, int numb) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.groupe = groupe;
        this.place = place;
        this.niveau = niveau;
        this.description = description;
        this.usersID = new ArrayList<>();
        usersID.add(AccessData.currentUser.getId());
        this.nbPeople = numb;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return "Event{" +
                "Nom = '" + name + '\'' +
                ", Sport =" + sport +
                ", Nombre de personne =" + nbPeople +
                ", Réservé =" + groupe +
                ", Lieu ='" + place + '\'' +
                ", Niveau ='" + niveau + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }

    public Event(String id, String name, Sport sport, int nbPeople, boolean groupe, String place, String niveau, String description, ArrayList<String> usersID) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.nbPeople = nbPeople;
        this.groupe = groupe;
        this.place = place;
        this.niveau = niveau;
        this.description = description;
        this.usersID = usersID;
    }

    public void setUsersID(ArrayList<String> usersID) {
        this.usersID = usersID;
    }

    public ArrayList<String> getUsersID() {

        return usersID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public int getNbPeople() {
        return nbPeople;
    }

    public void setNbPeople(int nbPeople) {
        this.nbPeople = nbPeople;
    }

    public boolean isGroupe() {
        return groupe;
    }

    public void setGroupe(boolean groupe) {
        this.groupe = groupe;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}