package com.clixifi.wabell.ui.tutorProfileforStudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.databinding.ActivityTutorProfileViewBinding;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.ui.Adapters.ProfilePagerAdapter;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.ui.viewAllCertificates.AllCertificates;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;

import java.util.ArrayList;

import static com.clixifi.wabell.utils.StaticMethods.tutorId;

public class TutorProfileView extends AppCompatActivity implements TutorProfileInterface {
    ActivityTutorProfileViewBinding binding;
    TutorProfile handler;
    ProfilePagerAdapter adapter;
    TutorProfilePresenter presenter;
    CustomDialog dialog;

    String id;
    boolean favorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutor_profile_view);
        handler = new TutorProfile(this);
        binding.setHandler(handler);
        presenter = new TutorProfilePresenter(this);
        dialog = new CustomDialog(this);
        configTabs();

        getID();
        dialog.ShowDialog();
    }
    public void onViewAll(ArrayList<ImageUrl> listOfImages){
        StaticMethods.images  = new ArrayList<>();
        StaticMethods.images = listOfImages ;
        IntentUtilies.openActivity(TutorProfileView.this , AllCertificates.class);
    }
    private void getID() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getString("ID");
        }

        presenter.getTutorData(this, id , true);
    }

    private void configTabs() {
        adapter = new ProfilePagerAdapter(getSupportFragmentManager(), TutorProfileView.this);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }

    @Override
    public void onSuccess(TutorProfileForStudent tutor) {
        if (LocaleManager.getLanguage(TutorProfileView.this).equals("ar")) {
            binding.txtPrice.setText(tutor.getHourPrice() + "ريال / للساعة");
            binding.callsCountTxt.setText(tutor.getCallsCount() + "مكالمة");
            binding.viewCountTxt.setText(tutor.getViewsCount() + " مشاهدة");
        } else {
            binding.txtPrice.setText(tutor.getHourPrice() + "SAR/hr");
            binding.callsCountTxt.setText(tutor.getCallsCount() + " Calls");
            Log.e("TAG", "onSuccess: from profile" + tutor.getViewsCount());
            binding.viewCountTxt.setText(tutor.getViewsCount() + " Views");
        }
        StaticMethods.LoadImage(TutorProfileView.this , binding.tutorImg,tutor.getProfilePicture() ,null);
        binding.txtTutorName.setText(tutor.getName());
        binding.txtNumOfRate.setText("(" + tutor.getRankCount() + ")");
        binding.txtDisc.setText(tutor.getBiography());
        binding.txtTutorLocation.setText(tutor.getLocation());
        binding.ratingBar.setRating(tutor.getRank());
        if (tutor.IsFavorite) {
            favorite = true;
            binding.favImage.setImageResource(R.drawable.favorite_24);
        }
        if (!tutor.IsSubscribe) {
            binding.message.setVisibility(View.GONE);
            if(LocaleManager.getLanguage(TutorProfileView.this ).equals("ar")){
                binding.call.setText("طلب");
            }else {
                binding.call.setText("Request");
            }
        }
        if (tutor.IsFeatured){
            binding.isFea.setVisibility(View.VISIBLE);
        }
        dialog.DismissDialog();
    }

    @Override
    public void onFail(boolean fail) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(TutorProfileView.this, R.string.error);
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(TutorProfileView.this, R.string.noInternet);
    }

    @Override
    public void OnAddedToFavorite(AddFavorite addFavorite) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(TutorProfileView.this, R.string.saved);
    }

    @Override
    public void onDeleteFav(ResultBoolean result) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(TutorProfileView.this, R.string.saved);
    }

    public class TutorProfile {
        Context context;

        public TutorProfile(Context context) {
            this.context = context;
        }

        public void back(View view) {
            tutorId = null;
            onBackPressed();
        }

        public void addtoFav(View view) {
            if (favorite) {
                binding.favImage.setImageResource(R.drawable.unfav_master);
                favorite = false;
                dialog.ShowDialog();
                presenter.unFavorite(TutorProfileView.this , id);
            } else {
                binding.favImage.setImageResource(R.drawable.favorite_24);
                favorite = true;
                presenter.favorite(TutorProfileView.this, id);
                dialog.ShowDialog();
            }


        }

        public void sendMessage(View v){
            openDialog();
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.onAttach(base));
    }

    private void openDialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.send_message_dialoge, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        Button send = dialogView.findViewById(R.id.btn_send);
        ImageView close = dialogView.findViewById(R.id.close);
        final EditText message = dialogView.findViewById(R.id.ed_review);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messages = message.getText().toString();
                if(messages.isEmpty()){
                    ToastUtil.showErrorToast(TutorProfileView.this , R.string.empty);
                }else {
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}