package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.PackagesArray;
import com.clixifi.wabell.ui.payment.PaymentScreen;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.MyViewHolder> {
    PackagesArray array;
    Context context;
    private LayoutInflater mInflater;

    public PackagesAdapter(PackagesArray array, Context context) {
        this.array = array;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.package_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (array.getResult().get(position).getPackageDuration().equals("Monthly")) {
            if (LocaleManager.getLanguage(context).equals("en")) {

                holder.duration.setText(array.getResult().get(position).getPackageDuration());
                holder.type.setText(array.getResult().get(position).getTagLine());
                holder.price.setText(array.getResult().get(position).getPrice() + " SAR / Month ");
            } else {
                holder.linSav.setBackground(context.getDrawable(R.drawable.rtl_packages_item));
                holder.type.setText(array.getResult().get(position).getTagLine());
                holder.duration.setText(array.getResult().get(position).getPackageDuration());
                holder.price.setText(array.getResult().get(position).getPrice() + " ريال / شهر ");
            }
        } else {
            if (LocaleManager.getLanguage(context).equals("en")) {
                holder.duration.setText(array.getResult().get(position).getPackageDuration());
                holder.type.setText(array.getResult().get(position).getTagLine());
                holder.price.setText(array.getResult().get(position).getPrice() + " SAR / Month ");
            } else {
                holder.type.setText(array.getResult().get(position).getTagLine());
                holder.linSav.setBackground(context.getDrawable(R.drawable.rtl_packages_item));
                holder.price.setText(array.getResult().get(position).getPrice() + " ريال / شهر ");
                holder.duration.setText(array.getResult().get(position).getPackageDuration());
            }
        }
        if (!array.getResult().get(position).getTagLine().equals("")) {
            holder.linSav.setVisibility(View.VISIBLE);
            holder.savPrice.setText(array.getResult().get(position).getTagLine());
        } else {
            holder.linSav.setVisibility(View.GONE);
        }
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("id",array.getResult().get(position).getId());
                b.putDouble("amount",array.getResult().get(position).getPrice());
                b.putString("duration",array.getResult().get(position).getPackageDuration());
                b.putString("type",array.getResult().get(position).getTagLine());
                IntentUtilies.openActivityWithBundle(context , PaymentScreen.class , b) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linSav;
        TextView duration, price, type, savPrice;
        CardView sub;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linSav = itemView.findViewById(R.id.lin_save);
            duration = itemView.findViewById(R.id.txt_time);
            price = itemView.findViewById(R.id.txt_price);
            type = itemView.findViewById(R.id.txt_paidType);
            sub = itemView.findViewById(R.id.card_sub);
            savPrice = itemView.findViewById(R.id.txt_savePrice);
        }
    }
}
