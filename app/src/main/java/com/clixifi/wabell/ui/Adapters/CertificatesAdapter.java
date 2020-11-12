package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.ui.viewAllCertificates.AllCertificates;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CertificatesAdapter extends RecyclerView.Adapter<CertificatesAdapter.MyViewHolder> {
    Context context ;
    ArrayList<ImageUrl> images ;
    ArrayList<String> imgs ;
    private LayoutInflater mInflater;

    public CertificatesAdapter(Context context, ArrayList<ImageUrl> images, ArrayList<String> imgs) {
        this.context = context;
        this.images = images;
        this.imgs = imgs;
        this.mInflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if(images != null){
            Picasso.with(context).load(images.get(position).getFilePath()).into(holder.img);
            //StaticMethods.LoadImage(context , holder.img ,images.get(position).getFilePath() , null);
        }else  {
            Picasso.with(context).load(imgs.get(position)).into(holder.img);
            //StaticMethods.LoadImage(context , holder.img ,imgs.get(position) , null);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                if(images != null){
                    b.putString("imageClicked" ,images.get(position).getFilePath() );
                    IntentUtilies.openActivityWithBundle(context , AllCertificates.class , b);
                }else  {
                    b.putString("imageClicked" ,imgs.get(position) );
                    IntentUtilies.openActivityWithBundle(context , AllCertificates.class , b);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        if(images != null){
            return images.size();
        }else  {
            return imgs.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
        }
    }
}
