package com.rahimian.app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class UserViewH extends RecyclerView.ViewHolder {

    private TextView name ,onprof ;
    private CircularImageView prof ;

    public UserViewH(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.RVprofileName);
        prof = itemView.findViewById(R.id.RVprofile_img);
        onprof = itemView.findViewById(R.id.RVonprofName);

    }

    public TextView getOnprof() {
        return onprof;
    }

    public TextView getName() {
        return name;
    }

    public CircularImageView getProf() {
        return prof;
    }
}
