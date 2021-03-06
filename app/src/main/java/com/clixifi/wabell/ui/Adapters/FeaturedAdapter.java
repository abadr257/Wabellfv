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
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.clixifi.wabell.utils.StaticMethods.tutorId;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder> {
    Context context;
    FeaturedArray list;
    private LayoutInflater mInflater;

    public FeaturedAdapter(Context context, FeaturedArray list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FeaturedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.featured_tutor_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.MyViewHolder holder, final int position) {
        Picasso.with(context).load(list.getItems().get(position).getProfilePicture()).into(holder.img);
        //StaticMethods.LoadImage(context , holder.img,list.getItems().get(position).getProfilePicture() ,null);
        holder.name.setText(list.getItems().get(position).getName());
        holder.rate.setRating(list.getItems().get(position).getRank());
        holder.numOfRate.setText(list.getItems().get(position).getRankCount() + "");
        if (LocaleManager.getLanguage(context).equals("ar")) {
            if (list.getItems().get(position).getViewsCount() > 2) {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " مشاهدات");
            } else if (list.getItems().get(position).getViewsCount() == 1 || list.getItems().get(position).getViewsCount() == 2) {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " مشاهدة");
            } else {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " مشاهدة");
            }
        } else {
            if (list.getItems().get(position).getViewsCount() > 1) {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " Views");
            } else if (list.getItems().get(position).getViewsCount() == 1) {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " View");
            } else {
                holder.numOfViews.setText(list.getItems().get(position).getViewsCount() + " View");
            }
        }
        holder.location.setText(list.getItems().get(position).getLocation());
        holder.bio.setText(list.getItems().get(position).getTagLine());
        if (LocaleManager.getLanguage(context).equals("en")) {
            holder.price.setText(list.getItems().get(position).getHourPrice() + " SAR / Hr");
            Log.e(TAG, "onBindViewHolder: " + "here");
            if (list.getItems().get(position).getEngTopics() != null) {
                Log.e(TAG, "onBindViewHolder: " + "here 1 ");
                int size = list.getItems().get(position).getEngTopics().size();
                Log.e(TAG, "onBindViewHolder: " + size);
                if (size == 0) {
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub3.setVisibility(View.GONE);
                    holder.subMany.setVisibility(View.GONE);
                }
                if (size > 3) {
                    holder.sub1.setText(list.getItems().get(position).getEngTopics().get(0));
                    holder.sub2.setText(list.getItems().get(position).getEngTopics().get(1));
                    holder.sub3.setText(list.getItems().get(position).getEngTopics().get(2));
                    holder.subMany.setText(list.getItems().get(position).getEngTopics().get(3));
                } else if (size == 3) {
                    holder.sub1.setText(list.getItems().get(position).getEngTopics().get(0));
                    holder.sub2.setText(list.getItems().get(position).getEngTopics().get(1));
                    holder.sub3.setText(list.getItems().get(position).getEngTopics().get(2));
                    holder.subMany.setVisibility(View.GONE);
                } else {
                    if (size == 2) {
                        holder.sub1.setText(list.getItems().get(position).getEngTopics().get(0));
                        holder.sub2.setText(list.getItems().get(position).getEngTopics().get(1));
                        holder.sub3.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    } else if (size == 1) {
                        holder.sub1.setText(list.getItems().get(position).getEngTopics().get(0));
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub3.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub3.setVisibility(View.GONE);
                holder.subMany.setVisibility(View.GONE);
            }
        } else {
            holder.price.setText(list.getItems().get(position).getHourPrice() + " ريال / س ");
            if (list.getItems().get(position).getArTopics() != null) {
                int size = list.getItems().get(position).getArTopics().size();
                if (size == 0) {
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub3.setVisibility(View.GONE);
                    holder.subMany.setVisibility(View.GONE);
                }
                if (size > 3) {
                    holder.sub1.setText(list.getItems().get(position).getArTopics().get(0));
                    holder.sub2.setText(list.getItems().get(position).getArTopics().get(1));
                    holder.sub3.setText(list.getItems().get(position).getArTopics().get(2));
                    holder.subMany.setText(list.getItems().get(position).getArTopics().get(3));
                } else if (size == 3) {
                    holder.sub1.setText(list.getItems().get(position).getArTopics().get(0));
                    holder.sub2.setText(list.getItems().get(position).getArTopics().get(1));
                    holder.sub3.setText(list.getItems().get(position).getArTopics().get(2));
                    holder.subMany.setVisibility(View.GONE);
                } else {
                    if (size == 2) {
                        holder.sub1.setText(list.getItems().get(position).getArTopics().get(0));
                        holder.sub2.setText(list.getItems().get(position).getArTopics().get(1));
                        holder.sub3.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    } else if (size == 1) {
                        holder.sub1.setText(list.getItems().get(position).getArTopics().get(0));
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub3.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub3.setVisibility(View.GONE);
                holder.subMany.setVisibility(View.GONE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bun = new Bundle();
                bun.putString("ID", list.getItems().get(position).getId());
                tutorId = list.getItems().get(position).getId();
                IntentUtilies.openActivityWithBundle(context, TutorProfileView.class, bun);
            }
        });

        if (StaticMethods.userRegisterResponse != null) {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                holder.status.setVisibility(View.GONE);
            } else {
                if (list.getItems().get(position).IsOnline) {
                    holder.status.setVisibility(View.VISIBLE);
                } else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }

            }
        } else {
            if (StaticMethods.userData.getUserType().equals("tutor")) {
                holder.status.setVisibility(View.GONE);
            } else {
                if (list.getItems().get(position).IsOnline) {
                    holder.status.setVisibility(View.VISIBLE);
                } else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.getItems().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, sub1, sub2, sub3, location, bio, numOfRate, numOfViews, subMany;
        RatingBar rate;
        CircleImageView img;
        RelativeLayout status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.rel_online);
            name = itemView.findViewById(R.id.txt_tutorName);
            price = itemView.findViewById(R.id.txt_price);
            sub1 = itemView.findViewById(R.id.txt_sub1);
            sub2 = itemView.findViewById(R.id.txt_sub2);
            subMany = itemView.findViewById(R.id.txt_subMany);
            sub3 = itemView.findViewById(R.id.txt_sub3);
            location = itemView.findViewById(R.id.txt_tutorLocation);
            bio = itemView.findViewById(R.id.txt_disc);
            numOfRate = itemView.findViewById(R.id.txt_numOfRate);
            numOfViews = itemView.findViewById(R.id.numOfViews);
            rate = itemView.findViewById(R.id.ratingBar);
            img = itemView.findViewById(R.id.tutor_img);
        }
    }
}
