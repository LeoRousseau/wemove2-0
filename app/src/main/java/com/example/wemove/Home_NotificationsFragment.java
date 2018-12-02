package com.example.wemove;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home_NotificationsFragment extends Fragment {

    ListView listView;
    ListNotifAdapter listNotifAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_fragment_notifications,container,false);


        List<Notification> list = new ArrayList<>();
        list.add(new Notification("Event","Football : Un nouvel évènement vous correspond ! ","0",new Date(2018,11,29)));
        list.add(new Notification("Group","Nouveau membre dans le groupe ","0",new Date(2018,11,20)));
        list.add(new Notification("Event","Ping Pong : Un nouvel évènement vous correspond ! ","0",new Date(2018,11,30)));
        list.add(new Notification("Event","Rugby : Un nouvel évènement vous correspond ! ","0",new Date(2018,11,11)));



        listView =  view.findViewById(R.id.listNotifView);
        listNotifAdapter = new ListNotifAdapter(getActivity(),list);
        listView.setAdapter(listNotifAdapter);

        return view;
    }
}
