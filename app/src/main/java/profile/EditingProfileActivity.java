package profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wemove.Home;
import com.example.wemove.Login;
import com.example.wemove.NewUser;
import com.example.wemove.Notification;
import com.example.wemove.R;
import com.example.wemove.Sport;
import com.example.wemove.WeMoveDB;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static CircleImageView profilePicture;
    private EditText name_text;
    private EditText fname_text;
    private Button bdBtn;
    private EditText bio_content;
    private Uri uriUser;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Date date=null;
    private Boolean isChanged = false;
    private Handler mHandler = new Handler();

    public boolean saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);


        // Init Content Variables
        initContentVariables();

        date = new Date(AccessData.currentUser.getAge());
        bdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();


                DatePickerDialog dialog = new DatePickerDialog(
                        EditingProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date=new Date(year-1900,month,dayOfMonth);
                bdBtn.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/" + String.valueOf(year));
            }
        };


        //Get Data from DataBase
        getData();

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


    public void initContentVariables () {
        hideList = (ImageView)findViewById(R.id.hideList);
        ctx = getBaseContext();
        profilePicture = (CircleImageView)findViewById(R.id.profile_image);
        name_text = (EditText) findViewById(R.id.nomEditText);
        fname_text = (EditText) findViewById(R.id.prenomEditText);
        bdBtn = (Button) findViewById(R.id.dateBtn);
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
                    Login.isNew=false;
                    isChanged = true;
                    break;
                }
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode==RESULT_OK) {
                    Uri uri = data.getData();
                    profilePicture.setImageURI(uri);
                    uriUser = uri;
                    Log.d("ok" ,"onActivityResult: "+uriUser);
                    Login.isNew=false;
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
            String[] levels = {"D??butant","Interm??diaire","Confirm??"};
            String[] styles = {"D??tente","S??rieux","Comp??tition","Tout"};
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
        Date date_ = new Date(AccessData.currentUser.getAge());
        String date = String.valueOf(date_.getDate()) + "/" + String.valueOf(date_.getMonth()+1) + "/" + String.valueOf(date_.getYear()+1900);
        bdBtn.setText(date);
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
        map.put("age",date.getTime());
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
        isRunning=false;
    }

    public void onConfirm (View view) {
        saveData();
        if(isChanged) {
            mHandler.postDelayed(mUpdateDisplay,2000);
            isChanged = false;
        } else {
            Intent intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
    }

    private Runnable mUpdateDisplay = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getBaseContext(),ProfileActivity.class);
            startActivity(intent);
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

}
