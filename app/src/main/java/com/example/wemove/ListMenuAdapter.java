package com.example.wemove;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Utils.AccessData;
import Utils.MenuItem;

public class ListMenuAdapter extends ArrayAdapter<MenuItem> {

    private ImageView imageView;
    private TextView textView;
    private Context context;

    public ListMenuAdapter(@NonNull Context context) {
        super(context,R.layout.menu_row);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View customView= layoutInflater.inflate(R.layout.menu_row,parent,false);

        MenuItem menuItem = getItem(position);

        imageView = (ImageView)customView.findViewById(R.id.iconMenuItem);
        textView = (TextView)customView.findViewById(R.id.textMenuItem);
        textView.setText(menuItem.getText());
        imageView.setImageResource(menuItem.getIcon());

        return customView;
    }

    @Override
    public int getCount() {
        return AccessData.menuItems.size();

    }

    @Override
    public MenuItem getItem(int position) {
        return AccessData.menuItems.get(position);
    }
}
