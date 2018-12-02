package com.example.wemove;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        Notification notification = getItem(position);

        switch (notification.getType()) {
            case "Event" :
                imageView.setImageResource(R.drawable.ic_event_black_24dp);
                break;
            case "Group" :
                imageView.setImageResource(R.drawable.ic_group_black_24dp);
                break;
        }

        message.setText(notification.getMessage());
        String date_;
        if (AccessData.ageCalculator.getDifferenceDays(notification.getDate())==0) {
            date_="Aujourd'hui";
        }
        else if (AccessData.ageCalculator.getDifferenceDays(notification.getDate())==1){
            date_="Hier";
        }
        else {
            date_ = new StringBuilder().append("Il y a ").append(AccessData.ageCalculator.getDifferenceDays(notification.getDate())).append(" jours").toString();
        }
        date.setText(date_);

       return customView;
    }
}
