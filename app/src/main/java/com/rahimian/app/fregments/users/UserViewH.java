package com.rahimian.app.fregments.users;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.rahimian.app.R;

public class UserViewH extends RecyclerView.ViewHolder  {

    private TextView name ,onprof ;
    private CircularImageView prof ;
    onItemClickListener ItemClickListener ;

    public UserViewH(@NonNull View itemView , onItemClickListener onItemClickListener) {
        super(itemView);

        ItemClickListener = onItemClickListener;
        name = itemView.findViewById(R.id.RVprofileName);
        prof = itemView.findViewById(R.id.RVprofile_img);
        onprof = itemView.findViewById(R.id.RVonprofName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemClickListener.onItemClick(getAdapterPosition());
            }
        });

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
interface onItemClickListener {
    void onItemClick(int position);
}
