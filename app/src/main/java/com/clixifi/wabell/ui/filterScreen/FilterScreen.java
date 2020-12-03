package com.clixifi.wabell.ui.filterScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.User.RegisterData;
import com.clixifi.wabell.data.Response.User.UserResponse;
import com.clixifi.wabell.data.Response.areas.AreasItem;
import com.clixifi.wabell.data.Response.cities.CityItem;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.databinding.ActivityFilterScreenBinding;
import com.clixifi.wabell.ui.homeStudent.StudentHomeInterface;
import com.clixifi.wabell.ui.homeStudent.StudentHomePresenter;
import com.clixifi.wabell.ui.registerTutor.TutorInterface;
import com.clixifi.wabell.ui.registerTutor.TutorPresenter;
import com.clixifi.wabell.ui.search.SearchScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtil;
import com.clixifi.wabell.utils.dialogs.DialogUtilResponse;
import com.clixifi.wabell.utils.network.MainApiBody;

import java.util.ArrayList;

import okhttp3.RequestBody;

public class FilterScreen extends AppCompatActivity implements StudentHomeInterface , DialogUtilResponse , TutorInterface {
    ActivityFilterScreenBinding binding;
    FilterHandler handler;
    StudentHomePresenter presenter ;
    RequestBody body = null ;
    DialogUtil dialogUtil;
    TutorPresenter tutorPresenter;
    ArrayList<CityItem> citiesList;
    ArrayList<AreasItem> areasList;
    int locationId = 0 , areaId = 0;
    CustomDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_screen);
        handler = new FilterHandler(this);
        binding.setHandler(handler);
        presenter = new StudentHomePresenter(this);
        tutorPresenter = new TutorPresenter(this);
        tutorPresenter.getCities(this);
        dialogUtil = new DialogUtil(this);
        dialog =new CustomDialog(this);
        binding.checkPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.checkLowest.isChecked()){
                    binding.checkLowest.setChecked(false);
                }

            }
        });
        binding.checkLowest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.checkPrice.isChecked()){
                    binding.checkPrice.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onFeaturedTutors(FeaturedArray featuredArray) {

    }

    @Override
    public void onFailFeatured(boolean failFeatured) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this ,R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {

    }

    @Override
    public void onLogs(TutorListArray array) {

    }

    @Override
    public void onFailLogs(boolean failLogs) {

    }

    @Override
    public void onFilter(TutorListArray array) {
        StaticMethods.tutors = array ;
        dialog.DismissDialog();
        IntentUtilies.openActivity(FilterScreen.this , SearchScreen.class);
        this.finish();
    }

    @Override
    public void selectedValueSingleChoice(int position) {

    }

    @Override
    public void selectedValueSingleChoice(int position, String arrayType) {
        if (arrayType.equals("city")) {
            locationId = citiesList.get(position).getId();
            binding.edCity.setText(citiesList.get(position).getName());
            binding.edNeighborhood.setText("");
        } else if (arrayType.equals("area")) {
            areaId = areasList.get(position).getId();
            binding.edNeighborhood.setText(areasList.get(position).getName());
        }
    }

    @Override
    public void selectedMultiChoicelang(ArrayList<String> choices, ArrayList<String> postions, String arrayType) {

    }

    @Override
    public void onSuccess(UserResponse<RegisterData> data) {

    }

    @Override
    public void onFail(boolean fail, String error) {

    }

    @Override
    public void onNoConnection(boolean noConnection) {

    }

    @Override
    public void onCity(ArrayList<CityItem> cityItems) {
        citiesList = new ArrayList<>();
        citiesList = cityItems;
    }

    @Override
    public void onArea(ArrayList<AreasItem> areasItems) {
        areasList = new ArrayList<>();
        areasList = areasItems;
        if (areasList != null) {
            ArrayList<String> areasName = new ArrayList<>();
            ArrayList<Integer> areasId = new ArrayList<>();
            for (AreasItem item : areasList) {
                areasName.add(item.getName());
                areasId.add(item.getId());
            }
            dialogUtil.showSingleChooiceArrayList(FilterScreen.this, R.string.city, R.string.ok, areasName, "area", areasId);
        }
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
                binding.relSortSub2.setVisibility(View.GONE);
            } else {
                binding.relSortSub.setVisibility(View.VISIBLE);
                binding.relSortSub2.setVisibility(View.VISIBLE);
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
        public void city(View v){
            if (citiesList != null) {
                ArrayList<String> citiesName = new ArrayList<>();
                ArrayList<Integer> citiesId = new ArrayList<>();
                for (CityItem item : citiesList) {
                    citiesName.add(item.getName());
                    citiesId.add(item.getId());
                }
                dialogUtil.showSingleChooiceArrayList(FilterScreen.this, R.string.city, R.string.ok, citiesName, "city", citiesId);
            }
        }
        public void area(View v){
            if(!binding.edCity.getText().toString().isEmpty()){
                tutorPresenter.getAres(FilterScreen.this, locationId);
            }else {
                ToastUtil.showErrorToast(FilterScreen.this , R.string.emptyCity);
            }
        }
        public void onApply(View v){
            dialog.ShowDialog();
            String  fromHour= "" , toHour = "" ;
            boolean price = false ;
            boolean priceLo = false;
            int rate = 0 ;
            if(!binding.edFromhp.getText().toString().isEmpty()){
                 fromHour = binding.edFromhp.getText().toString();
                 toHour = binding.edTohp.getText().toString();
            }else{

            }

            rate = (int) binding.rateBar.getRating();
            if(binding.checkPrice.isChecked()){
                price = true ;
                binding.checkLowest.setChecked(false);
            }else if(binding.checkLowest.isChecked()) {
                binding.checkPrice.setChecked(false);
                priceLo = true ;
            }
            try {
                body = MainApiBody.filterBody(fromHour , toHour , rate , locationId , areaId ,price , "" ,priceLo );
            }catch (Exception e){
                Log.e("TAG", "onApply: "+e );
            }
            StaticMethods.printJson("Body : ->" ,body);
            presenter.getTutorList(FilterScreen.this ,body);
        }
        public void onRest(View v){
            StaticMethods.tutors = null ;
            binding.edCity.setText("");
            binding.edNeighborhood.setText("");
            binding.rateBar.setRating(0);
            binding.edFromhp.setText("");
            binding.edTohp.setText("");
            if(binding.checkPrice.isChecked()){
                binding.checkPrice.setChecked(false);
                binding.checkLowest.setChecked(false);
            }
        }

        public void onBack(View v){
            onBackPressed();
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }
}