package com.rahimian.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class Users_fragment extends Fragment {

    private ArrayList<ParseUser> userArray;
    private LinearLayout loadarea;
    private TextView loadtxt;
    private ImageView imgerror;
    private RecyclerView userRV;
    private ProgressBar progresserror;

    public Users_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        loadarea = view.findViewById(R.id.loadarea);
        loadtxt = view.findViewById(R.id.loadtext);
        imgerror = view.findViewById(R.id.imgerror);
        userRV = view.findViewById(R.id.userRV);
        progresserror = view.findViewById(R.id.progerror);
        userRV.setLayoutManager(new LinearLayoutManager(getContext()));
        userArray = new ArrayList<>();

        loadarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getuser();
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        getuser();
        super.onStart();
    }

    public void getuser(){
        userArray.removeAll(userArray);
        imgerror.setVisibility(View.GONE);
        progresserror.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);;
        loadtxt.setText("loading..");
        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        userParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null&&objects!=null){
                    for (ParseUser user :
                            objects) {
                        userArray.add(user);
                    }
                    userRV.setAdapter(new UserRVAdapter(userArray));
                    userRV.setVisibility(View.VISIBLE);
                    loadarea.setVisibility(View.GONE);
                    imgerror.setVisibility(View.GONE);

                }else {
                    imgerror.setVisibility(View.VISIBLE);
                    userRV.setVisibility(View.GONE);
                    loadarea.setVisibility(View.VISIBLE);
                    progresserror.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.error), android.graphics.PorterDuff.Mode.MULTIPLY);;

                        loadtxt.setText("Check your Internet\nclick me to retry! ");

                    Log.e("ERROR", e.getMessage() + "[" + e.getCode() + "]");
                }
            }
        });
    }
}