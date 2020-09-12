package com.rahimian.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserViewH> {

    private ArrayList list ;
    public UserRVAdapter(ArrayList list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout,parent,false);

        return new UserViewH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewH holder, int position) {
        ParseUser parseUser = (ParseUser) list.get(position);
        holder.getName().setText(parseUser.getString("name"));
//        holder.getProf().setBackground();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
