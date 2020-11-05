package com.clixifi.wabell.ui.searchByWord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivitySearchByWordBinding;

public class SearchByWord extends AppCompatActivity {
    ActivitySearchByWordBinding binding ;
    MyHandler handler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_search_by_word);
        handler = new MyHandler(this );
        binding.setHandler(handler);
    }
    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }
        public void onCancel(View v ){
            onBackPressed();
        }
    }
}