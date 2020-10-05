package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;
import com.clixifi.wabell.utils.StaticMethods;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavMastersAdapter extends RecyclerView.Adapter<FavMastersAdapter.MyViewHolder> {
    Context context;
    FavMastersStudent fav;
    private LayoutInflater mInflater;

    public FavMastersAdapter(Context context, FavMastersStudent fav) {
        this.context = context;
        this.fav = fav;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fav_masters_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(fav.getFavMasters().get(position).getTutorImage() != null){
            StaticMethods.LoadImage(context , holder.img,fav.getFavMasters().get(position).getTutorImage() ,null);
        }
        if(fav.getFavMasters().get(position).getTutorName() != null){
            holder.name.setText(fav.getFavMasters().get(position).getTutorName());
        }
        /*if(fav.getFavMasters().get(position).getTutorImage() != null){

        }
        if(fav.getFavMasters().get(position).getTutorImage() != null){

        }
        if(fav.getFavMasters().get(position).getTutorImage() != null){

        }
        if(fav.getFavMasters().get(position).getTutorImage() != null){

        }*/
    }

    @Override
    public int getItemCount() {
        if (fav.getFavMasters() != null) {
            return fav.getFavMasters().size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img ;
        TextView name , loca , price ,disc , sub1 , sub2 ,sub3, numOfRates , numofViews;
        RatingBar rate ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.tutor_img );
            name = itemView.findViewById(R.id.txt_tutorName );
            loca = itemView.findViewById(R.id.txt_tutorLocation );
            price = itemView.findViewById(R.id.txt_price );
            disc = itemView.findViewById(R.id.txt_disc );
            sub1 = itemView.findViewById(R.id.txt_sub1 );
            sub2 = itemView.findViewById(R.id.txt_sub2 );
            sub3 = itemView.findViewById(R.id.txt_subMany );
            numOfRates = itemView.findViewById(R.id.numOfViews );
            numofViews = itemView.findViewById(R.id.txt_numOfRate );
            rate = itemView.findViewById(R.id.ratingBar );
        }
    }
}
