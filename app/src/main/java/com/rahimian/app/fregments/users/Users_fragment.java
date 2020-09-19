package com.rahimian.app.fregments.users;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rahimian.app.R;

import java.util.ArrayList;
import java.util.List;


public class Users_fragment extends Fragment {

    private ArrayList<ParseUser> userArray;
    private LinearLayout loadarea;
    private TextView loadtxt, appname;
    private ImageView imgerror, logo;
    private RecyclerView userRV;
    private ProgressBar progresserror;
    private SearchView searchView;
    private UserRVAdapter userRVAdapter;


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
                getuser(null);
            }
        });


        return view;
    }



    public void getuser(@Nullable String s){
        userArray=new ArrayList<>();
        imgerror.setVisibility(View.GONE);
        progresserror.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);;
        loadtxt.setText("loading..");
        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        userParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        if (s!=null){userParseQuery.whereContains("name",s);}
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null&&objects!=null){
                    for (ParseUser user :
                            objects) {
                        userArray.add(user);
                    }
                    userRVAdapter = new UserRVAdapter(userArray,getContext());
                    userRV.setAdapter(userRVAdapter);
                    userRV.setVisibility(View.VISIBLE);
                    loadarea.setVisibility(View.GONE);
                    imgerror.setVisibility(View.GONE);

                }else {
                    imgerror.setVisibility(View.VISIBLE);
                    userRV.setVisibility(View.GONE);
                    loadarea.setVisibility(View.VISIBLE);
                    progresserror.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.error), android.graphics.PorterDuff.Mode.MULTIPLY);

                        loadtxt.setText("Check your Internet\nclick me to retry! ");

                    Log.e("ERROR", e.getMessage() + "[" + e.getCode() + "]");
                }
            }
        });
    }
    @Override
    public void onStart() {
        setHasOptionsMenu(true);
        getuser(null);
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.user_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userRVAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

}