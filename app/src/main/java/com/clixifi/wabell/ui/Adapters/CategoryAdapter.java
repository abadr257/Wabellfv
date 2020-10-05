package com.clixifi.wabell.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topic.Topics;
import com.clixifi.wabell.ui.topicsDetails.TopicsDetailsActivity;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context ;
    Topics list;
    private LayoutInflater mInflater;
    boolean edit = false ;
    String type ;


    public CategoryAdapter(Context context, Topics list ,boolean edit , String type) {
        this.context = context;
        this.type = type;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.edit = edit;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.MyViewHolder holder, final int position) {
        if(list.getCategory().get(position).IsMarked){
            if(LocaleManager.getLanguage(context).equals("en")){
                holder.name.setText(list.getCategory().get(position).getTitleEn());
                holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.categ.setBackground(context.getResources().getDrawable(R.drawable.category_selected));
                holder.num.setText(list.getCategory().get(position).getChildsCount()+"");
            }else {
                holder.name.setText(list.getCategory().get(position).getTitleAr());
                holder.name.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.categ.setBackground(context.getResources().getDrawable(R.drawable.category_selected));
                holder.num.setText(list.getCategory().get(position).getChildsCount()+"");
            }
        }else {
            if(LocaleManager.getLanguage(context).equals("en")){
                holder.name.setText(list.getCategory().get(position).getTitleEn());
            }else {
                holder.name.setText(list.getCategory().get(position).getTitleAr());
            }
        }


        holder.categ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle( );
                bundle.putInt("ID" , list.getCategory().get(position).getId());
                bundle.putString("name" ,holder.name.getText().toString() );
                bundle.putString("type" ,type );
                bundle.putBoolean("edit" ,edit );
                IntentUtilies.openActivityWithBundle(context , TopicsDetailsActivity.class , bundle);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.getCategory().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name , num ;
        RelativeLayout categ ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.numOfSelections);
            name = itemView.findViewById(R.id.catName);
            categ = itemView.findViewById(R.id.category);
        }
    }
}
