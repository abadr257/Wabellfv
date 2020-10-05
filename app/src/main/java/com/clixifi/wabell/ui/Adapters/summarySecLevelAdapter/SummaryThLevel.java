package com.clixifi.wabell.ui.Adapters.summarySecLevelAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.FourthLevel;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;

import java.util.ArrayList;

public class SummaryThLevel extends RecyclerView.Adapter<SummaryThLevel.MyViewHolder> {
    Context context;
    private LayoutInflater mInflater;
    String lang;
    ArrayList<FourthLevel> SubTopics;

    public SummaryThLevel(Context context, String lang, ArrayList<FourthLevel> subTopics) {
        this.context = context;
        this.lang = lang;
        SubTopics = subTopics;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SummaryThLevel.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.summary_third_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(lang.equals("en")){
            holder.txt_name.setText(SubTopics.get(position).getTitleEn());
        }else {
            holder.txt_name.setText(SubTopics.get(position).getTitleAr());
        }

    }

    @Override
    public int getItemCount() {
        return SubTopics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        RelativeLayout rel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel = itemView.findViewById(R.id.rel_selected);
            txt_name = itemView.findViewById(R.id.nameThirdChild);
        }
    }
}
