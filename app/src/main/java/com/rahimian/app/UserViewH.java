package com.rahimian.app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class UserViewH extends RecyclerView.ViewHolder {

    private TextView name ;
    private CircularImageView prof ;

    public UserViewH(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.RVprofileName);
        prof = itemView.findViewById(R.id.RVprofile_img);

    }

    public TextView getName() {
        return name;
    }

    public CircularImageView getProf() {
        return prof;
    }
}
