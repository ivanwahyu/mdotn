package com.example.windows10.dbproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Windows 10 on 11/06/2017.
 */

public class CrimesAdapter extends RecyclerView.Adapter<CrimesAdapter.MyViewHolder> {

    private Context context;
    private List<Crime> crimeList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,time,description;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cv_title);
            time = (TextView) itemView.findViewById(R.id.cv_time);
            description = (TextView) itemView.findViewById(R.id.cv_description);
            image  = (ImageView) itemView.findViewById(R.id.cv_image);
        }
    }

    public CrimesAdapter(Context context, List<Crime> crimeList) {
        this.context = context;
        this.crimeList = crimeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.crime_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Crime crime = crimeList.get(position);
        holder.title.setText(crime.get_title());
        holder.time.setText(crime.get_time());
        holder.description.setText(crime.get_description());
        holder.image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}
