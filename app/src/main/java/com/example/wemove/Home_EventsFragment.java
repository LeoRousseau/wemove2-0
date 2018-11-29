package com.example.wemove;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Utils.AccessData;

public class Home_EventsFragment extends Fragment {

    public static ListView lv=null;
    private FloatingActionButton create;
    private Button join;
    private Sport foot=new Sport("foot");
    private boolean group=false;
    public static EventAdapter eventAdapter;
    public static ProgressBar progressBarEvents;
    public static boolean isCharged = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_events, container, false);
        progressBarEvents = (ProgressBar)view.findViewById(R.id.progressbarEvents);
        //eventAdapter=null;
        lv = (ListView) view.findViewById(R.id.listEventsView);
        create = (FloatingActionButton) view.findViewById(R.id.Create);
        join = (Button) view.findViewById(R.id.Rejoindre);
        if (AccessData.events.size()==0) {
            //AccessData.db.getEvents();
        }
        if (eventAdapter==null) {
            eventAdapter = new EventAdapter(getActivity(), AccessData.events);
        }
        lv.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.CreateEvent");
                startActivity(intent);
            }
        });


        if (isCharged) {
            hidePB();
        }

        return view;
    }

    public EventAdapter getEventAdapter() {
        return eventAdapter;
    }

    public static void hidePB() {
        progressBarEvents.setVisibility(View.GONE);
    }

}
