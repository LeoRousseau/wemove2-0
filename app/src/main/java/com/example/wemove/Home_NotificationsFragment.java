package com.example.wemove;

import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Utils.AccessData;
import Utils.NotificationComparator;

public class Home_NotificationsFragment extends Fragment {

    ListView listView;
    TextView noNotif;
    ListNotifAdapter listNotifAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_fragment_notifications,container,false);

        noNotif = view.findViewById(R.id.noNotif);
        listView =  view.findViewById(R.id.listNotifView);

        if (AccessData.currentUser.notifications!=null) {
            noNotif.setVisibility(View.GONE);
            ArrayList <Notification> notifs = new ArrayList<Notification>(AccessData.currentUser.notifications.values());
            Collections.sort(notifs,new NotificationComparator());
            Collections.reverse(notifs);
            listNotifAdapter = new ListNotifAdapter(getActivity(), notifs);
            listView.setAdapter(listNotifAdapter);
            clearNotif();
        }
        return view;
    }



    void clearNotif() {
        if (AccessData.currentUser.notifications != null) {
            for (String key : AccessData.currentUser.notifications.keySet()) {
                AccessData.currentUser.notifications.get(key).setSeen(true);
            }
            HashMap<String,Object> map = new HashMap<>();
            map.put("notifications",AccessData.currentUser.notifications);
            AccessData.db.updateUser(AccessData.currentUser,map);
            Home.notificationBadge.setVisibility(View.GONE);
        }

    }
}
