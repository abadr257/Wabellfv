package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.util.Log;
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
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

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
            StaticMethods.LoadImage(context, holder.img, fav.getFavMasters().get(position).getTutorImage(), null);
        }
        if (fav.getFavMasters().get(position).TutorIsFeatured) {
            holder.featured.setVisibility(View.VISIBLE);
        }
        if (fav.getFavMasters().get(position).getTutorName() != null) {
            holder.name.setText(fav.getFavMasters().get(position).getTutorName());
        }
        holder.numOfRates.setText(fav.getFavMasters().get(position).getRankCount()+"");
        holder.numofViews.setText(fav.getFavMasters().get(position).getViewsCount()+"");
        holder.loca.setText(fav.getFavMasters().get(position).getTutorLocation());
        holder.disc.setText(fav.getFavMasters().get(position).getTutorBiography());
        holder.rate.setRating(fav.getFavMasters().get(position).getRank());
        if(LocaleManager.getLanguage(context).equals("en")){
            holder.price.setText(fav.getFavMasters().get(position).getHourPrice()+"SAR/hr");

            if(fav.getFavMasters().get(position).getEngTopics() != null){

                int size = fav.getFavMasters().get(position).getEngTopics().size() ;

                if(size == 0 ){
                    holder.rel1.setVisibility(View.GONE);
                    holder.rel2.setVisibility(View.GONE);
                    holder.rel3.setVisibility(View.GONE);
                }
                if(size >= 3){
                    holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getEngTopics().get(1));
                    holder.sub3.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){
                        holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                        holder.sub2.setText(fav.getFavMasters().get(position).getEngTopics().get(1));
                        holder.rel3.setVisibility(View.GONE);
                    }else if(size == 1){
                        holder.sub1.setText(fav.getFavMasters().get(position).getEngTopics().get(0));
                        holder.rel2.setVisibility(View.GONE);
                        holder.rel3.setVisibility(View.GONE);
                    }
                }
            }else {
                holder.rel1.setVisibility(View.GONE);
                holder.rel2.setVisibility(View.GONE);
                holder.rel3.setVisibility(View.GONE);
            }
        }else {
            holder.price.setText(fav.getFavMasters().get(position).getHourPrice()+"ريال/س");
            if(fav.getFavMasters().get(position).getArTopics() != null){
                int size = fav.getFavMasters().get(position).getArTopics().size() ;
                if(size == 0 ){
                    holder.rel1.setVisibility(View.GONE);
                    holder.rel2.setVisibility(View.GONE);
                    holder.rel3.setVisibility(View.GONE);
                }
                if(size >= 3){
                    holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                    holder.sub2.setText(fav.getFavMasters().get(position).getArTopics().get(1));
                    holder.sub3.setText("+"+(size - 2));
                }else {
                    if(size == 2 ){
                        holder.sub1.setText(fav.getFavMasters().get(position).getArTopics().get(0));
                        holder.sub2.setText(fav.getFavMasters().get(position).getArTopics().get(1));
                        holder.rel3.setVisibility(View.GONE);
                    }else if(size == 1){
                        holder.rel2.setVisibility(View.GONE);
                        holder.rel3.setVisibility(View.GONE);
                    }
                }
            }else {
                holder.rel1.setVisibility(View.GONE);
                holder.rel2.setVisibility(View.GONE);
                holder.rel3.setVisibility(View.GONE);
            }
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.unFavorite(context, fav.getFavMasters().get(position).getTutorId());
                fav.getFavMasters().remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, fav.getFavMasters().size());
                dialog.ShowDialog();

            }
        });

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

    }

    @Override
    public void onConnection(boolean isConnected) {

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, loca, price, disc, sub1, sub2, sub3, numOfRates, numofViews, txt;
        RatingBar rate;
        RelativeLayout rel1 , rel2 , rel3 ;
        ImageView featured, fav;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel1 = itemView.findViewById(R.id.linSub1 );
            rel2 = itemView.findViewById(R.id.linSub2 );
            rel3 = itemView.findViewById(R.id.linSub3 );
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
            sub3 = itemView.findViewById(R.id.txt_subMany);
            numOfRates = itemView.findViewById(R.id.txt_numOfRate);
            numofViews = itemView.findViewById(R.id.numOfViews);
            rate = itemView.findViewById(R.id.ratingBar);
        }
    }
}
