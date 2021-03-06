package com.example.wemove;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utils.AccessData;

public class MySportAdapter extends BaseAdapter {

    List<Sport> items;
    Context context;
    List<Boolean> lb;

    public MySportAdapter(Context context, User user) {
        this.context=context;
        this.items=user.sports;
        this.lb=new ArrayList<>();
        if (items!=null) {
            for (int i = 0; i < items.size(); i++) {
                lb.add(false);
            }
        }
    }

    public void setInput (User user) {
        this.items=user.sports;
        if (items!=null) {
            for (int i = 0; i < items.size(); i++) {
                lb.add(false);
            }
        }
    }

    @Override
    public int getCount() {
        if (items==null) {
            return 0;
        }
        return items.size();
    }

    @Override
    public Sport getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        boolean isSup=false;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customView= layoutInflater.inflate(R.layout.gridview_sport_item,parent,false);

        final Sport sport = getItem(position);
        TextView sportname = (TextView)customView.findViewById(R.id.textSportGV);
        ImageView sportimage = (ImageView)customView.findViewById(R.id.imageSportGV);
        CardView cardView = (CardView) customView.findViewById(R.id.cardViewSportGV);
        RatingBar ratingBar = (RatingBar)customView.findViewById(R.id.ratingBarMySport);
        TextView level = (TextView)customView.findViewById(R.id.levelMySport);
        TextView style = (TextView)customView.findViewById(R.id.styleMySport);
        final RelativeLayout view = (RelativeLayout) customView.findViewById(R.id.viewGV);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lb.get(position)) {
                    view.animate().translationY(-view.getHeight());
                    lb.set(position,true);
                }
                else {
                    view.animate().translationY(0);
                    lb.set(position,false);
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openEditDialog(sport);
                notifyDataSetChanged();
                return true;
            }
        });


        sportname.setText(sport.getName());
        sportimage.setImageResource(AccessData.table.get(sport.getName()));
        level.setText(sport.getLevel());
        style.setText(sport.getType());
        ratingBar.setRating(sport.getInterest());


        return customView;
    }


    public void openEditDialog(final Sport sportItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.edit_sport,null);
        builder.setTitle(sportItem.getName());

        final Spinner levelSpinner = (Spinner) view.findViewById(R.id.levelSpinner);
        final Spinner styleSpinner = (Spinner) view.findViewById(R.id.styleSpinner);
        final RatingBar interestRating = (RatingBar) view.findViewById(R.id.interestEdit);
        String[] levels = {"D??butant","Interm??diaire","Confirm??"};
        String[] styles = {"D??tente","S??rieux","Comp??tition","Tout"};
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,levels);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapterLevel);
        ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,styles);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(adapterStyle);

        interestRating.setRating(sportItem.getInterest());
        //hello

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Sport> list = new ArrayList<>();
                list.add(new Sport(sportItem.getName(),sportItem.getLevel(),"",0));
                AccessData.db.deleteSports(list);
                sportItem.setInterest(interestRating.getRating());
                sportItem.setLevel(levelSpinner.getSelectedItem().toString());
                sportItem.setType(styleSpinner.getSelectedItem().toString());
                HashMap<String,Object> map = new HashMap<>();
                map.put("sports",items);
                AccessData.db.updateUser(AccessData.currentUser,map);
                AccessData.db.implementSports(AccessData.currentUser);
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Sport> list = new ArrayList<>();
                for (int i=0; i<items.size();i++) {
                    if (items.get(i).getName().compareTo(sportItem.getName())==0) {
                        list.add(new Sport(sportItem.getName(),sportItem.getLevel(),"",0));
                        items.remove(i);
                        break;
                    }
                }
                AccessData.db.deleteSports(list);
                HashMap<String,Object> map = new HashMap<>();
                map.put("sports",items);
                AccessData.db.updateUser(AccessData.currentUser,map);
                AccessData.db.implementSports(AccessData.currentUser);




            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
