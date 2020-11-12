package com.clixifi.wabell.ui.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.data.AddReviewObject;
import com.clixifi.wabell.data.CallsArray;
import com.clixifi.wabell.data.Response.AddReviews;
import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.ui.chat.ChatScreen;
import com.clixifi.wabell.ui.main.MainScreen;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.ConnectionListener;
import com.clixifi.wabell.utils.network.ConnectionResponse;
import com.clixifi.wabell.utils.network.MainApi;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class RequestLogsAdapter extends RecyclerView.Adapter<RequestLogsAdapter.MyViewHolder> {
    RequestLogsArray array;
    Context context;
    private LayoutInflater mInflater;
    CustomDialog dialog ;



    public RequestLogsAdapter(RequestLogsArray array, Context context) {
        this.array = array;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        dialog = new CustomDialog(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.request_logs_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.stName.setText(array.getResult().get(position).getFromUserName());
        StaticMethods.LoadImage(context , holder.st_img,array.getResult().get(position).getFromUserImage() ,null);
        if (array.getResult().get(position).getType().equals("CallLog")) {
            if (LocaleManager.getLanguage(context).equals("ar")) {
                holder.txt_call.setText("مكالمة");
                holder.img.setImageResource(R.drawable.calls_icon);
            } else {
                holder.txt_call.setText("Call");
                holder.img.setImageResource(R.drawable.calls_icon);
            }
            holder.linCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + array.getResult().get(position).getFromUserPhoneNumber()));
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},2);
                    }
                    else
                    {
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            if (LocaleManager.getLanguage(context).equals("ar")) {
                holder.txt_call.setText("اظهار الرسائل");
                holder.img.setImageResource(R.drawable.message_logs);
            } else {
                holder.txt_call.setText("View Messages");
                holder.img.setImageResource(R.drawable.message_logs);
            }
            holder.linCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ChatScreen.class);
                    intent.putExtra("user_id",array.getResult().get(position).getUserFirebaseId());
                    intent.putExtra("user_name",array.getResult().get(position).getFromUserName());
                    intent.putExtra("user_image",array.getResult().get(position).getFromUserImage());
                    context.startActivity(intent);
                }
            });
        }
        holder.linRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertDialog(array.getResult().get(position).getFromUserName(),array.getResult().get(position).getFromUserId()
                        , array.getResult().get(position).getFromUserImage());
            }
        });
        holder.stBio.setText(array.getResult().get(position).getDate());
        holder.date.setText(array.getResult().get(position).getTime());
        if(StaticMethods.userRegisterResponse != null){
            if(StaticMethods.userRegisterResponse.Data.getType().equals("tutor")){
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.txtRate.setText("تقييم الطالب");
                }else {
                    holder.txtRate.setText("Rate Student");
                }
                holder.status.setVisibility(View.GONE);
            }else {
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.txtRate.setText("تقييم المعلم");
                }else {
                    holder.txtRate.setText("Rate Tutor");
                }
                if(array.getResult().get(position).isOnline){
                    holder.status.setVisibility(View.VISIBLE);
                }else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }

            }
        }else {
            if(StaticMethods.userData.getUserType().equals("tutor")){
                holder.status.setVisibility(View.GONE);
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.txtRate.setText("تقييم الطالب");
                }else {
                    holder.txtRate.setText("Rate Student");
                }
            }else {
                if(LocaleManager.getLanguage(context).equals("ar")){

                    holder.txtRate.setText("تقييم المعلم");
                }else {
                    holder.txtRate.setText("Rate Tutor");
                }
                if(array.getResult().get(position).isOnline){
                    holder.status.setVisibility(View.VISIBLE);
                }else {
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setBackgroundResource(R.drawable.offline);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (array.getResult() != null) {
            return array.getResult().size();
        } else {
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView st_img;
        TextView stName, stBio, date, txt_call , txtRate ;
        ImageView img;
        LinearLayout linCall, linRate;
        RelativeLayout status ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRate = itemView.findViewById(R.id.rateTxt);
            status = itemView.findViewById(R.id.rel_online);
            img = itemView.findViewById(R.id.call);
            txt_call = itemView.findViewById(R.id.txt_call);
            st_img = itemView.findViewById(R.id.st_img);
            linRate = itemView.findViewById(R.id.lin_rateSt);
            stName = itemView.findViewById(R.id.txt_stName);
            stBio = itemView.findViewById(R.id.txt_stBio);
            date = itemView.findViewById(R.id.txt_date);
            linCall = itemView.findViewById(R.id.lin_callOrMessage);
        }
    }

    private void openAlertDialog(final String name ,final String studentId , String userImage) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.rate_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        CircleImageView image = dialogView.findViewById(R.id.tutor_img);
        StaticMethods.LoadImage(context , image,userImage ,null);
        final Button send = dialogView.findViewById(R.id.btn_submit);
        ImageView close = dialogView.findViewById(R.id.close);
        final EditText review = dialogView.findViewById(R.id.ed_review);
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
                    if(connectionResponse.data.message.equals("success")){
                        dialog.DismissDialog();
                        ToastUtil.showSuccessToast(context ,R.string.reviewAdded );
                    }else {
                        dialog.DismissDialog();
                        ToastUtil.showErrorToast(context , connectionResponse.data.message);
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
