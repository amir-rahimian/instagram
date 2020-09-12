package com.rahimian.app;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irozon.sneaker.Sneaker;
import com.parse.ParseUser;


public class Profile_fragment extends Fragment {

    private TextView name, username, id, phone, bio , passOnOrOff;
    private FloatingActionButton editBtn;
    private ParseUser User;
    private ImageView profIMG;
    private LinearLayout logout , changePass;
    public Profile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.profileName);
        username = view.findViewById(R.id.profileUsername);
        id = view.findViewById(R.id.idText);
        phone = view.findViewById(R.id.phoneText);
        bio = view.findViewById(R.id.bioText);
        editBtn = view.findViewById(R.id.editBtn);
        passOnOrOff = view.findViewById(R.id.passOnOrOff);
        profIMG = view.findViewById(R.id.profile_img);


        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sneaker sneaker =Sneaker.with(getActivity())
                        .autoHide(false);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.logout_dialog, sneaker.getView(), false);
                sneaker.sneakCustom(view);

            }
        });


        changePass = view.findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PassChangeActivity.class);
                startActivity(intent);
            }
        });


        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean haveid =false ;
                if (User.get("userID") == null){
                    editBtn.callOnClick();
                }else {
                    String idtex = id.getText().toString();
                    ClipData myClip = ClipData.newPlainText("id", idtex);
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setPrimaryClip(myClip);
                    Sneaker.with(getActivity())
                            .setTitle("Id copied ! ", R.color.colorAccent)
                            .setMessage(idtex, R.color.colorAccent)
                            .setCornerRadius(30)
                            .sneak(R.color.colorPrimaryDark);
                }
            }
        });

        User = ParseUser.getCurrentUser();

        getData();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), edituserActivity.class);
                Pair[] pairs = new Pair[] {
                        new Pair(profIMG, "profilePhoto"),
                };
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                startActivity(intent,activityOptions.toBundle());

            }
        });

        return view;
    }

    private void getData(){
        if (User.getBoolean("ispasschange")){passOnOrOff.setText("ON"); }else {passOnOrOff.setText("OFF"); }
        name.setText(User.get("name").toString());
        username.setText(((User.get("userID") == null) ? User.getUsername() : User.get("userID").toString()));
        id.setText(((User.get("userID") == null) ? "you don't set an id" : User.get("userID").toString()));
        phone.setText(User.getUsername());
        bio.setText(((User.get("bio") == null) ? "Bio" : User.get("bio").toString()));
    }



    @Override
    public void onStart() {
        getData();
        super.onStart();
    }
}
