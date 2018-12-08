package com.example.wemove;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Utils.AccessData;

public class Home_EventsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    public static ListView lv=null;
    private SearchView searchView;
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
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.home_fragment_events, container, false);
        progressBarEvents = (ProgressBar)view.findViewById(R.id.progressbarEvents);
        //eventAdapter=null;
        lv = (ListView) view.findViewById(R.id.listEventsView);
        create = (FloatingActionButton) view.findViewById(R.id.Create);

        lv.setDivider(null);
        lv.setDividerHeight(0);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menusearch, menu);
        android.view.MenuItem item = menu.findItem(R.id.action_search);

        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("val","close");
                eventAdapter.setTrue();
                eventAdapter.notifyDataSetChanged();
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if(s.equals("") || s.trim().isEmpty()) {
            Toast.makeText(getContext(),"Aucune valeur indiquée",Toast.LENGTH_LONG).show();
        }
        else {
            for(int i=0;i<AccessData.events.size();i++) {
                if (AccessData.events.get(i).sport.getName().toLowerCase().contains(s.toLowerCase())) {
                }
                else {
                    eventAdapter.displayed.set(i,false);
                    eventAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        eventAdapter.setTrue();
        eventAdapter.notifyDataSetChanged();
        if(s.equals("") || s.trim().isEmpty()) {

        }
        else {
            for(int i=0;i<AccessData.events.size();i++) {
                if (AccessData.events.get(i).sport.getName().toLowerCase().contains(s.toLowerCase())) {
                }
                else {
                    eventAdapter.displayed.set(i,false);
                    eventAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
    }


}
