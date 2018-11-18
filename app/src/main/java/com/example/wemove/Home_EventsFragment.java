package com.example.wemove;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Home_EventsFragment extends Fragment {
    private ListView lv;
    private Button create;
    private Button join;
    private ArrayList<String> eventNames= new ArrayList<String>();
    private ArrayList<String> eventDescriptions= new ArrayList<String>();
    private ArrayList<String> eventPlaces= new ArrayList<String>();
    private ArrayList<String> eventNiveaux= new ArrayList<String>();
    private ArrayList<String> eventSports= new ArrayList<String>();
    private ArrayList<Integer> eventNbParticipants= new ArrayList<Integer>();
    // private ArrayList<ArrayList<User>> eventUsers= new ArrayList<ArrayList<User>>();
    private ArrayList<Event> events=new ArrayList<Event>();
    Home home;
    // private ArrayList<User> usersFoot= new ArrayList<User>();
    private Sport foot=new Sport("foot");
    private boolean group=false;
    // private User player= new User("daas","saad","ddddd","d.b@gmail.com","03/08/1996");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_events, container, false);

        events = Home.events;

        for (int i = 0; i < events.size(); i++) {
            eventNames.add(events.get(i).getName());
            eventDescriptions.add(events.get(i).getDescription());
            eventNbParticipants.add(events.get(i).getNbPeople());
            eventPlaces.add(events.get(i).getPlace());
            eventNiveaux.add(events.get(i).getNiveau());
            eventSports.add(events.get(i).getSport().getName());
        }


        lv = (ListView) view.findViewById(R.id.listEventsView);
        create = (Button) view.findViewById(R.id.Create);
        join = (Button) view.findViewById(R.id.Rejoindre);

        Home_EventsFragment.EventAdaptater eventAdaptater = new Home_EventsFragment.EventAdaptater();
        lv.setAdapter(eventAdaptater);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateClicked();
            }
        });
/*
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
        return view;
    }

        public void onCreateClicked(){
            Intent intent=new Intent("android.intent.action.CreateEvent");
            startActivity(intent);
        }


        class EventAdaptater extends BaseAdapter {

            @Override
            public int getCount() {
                return events.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                view=getLayoutInflater().inflate(R.layout.eventlayout,null);

                TextView nom=(TextView) view.findViewById(R.id.textViewnom);
                TextView description=(TextView) view.findViewById(R.id.textViewDescription);
                TextView sport =(TextView) view.findViewById(R.id.textViewSport);
                TextView place =(TextView) view.findViewById(R.id.textViewPlace);
                TextView niveau=(TextView) view.findViewById(R.id.textViewNiveau);


                nom.setText(eventNames.get(position));
                description.setText(eventDescriptions.get(position));
                sport.setText(eventSports.get(position));
                place.setText(eventPlaces.get(position));
                niveau.setText(eventNiveaux.get(position));
                return view;
            }
        }


}
