package com.clixifi.wabell.ui.Adapters.summarySecLevelAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.ui.Adapters.CategoryAdapter;
import com.clixifi.wabell.ui.Adapters.ThirdTopicsAdapter;
import com.clixifi.wabell.ui.Adapters.TopicsChildAdapter;

public class SummarySecAdapter extends RecyclerView.Adapter<SummarySecAdapter.MyViewHolder> {
    Context context;
    private LayoutInflater mInflater;
    ChildResponse response;
    String lang;
    SummaryThLevel adapter ;

    public SummarySecAdapter(Context context, ChildResponse response, String lang) {
        this.context = context;
        this.response = response;
        this.lang = lang;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SummarySecAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.summary_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummarySecAdapter.MyViewHolder holder, int position) {
        adapter = new SummaryThLevel( context,  lang,response.getSubcategory().get(position).getSubTopics());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recThird.setLayoutManager(layoutManager);
        holder.recThird.setAdapter(adapter);
        if(lang.equals("ar")){
            holder.txtChild.setText(response.getSubcategory().get(position).getTitleAr());
        }else {
            holder.txtChild.setText(response.getSubcategory().get(position).getTitleEn());
        }
    }

    @Override
    public int getItemCount() {
        if(response.subcategory != null){
            return response.subcategory.size();
        }else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtChild;
        ImageView dropDown;
        RelativeLayout lin;
        RecyclerView recThird;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChild = itemView.findViewById(R.id.txt_child);
            dropDown = itemView.findViewById(R.id.dropDown);
            recThird = itemView.findViewById(R.id.recThird);
            lin = itemView.findViewById(R.id.lin_Top);
        }
    }
}
