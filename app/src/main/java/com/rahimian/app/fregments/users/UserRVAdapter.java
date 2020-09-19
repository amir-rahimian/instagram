package com.rahimian.app.fregments.users;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irozon.sneaker.Sneaker;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.rahimian.app.R;

import java.util.ArrayList;
import java.util.Collection;

import static com.rahimian.app.fregments.profile.edituserActivity.getBitmapFromVectorDrawable;

public class UserRVAdapter extends RecyclerView.Adapter<UserViewH> implements onItemClickListener , Filterable {

    private ArrayList<ParseUser> list;
    private ArrayList<ParseUser> filteredList;
    private Context context;

    public UserRVAdapter(ArrayList<ParseUser> list, Context context) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);

        return new UserViewH(view, this);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewH holder, int position) {
        ParseUser parseUser = (ParseUser) list.get(position);
        holder.getName().setText(parseUser.getString("name"));
        if (parseUser.getBoolean("haveprofile")) {
            holder.getOnprof().setText("");
            ParseFile parseFile = parseUser.getParseFile("profilephoto");
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    holder.getProf().setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                }
            });
        } else {
            holder.getProf().setImageBitmap(getBitmapFromVectorDrawable(context, R.drawable.ob_pro));
            holder.getOnprof().setText(parseUser.getString("name"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClick(final int position) {
        Intent intent = new Intent(context, User_page.class);
        if (list.size() != 0) {
            intent.putExtra("user", list.get(position).getObjectId());
            context.startActivity(intent);
        } else {
            //
             Sneaker sneaker = Sneaker.with((Activity) context);sneaker.autoHide(false);
             View vview = LayoutInflater.from(context).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
             sneaker.sneakCustom(vview);
            //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClick(position);
                    //
                    Sneaker sneaker = Sneaker.with((Activity) context);sneaker.setDuration(1);
                    View vview = LayoutInflater.from(context).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
                    sneaker.sneakCustom(vview);
                    //
                }
            },1000);
        }

    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<ParseUser> filteredlist = new ArrayList<>();

            if (constraint.toString().matches("")){
                filteredlist.addAll(filteredList);
            }else {
                for (ParseUser user :
                        filteredList) {
                    if (user.getString("name").toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredlist.add(user);
                    }
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends ParseUser>) results.values);
            notifyDataSetChanged();
        }
    };
}
