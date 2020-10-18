package com.clixifi.wabell.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivitySearchScreenBinding;

public class SearchScreen extends AppCompatActivity {
    ActivitySearchScreenBinding binding ; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
    }
}