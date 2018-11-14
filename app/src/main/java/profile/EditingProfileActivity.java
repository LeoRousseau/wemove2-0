package profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.wemove.R;

import java.util.ArrayList;
import java.util.List;

import Utils.SportIconsTable;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditingProfileActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE=1;
    private static final int REQUEST_IMAGE_CAPTURE=2;


    private List<SportItem> sportItems;
    private ImageView hideList;
    private ListView listSportView;
    private ListAdapter listAdapter;
    private Bitmap loadedBitmap;

    private CircleImageView profilePicture;
    private EditText name_text;
    private EditText fname_text;
    private EditText bd_text;
    private EditText bio_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);


        // Init Content Variables
        initContentVariables();

        //Get Data from DataBase
        getData();

        setSports();

        listSportView = (ListView) findViewById(R.id.listSportView);
        listAdapter = new ListSportAdapter(this,sportItems);
        listSportView.setAdapter(listAdapter);
        setListViewSize();
    }

    public void setSports () {
        sportItems.add(new SportItem("Football",(float)3.5,"Intermédiaire","Détente"));
        sportItems.add(new SportItem("Ping Pong",1,"Débutant","Détente"));
        sportItems.add(new SportItem("Rugby",5,"Confirmé","Tout"));
        sportItems.add(new SportItem("Natation",4,"Intermédiaire","Sérieux"));
        sportItems.add(new SportItem("Volleyball",2,"Débutant","Tout"));
        sportItems.add(new SportItem("Peche",4,"Intermédiaire","Détente"));
    }

    public void initContentVariables () {
        hideList = (ImageView)findViewById(R.id.hideList);

        profilePicture = (CircleImageView)findViewById(R.id.profile_image);
        name_text = (EditText) findViewById(R.id.nomEditText);
        fname_text = (EditText) findViewById(R.id.prenomEditText);
        bd_text = (EditText) findViewById(R.id.dateEditText);
        bio_content = (EditText) findViewById(R.id.bioEditText);
        sportItems = new ArrayList<>();
    }

    public void takePicture (View view) {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }
    }

    public void openPicture (View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE :
                if(resultCode==RESULT_OK && data!=null) {
                    Uri uri = data.getData();
                    profilePicture.setImageURI(uri);
                    break;
                }
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode==RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");
                    profilePicture.setImageBitmap(photo);
                    break;
                }
        }
    }

    public void hideSportList (View view) {
        if (listSportView.getVisibility()==View.VISIBLE) {
            hideList.setImageResource(R.drawable.down);
            listSportView.setVisibility(View.GONE);
        }
        else {
            hideList.setImageResource(R.drawable.up);
            listSportView.setVisibility(View.VISIBLE);
        }
        setListViewSize ();
    }

    public void setListViewSize () {
        ListAdapter listadp = listSportView.getAdapter();
        if (listadp != null) {
            int totalHeight = 0;
            for (int i = 0; i < listadp.getCount(); i++) {
                View listItem = listadp.getView(i, null, listSportView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listSportView.getLayoutParams();
            params.height = totalHeight + (listSportView.getDividerHeight() * (listadp.getCount() - 1));
            listSportView.setLayoutParams(params);
            listSportView.requestLayout();
        }
    }

    public void addSport (View cview) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.add_sport,null);
            builder.setTitle("Nouveau sport");

            final Spinner nameSpinner = (Spinner) view.findViewById(R.id.namesSpinner);
            final Spinner levelSpinner = (Spinner) view.findViewById(R.id.levelSpinner);
            final Spinner styleSpinner = (Spinner) view.findViewById(R.id.styleSpinner);
            final RatingBar interestRating = (RatingBar) view.findViewById(R.id.interestEdit);
            String[] levels = {"Débutant","Intermédiaire","Confirmé"};
            String[] styles = {"Détente","Sérieux","Compétition","Tout"};
            List<String> names = getOtherSportsName();
            ArrayAdapter<String> adapterName = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,names);
            adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nameSpinner.setAdapter(adapterName);
            ArrayAdapter<String> adapterLevel = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,levels);
            adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            levelSpinner.setAdapter(adapterLevel);
            ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,styles);
            adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            styleSpinner.setAdapter(adapterStyle);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SportItem sportItem = new SportItem(nameSpinner.getSelectedItem().toString(),interestRating.getRating(),levelSpinner.getSelectedItem().toString(),styleSpinner.getSelectedItem().toString());
                    sportItems.add(0,sportItem);
                    ((BaseAdapter)listAdapter).notifyDataSetChanged();
                    setListViewSize();

                }
            });

            builder.setNegativeButton("Anuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
    }

    //Get Data from DataBase
    public void getData() {

    }

    //Save Data to DataBase
    public void saveData() {

    }

    public void onConfirm (View view) {
        saveData();
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    public void onDismiss (View view) {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    public List<String> getOtherSportsName () {
        List<String> otherSport = new ArrayList<String>();
        for ( String key : SportIconsTable.table.keySet() ) {
            otherSport.add(key);
        }
        for (int i =0; i<sportItems.size();i++) {
            otherSport.remove(sportItems.get(i).getSportName());
        }
        return otherSport;
    }
}
