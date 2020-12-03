package com.clixifi.wabell.ui.searchByWord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;
import com.clixifi.wabell.databinding.ActivitySearchByWordBinding;
import com.clixifi.wabell.ui.filterScreen.FilterScreen;
import com.clixifi.wabell.ui.homeStudent.StudentHomeInterface;
import com.clixifi.wabell.ui.homeStudent.StudentHomePresenter;
import com.clixifi.wabell.ui.search.SearchScreen;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.MainApiBody;

import okhttp3.RequestBody;

import static com.clixifi.wabell.utils.StaticMethods.searchWord;

public class SearchByWord extends AppCompatActivity implements StudentHomeInterface {
    ActivitySearchByWordBinding binding;
    MyHandler handler;
    StudentHomePresenter presenter;
    RequestBody body = null;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_by_word);
        handler = new MyHandler(this);
        presenter = new StudentHomePresenter(this);

        dialog = new CustomDialog(this);
        binding.setHandler(handler);
    }


    @Override
    public void onFeaturedTutors(FeaturedArray featuredArray) {

    }

    @Override
    public void onFailFeatured(boolean failFeatured) {

    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(SearchByWord.this, R.string.noInternet);
    }

    @Override
    public void onLogs(TutorListArray array) {

    }

    @Override
    public void onFailLogs(boolean failLogs) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(SearchByWord.this, R.string.error);

    }

    @Override
    public void onFilter(TutorListArray array) {
        StaticMethods.tutors = array;
        dialog.DismissDialog();
        IntentUtilies.openActivity(SearchByWord.this, SearchScreen.class);
        searchWord = binding.edSearch.getText().toString();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void onCancel(View v) {
            onBackPressed();
        }

        public void onSearch(View v) {
            dialog.ShowDialog();
            String fromHour = "", toHour = "";
            boolean price = false;
            String searchWord = binding.edSearch.getText().toString();
            if (searchWord.isEmpty()) {
                dialog.DismissDialog();
                ToastUtil.showErrorToast(SearchByWord.this, R.string.empty);
            } else {
                try {
                    body = MainApiBody.filterBody(fromHour, toHour, 0, 0, 0, price, searchWord, false);
                } catch (Exception e) {
                    Log.e("TAG", "onApply: " + e);
                }
                StaticMethods.printJson("Body : ->", body);
                presenter.getTutorList(SearchByWord.this, body);
            }

        }
    }
}