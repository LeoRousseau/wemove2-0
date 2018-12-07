package com.example.wemove;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Utils.AccessData;
import profile.EditingProfileActivity;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> items;
    LayoutInflater inflater;
    ArrayList<Event> arrayList;

    public EventAdapter(@NonNull Context context, List<Event> items) {
        super(context, R.layout.eventlayout);
        this.context=context;
        this.items=items;
        inflater = LayoutInflater.from(context);
        arrayList=new ArrayList<Event>();
        arrayList.addAll(items);
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
        final View customView= layoutInflater.inflate(R.layout.eventlayout,parent,false);
        ImageView si=(ImageView)customView.findViewById(R.id.si);
        TextView textViewNom = (TextView) customView.findViewById(R.id.textViewnom);
        TextView textViewSport= (TextView) customView.findViewById(R.id.textViewSport);
        TextView textViewNumber = (TextView)customView.findViewById(R.id.textViewNumber);


        final Event event = getItem(position);
        final Button join = customView.findViewById(R.id.Rejoindre);

        textViewNumber.setText(event.usersID.size() + "/" + event.getNbPeople());


        if (AccessData.currentUser.getId().compareTo(event.usersID.get(0))==0) {
            join.setText("GERER");
            join.setTextColor(context.getResources().getColor(R.color.blue));
        }
        else if (isJoined(position)){
            join.setText("QUITTER");
            join.setTextColor(context.getResources().getColor(R.color.OrangeNormal));
        }
        else if (event.getNbPeople()<=event.usersID.size()) {
            join.setText("COMPLET");
            join.setTextColor(context.getResources().getColor(R.color.notifRed));
        }
        else {
            join.setText("REJOINDRE");
            join.setTextColor(context.getResources().getColor(R.color.OrangeNormal));
        }






        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AccessData.events.get(position).usersID.get(0).compareTo(AccessData.currentUser.getId())==0) {
                    //GERER
                }
                else if (isJoined(position)){
                    Log.d("changed","1");
                    ArrayList<String> list = new ArrayList<>();
                    for (int i=0;i<AccessData.events.get(position).usersID.size();i++) {
                        list.add(new String(AccessData.events.get(position).usersID.get(i)));
                    }
                    int i;
                    for (i=0;i<list.size();i++) {
                        if (list.get(i).compareTo(AccessData.currentUser.getId())==0) {
                            list.remove(i);
                            break;
                        }
                    }
                    Map<String, Object> eventUpdates = new HashMap<>();
                    eventUpdates.put("usersID",list);
                    AccessData.db.updateEvent(event,eventUpdates);
                    join.setText("REJOINDRE");
                    Log.d("changed",join.getText().toString());
                    join.setTextColor(context.getResources().getColor(R.color.OrangeNormal));

                }
                else if (event.getNbPeople()<=event.usersID.size()) {
                    //COMPLET
                }
                else {
                    Log.d("changed","2");
                    Map<String, Object> eventUpdates = new HashMap<>();
                    ArrayList<String> list = new ArrayList<>();
                    for (int i=0;i<AccessData.events.get(position).usersID.size();i++) {
                        list.add(new String(AccessData.events.get(position).usersID.get(i)));
                    }
                    list.add(AccessData.currentUser.getId());
                    eventUpdates.put("usersID",list);
                    AccessData.db.updateEvent(event,eventUpdates);
                    join.setText("QUITTER");
                    Log.d("changed",join.getText().toString());
                    join.setTextColor(context.getResources().getColor(R.color.OrangeNormal));
                }

            }
        });

        si.setImageResource(AccessData.table.get(event.getSport().getName()));
        textViewNom.setText(event.getName());
        textViewSport.setText(event.getSport().getName());

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //a coder
                Intent intent=new Intent(context, EventDetails.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        return customView;
    }


    public boolean isJoined(int position) {
        boolean result=false;
        if (AccessData.events.get(position).usersID.size()>1) {
            for (int i=1;i<AccessData.events.get(position).usersID.size();i++) {
                if(AccessData.events.get(position).usersID.get(i).compareTo(AccessData.currentUser.getId())==0) {
                    result  = true;
                }
            }
        }
        return result;
    }
}
