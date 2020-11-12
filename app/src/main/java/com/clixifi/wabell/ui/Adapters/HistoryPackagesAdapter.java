package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.HistoryArray;
import com.clixifi.wabell.utils.LocaleManager;

public class HistoryPackagesAdapter extends RecyclerView.Adapter<HistoryPackagesAdapter.MyViewHolder> {
    Context context;
    private LayoutInflater mInflater;
    HistoryArray array ;

    public HistoryPackagesAdapter(Context context, HistoryArray array) {
        this.context = context;
        this.array = array;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(LocaleManager.getLanguage(context).equals("en")){
            holder.until.setText(array.getResult().get(position).getStartDate() +" To " +array.getResult().get(position).getExpirationDate());
            if (array.getResult().get(position).getPackageDuration().equals("Monthly")){
                holder.duration.setText("Monthly Subscription");
                holder.type.setText("Paid Monthly");
                holder.price.setText(array.getResult().get(position).getPrice() + " SAR / Month ");
            }else {
                holder.type.setText("Paid Yearly");
                holder.price.setText(array.getResult().get(position).getPrice() + " SAR / Month ");
                holder.duration.setText("Yearly Subscription");
            }
        }else {
            holder.until.setText(array.getResult().get(position).getStartDate() +" إلى " +array.getResult().get(position).getExpirationDate());
            if (array.getResult().get(position).getPackageDuration().equals("Monthly")){
                holder.duration.setText("باقة شهرية");
                holder.linSave.setBackground(context.getDrawable(R.drawable.rtl_history));
                holder.price.setText(array.getResult().get(position).getPrice() + " ريال / شهر ");
                holder.type.setText("الدفع شهرى");
            }else {
                holder.duration.setText("باقة سنوية");
                holder.linSave.setBackground(context.getDrawable(R.drawable.rtl_history));
                holder.price.setText(array.getResult().get(position).getPrice() + " ريال / شهر ");
                holder.type.setText("الدفع سنوي");
            }
        }
        holder.active.setText(array.getResult().get(position).getTagLine());

    }

    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView duration, price, type , until , active;
        LinearLayout linSave  ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linSave = itemView.findViewById(R.id.lin_save);
            active = itemView.findViewById(R.id.txt_savePrice);
            until = itemView.findViewById(R.id.txt_until);
            duration = itemView.findViewById(R.id.txt_time);
            price = itemView.findViewById(R.id.txt_price);
            type = itemView.findViewById(R.id.txt_paidType);
        }
    }
}
