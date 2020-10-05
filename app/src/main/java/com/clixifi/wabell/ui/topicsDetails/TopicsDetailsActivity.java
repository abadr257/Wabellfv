package com.clixifi.wabell.ui.topicsDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.topicChild.ChildResponse;
import com.clixifi.wabell.databinding.ActivityTopicsDetailsBinding;
import com.clixifi.wabell.ui.Adapters.TopicsChildAdapter;
import com.clixifi.wabell.ui.code.VerificationCodeScreen;
import com.clixifi.wabell.ui.topicsSummary.SummaryInterface;
import com.clixifi.wabell.ui.topicsSummary.SummaryPresenter;
import com.clixifi.wabell.ui.topicsSummary.TopicsSummaryScreen;
import com.clixifi.wabell.ui.tutorSteps.TutorSteps;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import static android.content.ContentValues.TAG;

public class TopicsDetailsActivity extends AppCompatActivity implements TopicsDetailsInterface , SummaryInterface {
    ActivityTopicsDetailsBinding binding ;
    MyHandler handler ;
    Bundle bundle ;
    SummaryPresenter summaryPresenter ;
    TopicsDetailsPresenter presenter ;
    CustomDialog dialog ;
    TopicsChildAdapter adapter ;
    String name , type;
    int id ;
    boolean isSummary = false , edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_topics_details);
        handler = new MyHandler(this);
        binding.setHandler(handler);
        getId();
    }

    private void getId() {
        summaryPresenter = new SummaryPresenter(this);
        dialog = new CustomDialog(this);
        presenter = new TopicsDetailsPresenter(this);
        bundle = getIntent().getExtras();
        try {
            id = bundle.getInt("ID");
            name = bundle.getString("name");
            edit = bundle.getBoolean("edit");
            type = bundle.getString("type");
        }catch (Exception e){

        }

        binding.title.setText(name);
        presenter.getChilds(TopicsDetailsActivity.this ,id);
        dialog.ShowDialog();
    }

    @Override
    public void onSuccess(ChildResponse childResponse) {
        dialog.DismissDialog();
        adapter = new TopicsChildAdapter(this , childResponse);
        binding.recChild.setLayoutManager(new LinearLayoutManager(this));
        binding.recChild.setAdapter(adapter);
    }

    @Override
    public void onSummarySuccess(ChildResponse response) {
        dialog.DismissDialog();
        isSummary = true ;
        if(isSummary){
            Bundle bundle = new Bundle();
            bundle.putInt("ID" , id);
            bundle.putString("name" , name);
            bundle.putString("type" , type);
            bundle.putBoolean("edit" ,edit );
            IntentUtilies.openActivityWithBundle(TopicsDetailsActivity.this , TopicsSummaryScreen.class , bundle);
        }else {
            ToastUtil.showErrorToast(TopicsDetailsActivity.this ,R.string.noThing);
        }
    }

    @Override
    public void onCancel(boolean response) {

    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(this , R.string.noInternet);
    }

    @Override
    public void onNothing(boolean isNothing) {
        dialog.DismissDialog();
        isSummary = false ;
    }

    public class MyHandler{
        Context context ;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void back(View v){
            if(StaticMethods.userRegisterResponse != null){
                if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "tutor");
                    bundle.putBoolean("edit" ,edit );
                    IntentUtilies.openActivityWithBundle(TopicsDetailsActivity.this, TutorSteps.class, bundle);
                    finish();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "student");
                    bundle.putBoolean("edit" ,edit );
                    IntentUtilies.openActivityWithBundle(TopicsDetailsActivity.this, TutorSteps.class, bundle);
                    finish();
                }
            }else {
                if(StaticMethods.userData.getUserType().equals("tutor")){
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "tutor");
                    bundle.putBoolean("edit" ,edit );
                    IntentUtilies.openActivityWithBundle(TopicsDetailsActivity.this, TutorSteps.class, bundle);
                    finish();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "student");
                    bundle.putBoolean("edit" ,edit );
                    IntentUtilies.openActivityWithBundle(TopicsDetailsActivity.this, TutorSteps.class, bundle);
                    finish();
                }
            }


        }

        public void next(View v){
            dialog.ShowDialog();
            summaryPresenter.getSummary(TopicsDetailsActivity.this ,id);

        }
        public void cancel(View v){

        }
    }
}