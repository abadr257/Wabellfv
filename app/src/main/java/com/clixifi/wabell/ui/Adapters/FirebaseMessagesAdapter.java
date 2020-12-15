package com.clixifi.wabell.ui.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.AddReviewObject;
import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.Conv;
import com.clixifi.wabell.data.Response.AddReviews;
import com.clixifi.wabell.ui.chat.ChatScreen;
import com.clixifi.wabell.ui.messagesTap.MessagesFragmentTap;
import com.clixifi.wabell.ui.viewAllCertificates.AllCertificates;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class FirebaseMessagesAdapter extends RecyclerView.Adapter<FirebaseMessagesAdapter.MyViewHolder> {
    Context context ;
    CallsArray array ;
    private LayoutInflater mInflater;
    CustomDialog dialog ;
    private DatabaseReference mConvDatabase;
    private DatabaseReference mMessageDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    MyViewHolder holder;
    private String mCurrent_user_id;
    int pos ;

    public FirebaseMessagesAdapter(Context context, CallsArray array) {
        this.context = context;
        this.array = array;
        dialog = new CustomDialog(context);
        this.mInflater = LayoutInflater.from(context);
        /*mAuth = FirebaseAuth.getInstance();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mConvDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(mCurrent_user_id);

        mConvDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mMessageDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(mCurrent_user_id);
        mUsersDatabase.keepSynced(true);*/

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.room_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        this.holder = holder;
        this.pos = position;

        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.type.setText("تقييم الطالب");
                }else {
                    holder.type.setText("Rate Student");
                }
                holder.rel_online.setVisibility(View.GONE);
            }else {
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.type.setText("تقييم المعلم");
                }else {
                    holder.type.setText("Rate Tutor");
                }
                if(array.getResult().get(position).IsOnline){
                    holder.rel_online.setVisibility(View.VISIBLE);
                }else {
                    holder.rel_online.setVisibility(View.VISIBLE);
                    holder.rel_online.setBackgroundResource(R.drawable.offline);
                }

            }
        }else {
            if(StaticMethods.userData.getUserType().equals("tutor")){
                holder.rel_online.setVisibility(View.GONE);
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.type.setText("تقييم الطالب");
                }else {
                    holder.type.setText("Rate Student");
                }
            }else {
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.type.setText("تقييم المعلم");
                }else {
                    holder.type.setText("Rate Tutor");
                }
                if(array.getResult().get(position).IsOnline){
                    holder.rel_online.setVisibility(View.VISIBLE);
                }else {
                    holder.rel_online.setVisibility(View.VISIBLE);
                    holder.rel_online.setBackgroundResource(R.drawable.offline);
                }
            }
        }
        holder.name.setText(array.getResult().get(position).getFromUserName());
        holder.date.setText(array.getResult().get(position).getDate());
        String time = array.getResult().get(position).getTime().substring(0 , 2);
        String min = array.getResult().get(position).getTime().substring(3 , 5);
        int intTime = Integer.parseInt(time);
        int finalTime = 0 ;
        if(StaticMethods.timeEqu.equals("+")){
            finalTime = intTime + StaticMethods.timeZone;
            if(finalTime > 11){
                if(finalTime > 12){
                    int timeFinal =  finalTime - 12 ;
                    holder.time.setText(timeFinal +" : "+min+" Pm ");
                }else {
                    holder.time.setText(finalTime +" : "+min+" Pm ");
                }
            }else {
                holder.time.setText(finalTime +" : "+min+" Am ");
            }
        }else if(StaticMethods.timeEqu.equals("-")){
            finalTime = intTime - StaticMethods.timeZone;
            if(finalTime > 11){
                if(finalTime > 12){
                    int timeFinal = finalTime - 12 ;
                    holder.time.setText(timeFinal +" : "+min+" Pm ");
                }else {
                    holder.time.setText(finalTime +" : "+min+" Pm ");
                }
            }else {
                holder.time.setText(finalTime +" : "+min+" Am ");
            }
        }
        //holder.time.setText(array.getResult().get(position).getTime());
        Picasso.with(context).load( array.getResult().get(position).getFromUserImage()).into(holder.imgUser);

        holder.lin_rateSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertDialog( array.getResult().get(position).getFromUserName() ,array.getResult().get(position).getFromUserId()
                        , array.getResult().get(position).getFromUserImage());
            }
        });
        holder.lin_OpenMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(context, ChatScreen.class);
                chatIntent.putExtra("user_id", array.getResult().get(position).getFromUserFirebaseId());
                chatIntent.putExtra("user_name", array.getResult().get(position).getFromUserName());
                chatIntent.putExtra("user_image", array.getResult().get(position).getFromUserImage());
                chatIntent.putExtra("userServerId", array.getResult().get(position).getFromUserId());
                context.startActivity(chatIntent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return array.getResult().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , date , time , type ;
        CircleImageView imgUser ;
        LinearLayout lin_rateSt , lin_OpenMessage ;
        RelativeLayout rel_online , rel_isSeen;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rel_isSeen = itemView.findViewById(R.id.rel_isSeen);
            type = itemView.findViewById(R.id.txt_userType);
            rel_online = itemView.findViewById(R.id.rel_online);
            lin_OpenMessage = itemView.findViewById(R.id.lin_OpenMessage);
            lin_rateSt  =itemView.findViewById(R.id.lin_rateSt);
            imgUser = itemView.findViewById(R.id.st_img);
            name = itemView.findViewById(R.id.txt_stName);
            date = itemView.findViewById(R.id.txt_date);
            time = itemView.findViewById(R.id.txt_dateS);

        }
    }
    private void openAlertDialog(final String name ,final String studentId , String userImage) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.rate_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        CircleImageView image = dialogView.findViewById(R.id.tutor_img);
        Picasso.with(context).load(userImage).into(image);

        final Button send = dialogView.findViewById(R.id.btn_submit);
        ImageView close = dialogView.findViewById(R.id.close);
        final EditText review = dialogView.findViewById(R.id.ed_review);
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                review.setHint(context.getResources().getString(R.string.reViewHintStudent));
            }else {
                review.setHint(context.getResources().getString(R.string.reViewHint));
            }
        }else {
            if(StaticMethods.userData.getUserType().equals("tutor")){
                review.setHint(context.getResources().getString(R.string.reViewHintStudent));
            }else {
                review.setHint(context.getResources().getString(R.string.reViewHint));
            }
        }
        final RatingBar rate = dialogView.findViewById(R.id.ratingBar);
        TextView nameS = dialogView.findViewById(R.id.txt_tutorName);
        nameS.setText(name);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messages = review.getText().toString();
                double rating = rate.getRating();
                if (rating == 0.0) {
                    ToastUtil.showErrorToast(context, R.string.RateEmpty);
                } else if (messages.isEmpty()) {
                    ToastUtil.showErrorToast(context, R.string.empty);
                } else {
                    dialog.ShowDialog();
                    sendTheReview(studentId ,rating , messages);
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    private void sendTheReview(String studentId , double rating, String messages) {
        boolean net = StaticMethods.isConnectingToInternet(context);
        if(!net){
            ToastUtil.showErrorToast(context , R.string.noInternet);
            dialog.DismissDialog();
        }else {
            String token ="Bearer "+ StaticMethods.userData.getToken();
            int hisRate = (int) rating;
            MainApi.reviewStudents(token, studentId, hisRate, messages, new ConnectionListener<AddReviews>() {
                @Override
                public void onSuccess(ConnectionResponse<AddReviews> connectionResponse) {
                    if(connectionResponse.data != null){
                        if(connectionResponse.data.message.equals("success")){
                            dialog.DismissDialog();
                            ToastUtil.showSuccessToast(context ,R.string.reviewAdded );
                        }else {
                            dialog.DismissDialog();
                            ToastUtil.showErrorToast(context , R.string.rated);
                        }
                    }


                }

                @Override
                public void onFail(Throwable throwable) {
                    ToastUtil.showErrorToast(context , R.string.error);
                    dialog.DismissDialog();
                    Log.e(TAG, "onFail: Request  "+throwable.toString() );
                }
            });
        }
    }



}
