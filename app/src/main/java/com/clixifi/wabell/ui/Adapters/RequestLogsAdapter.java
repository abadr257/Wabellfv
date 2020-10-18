package com.clixifi.wabell.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;

public class RequestLogsAdapter extends RecyclerView.Adapter<RequestLogsAdapter.MyViewHolder> {
    RequestLogsArray array ;
    Context context ;
    private LayoutInflater mInflater;

    public RequestLogsAdapter(RequestLogsArray array, Context context) {
        this.array = array;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(array.getResult() != null){
            return array.getResult().size();
        }else {
            return 0 ;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
