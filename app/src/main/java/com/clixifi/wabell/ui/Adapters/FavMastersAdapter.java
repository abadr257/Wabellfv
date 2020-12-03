package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileInterface;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfilePresenter;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.clixifi.wabell.utils.StaticMethods.tutorId;

public class FavMastersAdapter extends RecyclerView.Adapter<FavMastersAdapter.MyViewHolder> implements TutorProfileInterface {
    Context context;
    FavMastersStudent fav;
    private LayoutInflater mInflater;
    TutorProfilePresenter presenter;
    CustomDialog dialog;

    public FavMastersAdapter(Context context, FavMastersStudent fav) {
        this.context = context;
        this.fav = fav;
        dialog = new CustomDialog(context);
        this.mInflater = LayoutInflater.from(context);
        presenter = new TutorProfilePresenter(this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fav_masters_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (fav.getFavMasters().get(position).getTutorImage() != null) {
            Picasso.with(context).load(fav.getFavMasters().get(position).getTutorImage()).into(holder.img);
            //StaticMethods.LoadImage(context, holder.img, fav.getFavMasters().get(position).getTutorImage(), null);
        }
        if (fav.getFavMasters().get(position).TutorIsFeatured) {
            holder.featured.setVisibility(View.VISIBLE);
        }
        if (fav.getFavMasters().get(position).getTutorName() != null) {
            holder.name.setText(fav.getFavMasters().get(position).getTutorName());
        }
        holder.numOfRates.setText(fav.getFavMasters().get(position).getRankCount() + "");
        if (LocaleManager.getLanguage(context).equals("ar")) {
            if (fav.getFavMasters().get(position).getViewsCount() > 2) {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " مشاهدات");
            } else if (fav.getFavMasters().get(position).getViewsCount() == 1 || fav.getFavMasters().get(position).getViewsCount() == 2) {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " مشاهدة");
            } else {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " مشاهدة");
            }
        } else {
            if (fav.getFavMasters().get(position).getViewsCount() > 1) {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " Views");
            } else if (fav.getFavMasters().get(position).getViewsCount() == 1) {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " View");
            } else {
                holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount() + " View");
            }
        }
        holder.loca.setText(fav.getFavMasters().get(position).getTutorLocation());
        holder.disc.setText(fav.getFavMasters().get(position).getTagLine());
        holder.rate.setRating(fav.getFavMasters().get(position).getRank());
        if (LocaleManager.getLanguage(context).equals("en")) {
            holder.price.setText(fav.getFavMasters().get(position).getHourPrice() + " SAR / Hr ");

            if (fav.getFavMasters().get(position).getEngTopics() != null) {

                int size = fav.getFavMasters().get(position).getEngTopics().size();

                if (size == 0) {
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub4.setVisibility(View.GONE);
                    holder.subMany.setVisibility(View.GONE);
                }
                if (size > 3) {
                    holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getEngTopics().get(1));
                    holder.sub4.setText(fav.getFavMasters().get(position).getEngTopics().get(2));
                    holder.subMany.setText(fav.getFavMasters().get(position).getEngTopics().get(3));
                } else if (size == 3) {
                    holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getEngTopics().get(1));
                    holder.sub4.setText(fav.getFavMasters().get(position).getEngTopics().get(2));
                    holder.subMany.setVisibility(View.GONE);
                } else {
                    if (size == 2) {
                        holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                        holder.sub2.setText(fav.getFavMasters().get(position).getEngTopics().get(1));
                        holder.sub4.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    } else if (size == 1) {
                        holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub4.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub4.setVisibility(View.GONE);
                holder.subMany.setVisibility(View.GONE);
            }
        } else {
            holder.price.setText(fav.getFavMasters().get(position).getHourPrice() + " ريال / س ");
            if (fav.getFavMasters().get(position).getArTopics() != null) {
                int size = fav.getFavMasters().get(position).getArTopics().size();
                if (size == 0) {
                    holder.sub1.setVisibility(View.GONE);
                    holder.sub2.setVisibility(View.GONE);
                    holder.sub4.setVisibility(View.GONE);
                    holder.subMany.setVisibility(View.GONE);
                }
                if (size > 3) {
                    holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getArTopics().get(1));
                    holder.sub4.setText(fav.getFavMasters().get(position).getArTopics().get(2));
                    holder.subMany.setText(fav.getFavMasters().get(position).getArTopics().get(3));
                } else if (size == 3) {
                    holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getArTopics().get(1));
                    holder.sub4.setText(fav.getFavMasters().get(position).getArTopics().get(2));
                    holder.subMany.setVisibility(View.GONE);
                } else {
                    if (size == 2) {
                        holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                        holder.sub2.setText(fav.getFavMasters().get(position).getArTopics().get(1));
                        holder.sub4.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    } else if (size == 1) {
                        holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                        holder.sub2.setVisibility(View.GONE);
                        holder.sub4.setVisibility(View.GONE);
                        holder.subMany.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.sub1.setVisibility(View.GONE);
                holder.sub2.setVisibility(View.GONE);
                holder.sub4.setVisibility(View.GONE);
                holder.subMany.setVisibility(View.GONE);
            }
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.ShowDialog();
                presenter.unFavorite(context, fav.getFavMasters().get(position).getTutorId());
                fav.getFavMasters().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, fav.getFavMasters().size());


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bun = new Bundle();
                bun.putString("ID", fav.getFavMasters().get(position).getTutorId());
                tutorId = fav.getFavMasters().get(position).getTutorId();
                IntentUtilies.openActivityWithBundle(context, TutorProfileView.class, bun);
            }
        });
        if (StaticMethods.userRegisterResponse != null) {
            if (StaticMethods.userRegisterResponse.Data.getType().equals("tutor")) {
                holder.status.setVisibility(View.GONE);
            } else {
                if (fav.getFavMasters().get(position).IsOnline) {
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
                if (fav.getFavMasters().get(position).IsOnline) {
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
        if (fav.getFavMasters() != null) {
            return fav.getFavMasters().size();
        } else {
            return 0;
        }
    }

    @Override
    public void onSuccess(TutorProfileForStudent tutor) {

    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(context, R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(context, R.string.noInternet);
    }

    @Override
    public void OnAddedToFavorite(AddFavorite addFavorite) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(context, R.string.saved);
    }

    @Override
    public void onDeleteFav(ResultBoolean result) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(context, R.string.delete);
    }

    @Override
    public void onRequest(ResultBoolean resultBoolean) {

    }

    @Override
    public void onSendMessage(ResultBoolean resultBoolean) {

    }

    @Override
    public void onCall(ResultBoolean resultBoolean) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(context, R.string.delete);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, loca, price, disc, sub1, sub2, subMany, sub4, numOfRates, numofViews, txt;
        RatingBar rate;
        RelativeLayout rel1, rel2, rel3, rel4, status;
        ImageView featured, fav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.rel_online);


            txt = itemView.findViewById(R.id.checkIf);
            fav = itemView.findViewById(R.id.fav_image);
            img = itemView.findViewById(R.id.tutor_img);
            featured = itemView.findViewById(R.id.isFea);
            name = itemView.findViewById(R.id.txt_tutorName);
            loca = itemView.findViewById(R.id.txt_tutorLocation);
            price = itemView.findViewById(R.id.txt_price);
            disc = itemView.findViewById(R.id.txt_disc);
            sub1 = itemView.findViewById(R.id.txt_sub1);
            sub2 = itemView.findViewById(R.id.txt_sub2);
            sub4 = itemView.findViewById(R.id.txt_sub3);
            subMany = itemView.findViewById(R.id.txt_subMany);
            numOfRates = itemView.findViewById(R.id.txt_numOfRate);
            numofViews = itemView.findViewById(R.id.numOfViews);
            rate = itemView.findViewById(R.id.ratingBar);
        }
    }
}
