package com.clixifi.wabell.ui.filterScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityFilterScreenBinding;

public class FilterScreen extends AppCompatActivity {
    ActivityFilterScreenBinding binding;
    FilterHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_screen);
        handler = new FilterHandler(this);
        binding.setHandler(handler);
    }


    public class FilterHandler {
        Context context;

        public FilterHandler(Context context) {
            this.context = context;
        }

        public void onBudget(View v) {
            if (binding.relBudgetSub.getVisibility() == View.VISIBLE) {
                binding.relBudgetSub.setVisibility(View.GONE);
            } else {
                binding.relBudgetSub.setVisibility(View.VISIBLE);
            }
        }

        public void onSortBy(View v) {
            if (binding.relSortSub.getVisibility() == View.VISIBLE) {
                binding.relSortSub.setVisibility(View.GONE);
            } else {
                binding.relSortSub.setVisibility(View.VISIBLE);
            }
        }

        public void onDistance(View v) {
            if (binding.relDisSub.getVisibility() == View.VISIBLE) {
                binding.relDisSub.setVisibility(View.GONE);
            } else {
                binding.relDisSub.setVisibility(View.VISIBLE);
            }
        }

        public void onRating(View v) {
            if (binding.relRateSub.getVisibility() == View.VISIBLE) {
                binding.relRateSub.setVisibility(View.GONE);
            } else {
                binding.relRateSub.setVisibility(View.VISIBLE);
            }
        }

        public void onApply(View v){
            onBackPressed();
        }
        public void onRest(View v){
            binding.edCity.setText("");
            binding.edNeighborhood.setText("");
            binding.rateBar.setRating(0);
            binding.edFromhp.setText("");
            binding.edTohp.setText("");
            if(binding.checkPrice.isActivated()){
                binding.checkPrice.setActivated(false);
            }
        }

        public void onBack(View v){
            onBackPressed();
        }
    }
}