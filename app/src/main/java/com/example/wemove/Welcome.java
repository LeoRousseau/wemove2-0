package com.example.wemove;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// Classe test pour les tests de la bdd
public class Welcome extends AppCompatActivity {

    WeMoveDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ///////////////////////////////////// Pour les tests ///////////////////////////////////////

        db = new WeMoveDB();

        ArrayList<Sport> sports = new ArrayList<>();
        ArrayList<Sport> sports2 = new ArrayList<>();

        sports.add( new Sport("football","intermédiaire", "détente", 2));
        sports.add( new Sport("tennis","débutant", "compétition", 5));
        sports.add( new Sport("rugby","expert", "apprendre", 4));

        sports2.add( new Sport("ski","intermédiaire", "détente", 2));
        sports2.add( new Sport("tennis","débutant", "compétition", 5));
        sports2.add( new Sport("rugby","expert", "apprendre", 4));

        User user = new User("OliverNico","Oliver","Nicolas","Joyeux gars",sports);
        User user2 = new User("RousseauLeo","Rousseau","Leo","Happy guy",sports2);
        User userEmail = new User("test","nico","test","j@gmail.com","012541");

        /*File img = new File("C:/Users/nicoo/Google Drive/Mobilité sortante/Cours UQAC/Automne/Prog Mobiles/marvel.png");
        User userPhoto = new User("MartinDupont","Dupont","Martin","Un test",sports);
        db.addPhoto(userPhoto,img);*/

        //Pour ajouter un evenement
        Event event = new Event("Badminton Time",new Sport("badminton"),5,false,"Pavillon sportif","Amateur","Cherche 2 personnes pour une doublette");

        // Pour completer un profil (une description et liste de sport)
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("bio","joyeux noel");
        childUpdates.put("sports",sports);

        //Pour modifier un evenement
        Map<String, Object> eventUpdates = new HashMap<>();
        eventUpdates.put("niveau","Pro");

        //Pour modifier un utilisateur
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("bio","Happy Tree Friends");

        //Ajout utilisateur
        db.addUser(userEmail);
        db.addUser(user);
        db.addUser(user2);
        //Ajout event
        db.addEvent(event);
        //Ajout des utilisateurs dans la table sport
        db.implementSports(user);
        //complete un profil lors de la premiere utilisation
        db.completeProfil(userEmail,childUpdates);
        //modifier un event
        db.updateEvent(event,eventUpdates);
        //modifier un user
        db.updateUser(user2,userUpdates);
        //Supprimer un event
        //db.deleteEvent(event);




        ////////////////////////////////////////////////////////////////////////////////////////////

    }
}
