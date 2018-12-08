package profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.wemove.Home;
import com.example.wemove.Notification;
import com.example.wemove.R;
import com.example.wemove.Sport;
import com.example.wemove.WeMoveDB;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Utils.AccessData;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditingProfileActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE=1;
    private static final int REQUEST_IMAGE_CAPTURE=2;

    public static boolean isRunning=false;
    public static Context ctx;

    private List<Sport> sportItems;
    private List<Sport> sportToEdit;
    private Sport sportItem;
    private ImageView hideList;
    private ListView listSportView;
    private ListAdapter listAdapter;
    private Bitmap loadedBitmap;

    public static CircleImageView profilePicture;
    private EditText name_text;
    private EditText fname_text;
    private EditText bd_text;
    private EditText bio_content;
    private Uri uriUser;
    private Boolean isChanged = false;
    private Handler mHandler = new Handler();

    public boolean saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);


        // Init Content Variables
        initContentVariables();

        //Get Data from DataBase
        getData();

        //setSports();

        listSportView = (ListView) findViewById(R.id.listSportView);
        listAdapter = new ListSportAdapter(this,sportItems,sportToEdit);
        listSportView.setAdapter(listAdapter);
        setListViewSize();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }

    public void setSports () {
        sportItems.add(new Sport("Football","Intermédiaire","Détente",(float)3.5));
        sportItems.add(new Sport("Ping Pong","Débutant","Détente",1));
        sportItems.add(new Sport("Rugby","Confirmé","Tout",5));
        sportItems.add(new Sport("Natation","Intermédiaire","Sérieux",4));
        sportItems.add(new Sport("Volleyball","Débutant","Tout",2));
        sportItems.add(new Sport("Peche","Intermédiaire","Détente",4));
    }

    public void initContentVariables () {
        hideList = (ImageView)findViewById(R.id.hideList);
        ctx = getBaseContext();
        profilePicture = (CircleImageView)findViewById(R.id.profile_image);
        name_text = (EditText) findViewById(R.id.nomEditText);
        fname_text = (EditText) findViewById(R.id.prenomEditText);
        bd_text = (EditText) findViewById(R.id.dateEditText);
        bio_content = (EditText) findViewById(R.id.bioEditText);
        sportItems = new ArrayList<>();
        sportToEdit = new ArrayList<>();
        saved=false;
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
                    uriUser = uri;
                    Log.d("ok" ,"onActivityResult: "+uriUser);
                    isChanged = true;
                    break;
                }
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode==RESULT_OK) {
                    Uri uri = data.getData();
                    profilePicture.setImageURI(uri);
                    uriUser = uri;
                    Log.d("ok" ,"onActivityResult: "+uriUser);
                    isChanged = true;
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
                    sportItem = new Sport(nameSpinner.getSelectedItem().toString(),levelSpinner.getSelectedItem().toString(),styleSpinner.getSelectedItem().toString(),interestRating.getRating());
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
        name_text.setText(AccessData.currentUser.getName());
        fname_text.setText(AccessData.currentUser.getFirstname());
        bd_text.setText(AccessData.currentUser.getAge());
        bio_content.setText(AccessData.currentUser.getBio());
        for (int i=0;i<AccessData.currentUser.getSports().size();i++) {
            sportItems.add(new Sport(AccessData.currentUser.getSports().get(i).getName(),AccessData.currentUser.getSports().get(i).getLevel(),AccessData.currentUser.getSports().get(i).getType(),AccessData.currentUser.getSports().get(i).getInterest()));
        }
        AccessData.db.getPhoto();
    }

    //Save Data to DataBase
    public void saveData() {
        AccessData.db.deleteSports(sportToEdit);
        HashMap<String,Object> map = new HashMap<>();
        map.put("bio",bio_content.getText().toString());
        map.put("age",bd_text.getText().toString());
        map.put("firstname",fname_text.getText().toString());
        map.put("name", name_text.getText().toString());
        map.put("sports",sportItems);

        AccessData.db.updateUser(AccessData.currentUser,map);
        saved=true;
        if(isChanged == true) {
            AccessData.db.addPhoto(uriUser);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (saved) {
            AccessData.db.implementSports(AccessData.currentUser);

        }
        Log.d("test",AccessData.currentUser.getSports().toString());
        isRunning=false;
    }

    public void onConfirm (View view) {
        saveData();
        if(isChanged) {
            mHandler.postDelayed(mUpdateDisplay,2000);
            isChanged = false;
        } else {
            onBackPressed();
        }
    }

    private Runnable mUpdateDisplay = new Runnable() {
        @Override
        public void run() {
            onBackPressed();
        }
    };

    public void onDismiss (View view) {
        onBackPressed();
    }

    public List<String> getOtherSportsName () {
        List<String> otherSport = new ArrayList<String>();
        for ( String key : AccessData.table.keySet() ) {
            otherSport.add(key);
        }
        for (int i =0; i<sportItems.size();i++) {
            otherSport.remove(sportItems.get(i).getName());
        }
        return otherSport;
    }

    /*
    @Override
    public void onBackPressed() {

    }
    */

}
