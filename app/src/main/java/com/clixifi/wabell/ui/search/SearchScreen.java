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
import com.clixifi.wabell.ui.filterScreen.FilterScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.searchByWord.SearchByWord;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;

import static com.clixifi.wabell.utils.StaticMethods.searchWord;

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
        if(!searchWord.equals("")){
            binding.searchName.setText(searchWord);
        }
        binding.arrayList.setText(""+StaticMethods.tutors.getResult().size());
        listAdapter = new TutorListAdapter(SearchScreen.this, StaticMethods.tutors);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchScreen.this);
        /*linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);*/
        binding.recResult.setLayoutManager(linearLayoutManager);
        binding.recResult.setAdapter(listAdapter);
    }

    public class SearchHandler {
        Context context;

        public SearchHandler(Context context) {
            this.context = context;
        }

        public void onFilter(View v) {
            IntentUtilies.openActivity(SearchScreen.this, FilterScreen.class);
            SearchScreen.this.finish();
        }
        public void onSearch(View v) {
            IntentUtilies.openActivity(SearchScreen.this, SearchByWord.class);
            SearchScreen.this.finish();
        }

        public void back(View v) {
            onBackPressed();
            StaticMethods.tutors = null ;
            searchWord = "";
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
}