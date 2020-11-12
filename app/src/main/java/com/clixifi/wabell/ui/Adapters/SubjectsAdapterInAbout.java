package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;

import java.util.ArrayList;

public class SubjectsAdapterInAbout extends RecyclerView.Adapter<SubjectsAdapterInAbout.MyViewHolder> {
    ArrayList<String> Topics ;
    Context context ;
    private LayoutInflater mInflater;

    public SubjectsAdapterInAbout(ArrayList<String> topics, Context context) {
        Topics = topics;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.subjects_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt.setText(Topics.get(position));
    }

    @Override
    public int getItemCount() {
        return Topics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txtSub);
        }
    }
}
