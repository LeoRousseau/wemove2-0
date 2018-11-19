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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Utils.AccessData;

public class Home_EventsFragment extends Fragment {

    private ListView lv;
    private FloatingActionButton create;
    private Button join;
    private Sport foot=new Sport("foot");
    private boolean group=false;
    private EventAdapter eventAdapter;
    private ArrayList<Event> events= AccessData.events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_events, container, false);

        lv = (ListView) view.findViewById(R.id.listEventsView);
        create = (FloatingActionButton) view.findViewById(R.id.Create);
        join = (Button) view.findViewById(R.id.Rejoindre);
        if(eventAdapter == null) {
            eventAdapter = new EventAdapter(getActivity(), events);
            lv.setAdapter(eventAdapter);
            eventAdapter.notifyDataSetChanged();
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("android.intent.action.CreateEvent");
                startActivity(intent);
            }
        });
        return view;
    }

        public void onCreateClicked(){
            //Intent intent=new Intent("android.intent.action.CreateEvent");
            //startActivity(intent);
            Log.d("TEST",String.valueOf(AccessData.events.size()));
        }

}
