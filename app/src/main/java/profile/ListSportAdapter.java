package profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wemove.R;
import com.example.wemove.Sport;

import java.util.List;

import Utils.AccessData;


public class ListSportAdapter extends ArrayAdapter<Sport> {

    private Context context;
    private List<Sport> items;




    public ListSportAdapter(@NonNull Context context, List<Sport> items) {
        super(context, R.layout.editingprofile_sportrow);
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View customView= layoutInflater.inflate(R.layout.editingprofile_sportrow,parent,false);

        Sport sportItem = getItem(position);

        ImageView sportIcon = (ImageView)customView.findViewById(R.id.imageSportRow);
        TextView sportName = (TextView)customView.findViewById(R.id.textSportRow);
        sportName.setText(sportItem.getName());
        sportIcon.setImageResource(AccessData.table.get(sportItem.getName()));
        ImageView editSport = (ImageView)customView.findViewById(R.id.editSportIcon);
        ImageView deleteSport = (ImageView)customView.findViewById(R.id.deleteSportIcon);


        deleteSport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
                ((EditingProfileActivity)context).setListViewSize();
            }
        });

        editSport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openEditDialog(items.get(position));
                notifyDataSetChanged();
            }
        });





        return customView;
    }

    @Override
    public int getCount() {
        return items.size();

    }

    public void setItems(List<Sport> items) {
        this.items = items;
    }

    @Override
    public Sport getItem(int position) {
        return items.get(position);
    }

    public void openEditDialog(final Sport sportItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.edit_sport,null);
        builder.setTitle(sportItem.getName());

        final Spinner levelSpinner = (Spinner) view.findViewById(R.id.levelSpinner);
        final Spinner styleSpinner = (Spinner) view.findViewById(R.id.styleSpinner);
        final RatingBar interestRating = (RatingBar) view.findViewById(R.id.interestEdit);
        String[] levels = {"Débutant","Intermédiaire","Confirmé"};
        String[] styles = {"Détente","Sérieux","Compétition","Tout"};
        ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,levels);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapterLevel);
        ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,styles);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(adapterStyle);

        interestRating.setRating(sportItem.getInterest());

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sportItem.setInterest(interestRating.getRating());
                sportItem.setLevel(levelSpinner.getSelectedItem().toString());
                sportItem.setType(styleSpinner.getSelectedItem().toString());
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
