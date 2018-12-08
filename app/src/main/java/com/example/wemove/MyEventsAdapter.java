package com.example.wemove;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utils.AccessData;

public class MyEventsAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> items;
    public int type;
    LayoutInflater inflater;
    ArrayList<Event> arrayList;

    public MyEventsAdapter(@NonNull Context context, List<Event> items, int val) {
        super(context, R.layout.myevents_layout);
        this.context=context;
        this.items=items;
        inflater = LayoutInflater.from(context);
        arrayList=new ArrayList<Event>();
        arrayList.addAll(items);
        type=val;
    }


    @Override
    public Event getItem(int position) {
        return items.get(position);
    }


    public void setInput(List<Event> items) {
        this.items=items;
    }


    @Override
    public int getCount() {
        return items.size();

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View customView = layoutInflater.inflate(R.layout.myevents_layout, parent, false);

        ImageView si=(ImageView)customView.findViewById(R.id.si);
        TextView textViewNom = (TextView) customView.findViewById(R.id.textViewnom);
        TextView textViewSport= (TextView) customView.findViewById(R.id.textViewSport);
        TextView textViewNumber = (TextView)customView.findViewById(R.id.textViewNumber);

        final Event event = getItem(position);

        textViewNumber.setText(event.usersID.size() + "/" + event.getNbPeople());

        si.setImageResource(AccessData.table.get(event.getSport().getName()));
        textViewNom.setText(event.getName());
        textViewSport.setText(event.getSport().getName());

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a coder
                Intent intent=new Intent(context, EventDetails.class);
                intent.putExtra("position", getPos(event));
                context.startActivity(intent);
            }
        });
        return customView;
    }


    public int getPos(Event event) {
        for (int i=0;i<AccessData.events.size();i++) {
            if (AccessData.events.get(i).getId().compareTo(event.getId())==0) {
                return i;
            }
        }
        return 0;
    }





}
