package com.example.wemove;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import Utils.AccessData;

public class ListNotifAdapter extends ArrayAdapter<Notification> {

    private List<Notification> list;
    private Context context;
    public ListNotifAdapter(@NonNull Context context, List<Notification> list) {
        super(context,R.layout.notifications_row);
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Notification getItem(int position) {
        return this.list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View customView= layoutInflater.inflate(R.layout.notifications_row,parent,false);

        ImageView imageView = customView.findViewById(R.id.imageNotif);
        TextView message = customView.findViewById(R.id.msgNotif);
        TextView date = customView.findViewById(R.id.dateNotif);

        final Notification notification = getItem(position);

        switch (notification.getType()) {
            case "Event" :
                imageView.setImageResource(R.drawable.ic_event_black_24dp);
                break;
            case "Group" :
                imageView.setImageResource(R.drawable.ic_group_black_24dp);
                break;
        }

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean found=false;
                Intent intent = new Intent(context,EventDetails.class);
                for (int i=0;i<AccessData.events.size();i++) {
                    if (AccessData.events.get(i).getId().compareTo(notification.getContentID())==0) {
                        intent.putExtra("nom", AccessData.events.get(i).getName());
                        intent.putExtra("sport", AccessData.events.get(i).getSport().getName());
                        intent.putExtra("niveau",AccessData.events.get(i).getNiveau());
                        intent.putExtra("place",AccessData.events.get(i).getPlace());
                        intent.putExtra("description", AccessData.events.get(i).getDescription());
                        context.startActivity(intent);
                        found=true;
                    }
                }
                if (!found) {
                    Toast.makeText(context,"Evenement introuvable",Toast.LENGTH_SHORT).show();
                }

            }
        });

        message.setText(notification.getMessage());
        String date_;
        if (AccessData.ageCalculator.getDifferenceDays(notification.getDate())<=0) {
            date_="Aujourd'hui";
        }
        else if (AccessData.ageCalculator.getDifferenceDays(notification.getDate())==1){
            date_="Hier";
        }
        else if (AccessData.ageCalculator.getDifferenceDays(notification.getDate())>30) {
            date_="Il y a plus d'un mois";
        }
        else {
            date_ = new StringBuilder().append("Il y a ").append(AccessData.ageCalculator.getDifferenceDays(notification.getDate())).append(" jours").toString();
        }
        date.setText(date_);

       return customView;
    }
}