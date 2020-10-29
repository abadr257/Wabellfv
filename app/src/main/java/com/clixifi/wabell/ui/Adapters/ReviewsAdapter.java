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
import com.clixifi.wabell.data.Response.GetReviews.ReviewsData;
import com.clixifi.wabell.data.Response.ReviewsArray;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    Context context ;
    ReviewsArray data ;
    private LayoutInflater mInflater;
    public ReviewsAdapter(Context context, ReviewsArray data) {
        this.context = context;
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reviews_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(data.getReviewsData().get(position).getFromUserName());
        holder.bio.setText(data.getReviewsData().get(position).getComment());
        holder.rate.setRating(data.getReviewsData().get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return data.getReviewsData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , bio ;
        RatingBar rate ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bio = itemView.findViewById(R.id.txt_disc);
            name = itemView.findViewById(R.id.txt_tutorName);
            rate = itemView.findViewById(R.id.ratingBar);
        }
    }
}
