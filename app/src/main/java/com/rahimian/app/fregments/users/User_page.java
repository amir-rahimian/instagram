package com.rahimian.app.fregments.users;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rahimian.app.R;
import com.rahimian.app.swipeInterface.SwipeActions;
import com.rahimian.app.swipeInterface.SwipeGestureDetector;

import static com.rahimian.app.fregments.profile.edituserActivity.getBitmapFromVectorDrawable;

public class User_page extends AppCompatActivity {

    private GestureDetectorCompat gestureDetectorCompat;
    private ConstraintLayout root ;

    private ProgressBar cirprog,lineprog;
    private CircularImageView prof_img;
    private Toolbar userpage_toolbar;
    private TextView name , bio , onprof , topname;
    private ImageView btn_back;
    private ParseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        root = findViewById(R.id.UserAct);
        lineprog = findViewById(R.id.lineprog); lineprog.setIndeterminate(true);
        cirprog = findViewById(R.id.cirprog);
        prof_img = findViewById(R.id.profile_img);
        userpage_toolbar = findViewById(R.id.userpage_toolbar);setSupportActionBar(userpage_toolbar);setTitle("");
        onprof = findViewById(R.id.onprofName);
        name = findViewById(R.id.profileName);
        topname = findViewById(R.id.txt_top_name);
        bio = findViewById(R.id.userBio);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String userid =  getIntent().getExtras().getString("user");

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(userid, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e==null){
                    User=object;
                    getData();
                    lineprog.setVisibility(View.GONE);cirprog.setVisibility(View.GONE);
                }
                else {
                    e.printStackTrace();
                }
            }
        });



        //swipe implementation
        SwipeGestureDetector swipeGestureDetector = new SwipeGestureDetector(new SwipeActions() {
            @Override
            public void onSwipeLeft() {
            }

            @Override
            public void onSwipeRight() {
                onBackPressed();
            }

            @Override
            public void onSwipeUp() {
            }

            @Override
            public void onSwipeDown() {
            }
        });
        gestureDetectorCompat = new GestureDetectorCompat(getApplicationContext(), swipeGestureDetector);
        root.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return true;
            }
        });
    }


    private void getData() {
        name.setText(User.get("name").toString());
        name.setVisibility(View.VISIBLE);
        topname.setText(((User.get("userID") == null) ? User.getUsername() : User.get("userID").toString().replace("@"," ")));
        if (User.getBoolean("haveprofile")){
            onprof.setText("");
            ParseFile parseFile = User.getParseFile("profilephoto");
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    prof_img.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
                }
            });
        }else {
            prof_img.setImageBitmap(getBitmapFromVectorDrawable(User_page.this, R.drawable.ob_pro));
            onprof.setText(name.getText());
        }
        if (User.get("bio") != null){
            bio.setVisibility(View.VISIBLE);
            bio.setText(User.get("bio").toString());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Coming Soon!");builder.setMessage("this future in not available in this version!");
        builder.setPositiveButton("OK",null);
        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return super.onOptionsItemSelected(item);
    }
}