package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.utils.StaticMethods.tutorId;

public class TutorListAdapter extends RecyclerView.Adapter<TutorListAdapter.MyViewHolder> {
    Context context ;
    TutorListArray array ;
    private LayoutInflater mInflater;

    public TutorListAdapter(Context context, TutorListArray array) {
        this.context = context;
        this.array = array;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tutor_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.name.setText(array.getResult().get(position).getName());
        holder.rate.setRating(array.getResult().get(position).getRank());
        holder.numOfRate.setText(array.getResult().get(position).getRankCount()+"");
        holder.numOfViews.setText(array.getResult().get(position).getViewsCount()+"");
        holder.location.setText(array.getResult().get(position).getLocation());
        holder.bio.setText(array.getResult().get(position).getTagLine());
        StaticMethods.LoadImage(context , holder.img,array.getResult().get(position).getProfilePicture() ,null);
        if(LocaleManager.getLanguage(context).equals("en")){
            holder.price.setText(array.getResult().get(position).getHourPrice()+" SAR / Hr");
            Log.e(TAG, "onBindViewHolder: "+"here" );
            if(array.getResult().get(position).getEngTopics() != null){
                Log.e(TAG, "onBindViewHolder: "+"here 1 " );
                int size = array.getResult().get(position).getEngTopics().size() ;
                Log.e(TAG, "onBindViewHolder: "+size );
                if(size == 0 ){
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub3.setVisibility(View.GONE);
                }
                if(size >= 3){
                    holder.sub1.setText(array.getResult().get(position).getEngTopics().get(0));
                    holder.sub2.setText(array.getResult().get(position).getEngTopics().get(1));
                    holder.sub3.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){
                        holder.sub1.setText(array.getResult().get(position).getEngTopics().get(0));
                        holder.sub2.setText(array.getResult().get(position).getEngTopics().get(1));
                        holder.sub3.setVisibility(View.GONE);
                    }else if(size == 1){
                        holder.sub1.setText(array.getResult().get(position).getEngTopics().get(0));
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub3.setVisibility(View.GONE);
                    }
                }
            }else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub3.setVisibility(View.GONE);
            }
        }else {
            holder.price.setText(array.getResult().get(position).getHourPrice()+" ريال / س ");
            if(array.getResult().get(position).getArTopics() != null){
                int size = array.getResult().get(position).getArTopics().size() ;
                if(size == 0 ){
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub3.setVisibility(View.GONE);
                }
                if(size >= 3){
                    holder.sub1.setText(array.getResult().get(position).getArTopics().get(0));
                    holder.sub2.setText(array.getResult().get(position).getArTopics().get(1));
                    holder.sub3.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){
                        holder.sub1.setText(array.getResult().get(position).getArTopics().get(0));
                        holder.sub2.setText(array.getResult().get(position).getArTopics().get(1));
                        holder.sub3.setVisibility(View.GONE);
                    }else if(size == 1){
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub3.setVisibility(View.GONE);
                    }
                }
            }else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub3.setVisibility(View.GONE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bun = new Bundle( );
                bun.putString("ID" ,array.getResult().get(position).getId() );
                tutorId = array.getResult().get(position).getId();
                Log.e(TAG, "onClick: from adapter"+array.getResult().get(position).getId() );
                IntentUtilies.openActivityWithBundle(context, TutorProfileView.class , bun);
            }
        });
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                holder.status.setVisibility(View.GONE);
            }else {
                if(array.getResult().get(position).IsOnline){
                    holder.status.setVisibility(View.VISIBLE);
                }else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }

            }
        }else {
            if(StaticMethods.userData.getUserType().equals("tutor")){
                holder.status.setVisibility(View.GONE);
            }else {
                if(array.getResult().get(position).IsOnline){
                    holder.status.setVisibility(View.VISIBLE);
                }else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , price , sub1 , sub2 , sub3 ,location , bio , numOfRate , numOfViews ;
        RatingBar rate ;
        CircleImageView img ;
        RelativeLayout status ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.rel_online);
            name = itemView.findViewById(R.id.txt_tutorName );
            price = itemView.findViewById(R.id.txt_price );
            sub1 = itemView.findViewById(R.id.txt_sub1 );
            sub2 = itemView.findViewById(R.id.txt_sub2 );
            sub3 = itemView.findViewById(R.id.txt_subMany );
            location = itemView.findViewById(R.id.txt_tutorLocation );
            bio = itemView.findViewById(R.id.txt_disc );
            numOfRate = itemView.findViewById(R.id.txt_numOfRate );
            numOfViews = itemView.findViewById(R.id.numOfViews );
            rate = itemView.findViewById(R.id.ratingBar);
            img = itemView.findViewById(R.id.tutor_img);
        }
    }
}
