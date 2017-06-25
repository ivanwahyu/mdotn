package com.example.windows10.dbproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.RequiresPermission;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 10 on 11/06/2017.
 */

public class CrimesAdapter extends RecyclerView.Adapter<CrimesAdapter.MyViewHolder> {

    private List<Crime> crimeList;
    private Crime crime;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title,time,description;
        public ImageView image;
        List<Crime> crimeList = new ArrayList<Crime>();
        Context context;

        public MyViewHolder(View itemView, Context context, List<Crime> crimes) {
            super(itemView);
            this.context = context;
            this.crimeList = crimes;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.cv_title);
            time = (TextView) itemView.findViewById(R.id.cv_time);
            description = (TextView) itemView.findViewById(R.id.cv_description);
            image  = (ImageView) itemView.findViewById(R.id.cv_image);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Crime crime = this.crimeList.get(position);
            Intent intent = new Intent(this.context,ScrollingActivity.class);
            intent.putExtra("crime title",crime.get_title());
            intent.putExtra("crime time",crime.get_time());
            intent.putExtra("crime description",crime.get_description());
            intent.putExtra("crime image",crime.get_image());
            this.context.startActivity(intent);

        }
    }

    public CrimesAdapter(Context context, List<Crime> crimeList) {
        this.context = context;
        this.crimeList = crimeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.crime_card,parent,false);
        return new MyViewHolder(itemView,context,crimeList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Crime crime = crimeList.get(position);
        holder.title.setText(crime.get_title());
        holder.time.setText(crime.get_time());
        holder.description.setText(crime.get_description());
        byte[] outImage=crime.get_image();
        Bitmap theImage = BitmapFactory.decodeByteArray(outImage, 0, outImage.length);
        holder.image.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }
}
