package com.clixifi.wabell.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivitySearchScreenBinding;
import com.clixifi.wabell.ui.Adapters.TutorListAdapter;
import com.clixifi.wabell.utils.StaticMethods;

public class SearchScreen extends AppCompatActivity {
    ActivitySearchScreenBinding binding;
    SearchHandler handler;
    TutorListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_screen);
        handler = new SearchHandler(this);
        binding.setHandler(handler);
        if (StaticMethods.tutors.getResult().size() == 0){
            binding.noResultTv.setVisibility(View.VISIBLE);
        }
        binding.arrayList.setText(""+StaticMethods.tutors.getResult().size());
        listAdapter = new TutorListAdapter(SearchScreen.this, StaticMethods.tutors);
        binding.recResult.setLayoutManager(new LinearLayoutManager(SearchScreen.this));
        binding.recResult.setAdapter(listAdapter);
    }

    public class SearchHandler {
        Context context;

        public SearchHandler(Context context) {
            this.context = context;
        }

        public void onFilter(View v) {

        }

        public void onSearch(View v) {

        }

        public void back(View v) {
            onBackPressed();
            StaticMethods.tutors = null ;
        }
    }
}