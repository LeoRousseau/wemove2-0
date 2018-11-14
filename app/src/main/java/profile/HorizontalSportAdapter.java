package profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.wemove.R;
import com.example.wemove.Sport;

import java.util.List;

import Utils.SportIconsTable;

class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView sportName;
    public ImageView sportImage;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        sportName = (TextView) itemView.findViewById(R.id.textSport);
        sportImage= (ImageView) itemView.findViewById(R.id.imageSport);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClik(v,getAdapterPosition());
    }
}



public class HorizontalSportAdapter extends RecyclerView.Adapter<CustomViewHolder> {


    LinearLayout linearLayout;
    TextView sportTitle;
    TextView level;
    RatingBar interest;
    TextView type;
    List<Sport> items;
    Context context;
    int rowindex =-1; //Default case

    public HorizontalSportAdapter(List<Sport> items, Context context, LinearLayout linearLayout) {
        this.items = items;
        this.context = context;
        this.linearLayout = linearLayout;

        sportTitle = (TextView) linearLayout.findViewById(R.id.sportTitle);
        level = (TextView)linearLayout.findViewById(R.id.levelSelected);
        interest = (RatingBar)linearLayout.findViewById(R.id.interestSelected);
        type = (TextView)linearLayout.findViewById(R.id.typeSelected);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.profile_sportitem,viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.sportName.setText(items.get(i).getName());
        customViewHolder.sportImage.setImageResource(SportIconsTable.table.get(items.get(i).getName()));

        customViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClik(View view, int position) {

                if (rowindex==position) {
                    rowindex=-1;
                    CommonSportItem.currentsportItem=null;
                    notifyDataSetChanged();
                }
                else {
                    rowindex=position;
                    CommonSportItem.currentsportItem=items.get(position);
                    notifyDataSetChanged();
                    setSelectedInformation(items.get(position));
                }
            }
        });

        if (rowindex==i) {
            customViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.OrangeNormal));
        }
        else {
            customViewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.sportcardview));
        }

        if (rowindex==-1) {
            linearLayout.setVisibility(View.GONE);
        }
        else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setSelectedInformation (Sport sportItem) {
        sportTitle.setText(sportItem.getName());
        interest.setRating(sportItem.getInterest());
        type.setText(sportItem.getType());
        level.setText(sportItem.getLevel());
    }

}
