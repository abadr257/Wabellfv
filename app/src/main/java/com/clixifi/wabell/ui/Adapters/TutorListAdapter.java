package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.utils.LocaleManager;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(array.getResult().get(position).getName());
        holder.rate.setRating(array.getResult().get(position).getRank());
        holder.numOfRate.setText(array.getResult().get(position).getRankCount()+"");
        holder.numOfViews.setText(array.getResult().get(position).getViewsCount()+"");
        holder.location.setText(array.getResult().get(position).getLocation());
        holder.bio.setText(array.getResult().get(position).getBiography());
        if(LocaleManager.getLanguage(context).equals("en")){
            holder.price.setText(array.getResult().get(position).getHourPrice()+"SAR/hr");
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
            holder.price.setText(array.getResult().get(position).getHourPrice()+"ريال/س");
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
    }

    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , price , sub1 , sub2 , sub3 ,location , bio , numOfRate , numOfViews ;
        RatingBar rate ;
        CircleImageView img ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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