package com.example.wemove;

import java.util.ArrayList;

public class Event {
    String name;
    Sport sport;
    int nbPeople;
    boolean groupe = false;
    String place;
    String niveau;
    String description;
    ArrayList<User> users;

    public Event(String name, Sport sport, int nbPeople, boolean groupe, String place, String niveau, String description, ArrayList<User> users) {
        this.name = name;
        this.sport = sport;
        this.nbPeople = nbPeople;
        this.groupe = groupe;
        this.place = place;
        this.niveau = niveau;
        this.description = description;
        this.users = users;
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
                ", Participants =" + users +
                '}';
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
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

    public Event(String name, Sport sport, int nbPeople, boolean groupe, String place, String niveau, String description) {

        this.name = name;
        this.sport = sport;
        this.nbPeople = nbPeople;
        this.groupe = groupe;
        this.place = place;
        this.niveau = niveau;
        this.description = description;
    }
}
