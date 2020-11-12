package com.clixifi.wabell.ui.tutorProfileforStudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ImageUrl;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;
import com.clixifi.wabell.databinding.ActivityTutorProfileViewBinding;
import com.clixifi.wabell.ui.Adapters.PagerAdapter;
import com.clixifi.wabell.ui.Adapters.ProfilePagerAdapter;
import com.clixifi.wabell.ui.chat.ChatScreen;
import com.clixifi.wabell.ui.register.RegisterScreen;
import com.clixifi.wabell.ui.viewAllCertificates.AllCertificates;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.IntentUtilies;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.clixifi.wabell.utils.StaticMethods.tutorId;

public class TutorProfileView extends AppCompatActivity implements TutorProfileInterface {
    ActivityTutorProfileViewBinding binding;
    TutorProfile handler;
    ProfilePagerAdapter adapter;
    TutorProfilePresenter presenter;
    CustomDialog dialog;
    TutorProfileForStudent data;
    String id;
    boolean favorite = false;
    private String mCurrentUserId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutor_profile_view);
        handler = new TutorProfile(this);
        binding.setHandler(handler);
        presenter = new TutorProfilePresenter(this);
        dialog = new CustomDialog(this);
        configTabs();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        getID();
        dialog.ShowDialog();
    }

    public void onViewAll(ArrayList<ImageUrl> listOfImages) {
        StaticMethods.images = new ArrayList<>();
        StaticMethods.images = listOfImages;
        Log.e("TAG", "onViewAll: " + "Hr");
        IntentUtilies.openActivity(TutorProfileView.this, AllCertificates.class);
    }

    private void getID() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getString("ID");
        }

        presenter.getTutorData(this, id, true);
    }

    private void configTabs() {
        adapter = new ProfilePagerAdapter(getSupportFragmentManager(), TutorProfileView.this);
        binding.tabLayout.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(adapter);
    }

    @Override
    public void onSuccess(TutorProfileForStudent tutor) {
        this.data = tutor;
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                binding.relOnline.setVisibility(View.GONE);
            }else {
                if(tutor.IsOnline){
                    binding.relOnline.setVisibility(View.VISIBLE);
                }else {
                    binding.relOnline.setVisibility(View.VISIBLE);
                    binding.relOnline.setBackgroundResource(R.drawable.offline);
                }

            }
        }else {
            if(StaticMethods.userData.getUserType().equals("tutor")){
                binding.relOnline.setVisibility(View.GONE);
            }else {
                if(tutor.IsOnline){
                    binding.relOnline.setVisibility(View.VISIBLE);
                }else {
                    binding.relOnline.setVisibility(View.VISIBLE);
                    binding.relOnline.setBackgroundResource(R.drawable.offline);
                }
            }
        }
        if (LocaleManager.getLanguage(TutorProfileView.this).equals("ar")) {
            binding.txtPrice.setText(tutor.getHourPrice() + " ريال / س ");
            binding.callsCountTxt.setText(tutor.getCallsCount() + "مكالمة");
            binding.viewCountTxt.setText(tutor.getViewsCount() + " مشاهدة");
        } else {
            binding.txtPrice.setText(tutor.getHourPrice() + " SAR / Hr ");
            binding.callsCountTxt.setText(tutor.getCallsCount() + " Calls");
            Log.e("TAG", "onSuccess: from profile" + tutor.getViewsCount());
            binding.viewCountTxt.setText(tutor.getViewsCount() + " Views");
        }
        StaticMethods.LoadImage(TutorProfileView.this, binding.tutorImg, tutor.getProfilePicture(), null);

        binding.txtTutorName.setText(tutor.getName());
        binding.txtNumOfRate.setText("(" + tutor.getRankCount() + ")");
        binding.txtDisc.setText(tutor.getTagline());
        binding.txtTutorLocation.setText(tutor.getLocation());
        binding.ratingBar.setRating(tutor.getRank());
        if (tutor.IsFavorite) {
            favorite = true;
            binding.favImage.setImageResource(R.drawable.favorite_24);
        }
        if (!tutor.IsSubscribe) {
            binding.message.setVisibility(View.GONE);
            if (LocaleManager.getLanguage(TutorProfileView.this).equals("ar")) {
                binding.call.setText("طلب");
            } else {
                binding.call.setText("Request");
            }
        }
        if(!tutor.IsOnline){
            binding.call.setVisibility(View.GONE);
        }
        if (tutor.IsFeatured) {
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

    @Override
    public void onSendMessage(ResultBoolean resultBoolean) {
        if(resultBoolean.result){
            Intent intent = new Intent(TutorProfileView.this , ChatScreen.class);
            intent.putExtra("user_id",data.getUserFirebaseId());
            intent.putExtra("user_name",data.getName());
            intent.putExtra("user_image",data.getProfilePicture());
            startActivity(intent);
        }else {

        }
    }

    @Override
    public void onCall(ResultBoolean resultBoolean) {
        dialog.DismissDialog();
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
                presenter.unFavorite(TutorProfileView.this, id);
            } else {
                binding.favImage.setImageResource(R.drawable.favorite_24);
                favorite = true;
                presenter.favorite(TutorProfileView.this, id);
                dialog.ShowDialog();
            }


        }

        public void sendMessage(View v) {
            openDialog();
        }

        public void call(View v) {
            if (binding.call.getText().equals("طلب")) {
                openRequestDialog();
            } else if (binding.call.getText().equals("Request")) {
                openRequestDialog();
            } else {
                openCallDialog();
            }

        }

    }

    private void openCallDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.calls_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        Button call = dialogView.findViewById(R.id.btn_call);
        ImageView close = dialogView.findViewById(R.id.close);
        CircleImageView tutor_img = dialogView.findViewById(R.id.tutor_img);
        StaticMethods.LoadImage(TutorProfileView.this, tutor_img, data.getProfilePicture(), null);
        TextView txtPhone = dialogView.findViewById(R.id.phone);
        txtPhone.setText(data.getPhoneNumber() + "");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addRequestLogCall(TutorProfileView.this , id ,"" );
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getPhoneNumber()));

                if (ContextCompat.checkSelfPermission(TutorProfileView.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TutorProfileView.this, new String[]{Manifest.permission.CALL_PHONE},2);
                }
                else
                {
                    startActivity(intent);
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
    private void openRequestDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.send_request_tutor, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        ImageView close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Button done = dialogView.findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
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
        CircleImageView tutor_img = dialogView.findViewById(R.id.tutor_img);
        TextView name = dialogView.findViewById(R.id.txt_tutorName);
        StaticMethods.LoadImage(TutorProfileView.this, tutor_img, data.getProfilePicture(), null);
        name.setText(data.getName());
        final AlertDialog alertDialog = dialogBuilder.create();
        Button send = dialogView.findViewById(R.id.btn_submit);

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
                if (messages.isEmpty()) {
                    ToastUtil.showErrorToast(TutorProfileView.this, R.string.empty);
                } else {
                    presenter.addRequestLogMessage(TutorProfileView.this , id ,messages );
                   // dialog.ShowDialog();
                    sendMessageToChat(messages , data.getUserFirebaseId());
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
    public void sendMessageToChat(String message , String userFireId) {
        DatabaseReference mRootRef;
        mRootRef = FirebaseDatabase.getInstance().getReference();

        if(!TextUtils.isEmpty(message)){

            String current_user_ref = "messages/" + mCurrentUserId + "/" + userFireId;
            String chat_user_ref = "messages/" + userFireId + "/" + mCurrentUserId;

            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(mCurrentUserId).child(userFireId).push();

            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);
            messageMap.put("RecUser", id);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            //binding.edMessage.setText("");

            mRootRef.child("Chat").child(mCurrentUserId).child(userFireId).child("seen").setValue(true);
            mRootRef.child("Chat").child(mCurrentUserId).child(userFireId).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.child("Chat").child(userFireId).child(mCurrentUserId).child("seen").setValue(false);
            mRootRef.child("Chat").child(userFireId).child(mCurrentUserId).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){

                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }

                }
            });

        }

    }
}