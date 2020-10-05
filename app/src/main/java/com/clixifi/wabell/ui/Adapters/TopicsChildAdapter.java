package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

public class TopicsChildAdapter extends RecyclerView.Adapter<TopicsChildAdapter.MyViewHolder> {
    Context context;
    private LayoutInflater mInflater;
    ChildResponse response;
    String lang;
    ThirdTopicsAdapter adapter;
    int pp = 0;


    public TopicsChildAdapter(Context context, ChildResponse response) {
        this.context = context;
        this.response = response;
        this.mInflater = LayoutInflater.from(context);
        lang = LocaleManager.getLanguage(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.topics_child, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        adapter = new ThirdTopicsAdapter(holder, position, context, response.getSubcategory().get(position).getSubTopics(), lang);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recThird.setLayoutManager(layoutManager);
        holder.recThird.setAdapter(adapter);
        if (response.getSubcategory().get(position).IsMarked){
            if (lang.equals("ar")) {
                holder.checkBox.setChecked(true);
                holder.txtChild.setText(response.subcategory.get(position).getTitleAr());
            } else {
                holder.checkBox.setChecked(true);
                holder.txtChild.setText(response.subcategory.get(position).getTitleEn());
            }
        }else {
            if (lang.equals("ar")) {
                holder.checkBox.setChecked(false);
                holder.txtChild.setText(response.subcategory.get(position).getTitleAr());
            } else {
                holder.checkBox.setChecked(false);
                holder.txtChild.setText(response.subcategory.get(position).getTitleEn());
            }
        }

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.recThird.getVisibility() == View.GONE) {
                    holder.dropDown.setImageResource(R.drawable.expand_less_24);
                    holder.recThird.setVisibility(View.VISIBLE);
                } else {
                    holder.recThird.setVisibility(View.GONE);
                    holder.dropDown.setImageResource(R.drawable.expand_more_24);
                }

            }
        });

        holder.checkBox.setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return response.subcategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtChild;
        ImageView dropDown;
        RelativeLayout lin;
        RecyclerView recThird;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_child);
            txtChild = itemView.findViewById(R.id.txt_child);
            dropDown = itemView.findViewById(R.id.dropDown);
            recThird = itemView.findViewById(R.id.recThird);
            lin = itemView.findViewById(R.id.lin_Top);
        }
    }
}
