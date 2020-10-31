package com.clixifi.wabell.ui.topicsSummary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.databinding.ActivityTopicsSummaryScreenBinding;
import com.clixifi.wabell.ui.Adapters.summarySecLevelAdapter.SummarySecAdapter;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

public class TopicsSummaryScreen extends AppCompatActivity implements SummaryInterface{
    ActivityTopicsSummaryScreenBinding binding;
    Summary handler;
    SummaryPresenter presenter ;
    CustomDialog dialog ;
    Bundle bundle ;
    String name , type;
    SummarySecAdapter adapter ;
    int id ;
    boolean  edit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topics_summary_screen);
        handler = new Summary(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.setHandler(handler);
        getId();
    }

    private void getId() {
        dialog = new CustomDialog(this);
        presenter = new SummaryPresenter(this);
        bundle = getIntent().getExtras();
        try {
            id = bundle.getInt("ID");
            name = bundle.getString("name");
            edit = bundle.getBoolean("edit");
            type = bundle.getString("type");
        }catch (Exception e){

        }

        binding.title.setText(name);
        dialog.ShowDialog();
        presenter.getSummary(TopicsSummaryScreen.this , id);
    }

    @Override
    public void onSummarySuccess(ChildResponse response) {
        dialog.DismissDialog();
        String lang = LocaleManager.getLanguage(TopicsSummaryScreen.this);
        adapter = new SummarySecAdapter(this , response , lang);
        binding.recSummary.setLayoutManager(new LinearLayoutManager(this));
        binding.recSummary.setAdapter(adapter);
    }

    @Override
    public void onCancel(boolean response) {
        if(response){
            dialog.DismissDialog();
            Bundle bundle = new Bundle();
            if (StaticMethods.userRegisterResponse != null){
                bundle.putString("type", StaticMethods.userRegisterResponse.Data.getType());
            }else {
                bundle.putString("type", StaticMethods.userData.getUserType());
            }
            bundle.putBoolean("edit" ,edit );
            bundle.putInt("position",0);
            IntentUtilies.openActivityWithBundle(TopicsSummaryScreen.this, TutorSteps.class, bundle);
            finish();
        }

    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(TopicsSummaryScreen.this,R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(TopicsSummaryScreen.this,R.string.noInternet);
    }

    @Override
    public void onNothing(boolean isNothing) {

    }


    public class Summary {
        Context context;

        public Summary(Context context) {
            this.context = context;
        }

        public void finishHandler(View v) {
            Bundle bundle = new Bundle();
            if (StaticMethods.userRegisterResponse != null){
                bundle.putString("type", StaticMethods.userRegisterResponse.Data.getType());
            }else {
                bundle.putString("type", StaticMethods.userData.getUserType());
            }
            bundle.putBoolean("edit" ,edit );
            bundle.putInt("position",0);
            IntentUtilies.openActivityWithBundle(TopicsSummaryScreen.this, TutorSteps.class, bundle);
            finish();
        }

        public void cancel(View v) {
            dialog.ShowDialog();
            presenter.cancelAll(TopicsSummaryScreen.this , id);
        }

    }
}