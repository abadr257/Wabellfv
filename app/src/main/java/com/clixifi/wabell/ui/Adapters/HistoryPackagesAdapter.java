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
    HistoryArray array;

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
        if (LocaleManager.getLanguage(context).equals("en")) {
            holder.until.setText(array.getResult().get(position).getStartDate() + " To " + array.getResult().get(position).getExpirationDate());
            holder.duration.setText(array.getResult().get(position).getTitle());
            holder.type.setText(array.getResult().get(position).getPackageDuration());
            holder.price.setText(array.getResult().get(position).getPrice() + " SAR / Month ");
        } else {
            holder.until.setText(array.getResult().get(position).getStartDate() + " إلى " + array.getResult().get(position).getExpirationDate());
            holder.duration.setText(array.getResult().get(position).getTitleAr());
            holder.price.setText(array.getResult().get(position).getPrice() + " ريال / شهر ");
            holder.type.setText(array.getResult().get(position).getPackageDurationAr());
        }
        if (array.getResult().get(position).getTagLine().equals("Active")) {
            holder.active.setText(context.getResources().getString(R.string.active));
            if (LocaleManager.getLanguage(context).equals("ar")) {
                holder.active.setTextColor(context.getColor(R.color.history_active));
                holder.linSave.setBackground(context.getDrawable(R.drawable.rtl_history2));
            } else {
                holder.active.setTextColor(context.getColor(R.color.history_active));
                holder.linSave.setBackground(context.getDrawable(R.drawable.history_shape2));
            }
        } else {
            holder.active.setText(context.getResources().getString(R.string.expir));
            if (LocaleManager.getLanguage(context).equals("ar")) {
                holder.active.setTextColor(context.getColor(R.color.history_unactive));
                holder.linSave.setBackground(context.getDrawable(R.drawable.rtl_history));
            } else {
                holder.active.setTextColor(context.getColor(R.color.history_unactive));
                holder.linSave.setBackground(context.getDrawable(R.drawable.history_shape));
            }
        }


    }

    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView duration, price, type, until, active;
        LinearLayout linSave;

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
