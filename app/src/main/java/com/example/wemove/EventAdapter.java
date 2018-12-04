package com.example.wemove;

import android.content.Context;
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
import java.util.List;
import java.util.Map;

import Utils.AccessData;
import profile.EditingProfileActivity;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> items;

    public EventAdapter(@NonNull Context context, List<Event> items) {
        super(context, R.layout.eventlayout);
        this.context=context;
        this.items=items;
    }

    @Override
    public Event getItem(int position) {
        return items.get(position);
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

        TextView textViewNom = (TextView) customView.findViewById(R.id.textViewnom);
        TextView textViewDesc = (TextView) customView.findViewById(R.id.textViewDescription);
        TextView textViewSport= (TextView) customView.findViewById(R.id.textViewSport);
        TextView textViewPlace = (TextView) customView.findViewById(R.id.textViewPlace);
        TextView textViewNiveau = (TextView) customView.findViewById(R.id.textViewNiveau);

        final Event event = getItem(position);
        Button join = customView.findViewById(R.id.Rejoindre);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> eventUpdates = new HashMap<>();
                eventUpdates.put(AccessData.currentUser.getName(),AccessData.currentUser.getId());
                AccessData.db.updateEvent(event,eventUpdates);
            }
        });

        textViewNom.setText(event.getName());
        textViewDesc.setText(event.getDescription());
        textViewNiveau.setText(event.getNiveau());
        textViewPlace.setText(event.getPlace());
        textViewSport.setText(event.getSport().getName());


        return customView;
    }
}
