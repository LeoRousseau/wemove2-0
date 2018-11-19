package com.example.wemove;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import profile.ListSportAdapter;
import profile.ProfileActivity;

public class Home_MenuFragment extends Fragment {

    ListView listView;
    private ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_fragment_menu,container,false);

        listView = (ListView) view.findViewById(R.id.menuListView);

        listAdapter = new ListMenuAdapter(getActivity());
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 :
                        goToProfile();
                        break;
                    case 1 :
                        break;
                    case 2 :
                        goToMySports();
                        break;
                    case 3 :
                        break;
                    case 4 :
                        break;
                    case 5 :
                        break;
                    case 6 :
                        goToCredits();
                        break;
                    case 7 :
                        signOut();
                        break;
                }
            }
        });
        return view;
    }
    public void goToProfile () {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void goToCredits () {
        Intent intent = new Intent(getContext(), CreditActivity.class);
        startActivity(intent);
    }

    public void goToMySports() {
        Intent intent = new Intent(getContext(), MySportsActivity.class);
        startActivity(intent);
    }

    public void signOut () {
        Log.d("ATTEMPT", "onClick: attempting to sign out the user");
        FirebaseAuth.getInstance().signOut();
    }
}
