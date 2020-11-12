package com.clixifi.wabell.ui.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityPaymentScreenBinding;
import com.clixifi.wabell.ui.tutorProfileforStudent.TutorProfileView;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.StaticMethods;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.MainApiBody;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

public class PaymentScreen extends AppCompatActivity implements PaymentInterface {
    ActivityPaymentScreenBinding binding;
    MyHandler handler;
    int packageId = 0;
    double amount = 0.0;
    PaymentPresenter presenter;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_screen);
        handler = new MyHandler(this);
        binding.setHandler(handler);
        presenter = new PaymentPresenter(this);
        dialog = new CustomDialog(this);
        getBund();
    }

    private void getBund() {
        packageId = getIntent().getIntExtra("id", 0);
        amount = getIntent().getIntExtra("amount", 0);
        String duration = getIntent().getStringExtra("duration");
        String type = getIntent().getStringExtra("type");
        binding.txtDuration.setText(duration);
        binding.txtType.setText(type);

        if (LocaleManager.getLanguage(this).equals("ar")) {
            binding.txtPrice.setText(amount + "ريال");
        } else {
            binding.txtPrice.setText(amount + "SAR");
        }
    }

    @Override
    public void onPayment(String response) {

        if (response.startsWith("successfully")) {
            dialog.DismissDialog();
            alertDialog();
        } else {
            dialog.DismissDialog();
            ToastUtil.showErrorToast(PaymentScreen.this, R.string.error);
        }

    }

    private void alertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.success_payment, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        ImageView close = dialogView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(PaymentScreen.this, R.string.noInternet);
    }

    @Override
    public void onFail(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(PaymentScreen.this, R.string.error);
    }

    public class MyHandler {
        Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        public void completePayment(View v) {
            String cardName = binding.edHolderName.getText().toString();
            String cardNum = binding.edCardPassword.getText().toString();
            String expMonth = binding.edExpFrom.getText().toString();
            String expYear = binding.edExpTo.getText().toString();
            String cvv = binding.edCvvTo.getText().toString();

            if (cardNum.isEmpty()) {
                dialog.DismissDialog();
                ToastUtil.showErrorToast(PaymentScreen.this, R.string.empty);
            } else if (expMonth.isEmpty() || expYear.isEmpty()) {
                dialog.DismissDialog();
                ToastUtil.showErrorToast(PaymentScreen.this, R.string.empty);
            } else if (cvv.isEmpty()) {
                dialog.DismissDialog();
                ToastUtil.showErrorToast(PaymentScreen.this, R.string.empty);
            } else {
                dialog.ShowDialog();
                RequestBody body = null;
                try {
                    body = MainApiBody.paymentBody(String.valueOf(amount), cardNum, cardName, expMonth, expYear, cvv, packageId);
                } catch (Exception e) {

                }

                presenter.doPayment(PaymentScreen.this, body);
            }
        }

        public void back(View view) {
            onBackPressed();
        }
    }
}