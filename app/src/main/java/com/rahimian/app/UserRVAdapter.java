package com.rahimian.app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;

import static com.rahimian.app.edituserActivity.getBitmapFromVectorDrawable;

public class UserRVAdapter extends RecyclerView.Adapter<UserViewH> {

    private ArrayList list ;
    private Context context;
    public UserRVAdapter(ArrayList list ,Context context) {
        this.list = list;
        this.context =context;
    }

    @NonNull
    @Override
    public UserViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout,parent,false);

        return new UserViewH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewH holder, int position) {
        ParseUser parseUser = (ParseUser) list.get(position);
        holder.getName().setText(parseUser.getString("name"));
        if (parseUser.getBoolean("haveprofile")){
            holder.getOnprof().setText("");
            ParseFile parseFile = parseUser.getParseFile("profilephoto");
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    holder.getProf().setImageBitmap( BitmapFactory.decodeByteArray(data,0,data.length));
                }
            });
        }else {
            holder.getProf().setImageBitmap(getBitmapFromVectorDrawable(context, R.drawable.ob_pro));
            holder.getOnprof().setText(parseUser.getString("name"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
