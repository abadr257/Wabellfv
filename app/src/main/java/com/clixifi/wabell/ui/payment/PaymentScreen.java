package com.clixifi.wabell.ui.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.clixifi.wabell.utils.CheckoutBroadcastReceiver;
import com.clixifi.wabell.R;
import com.clixifi.wabell.databinding.ActivityPaymentScreenBinding;
import com.clixifi.wabell.utils.CustomDialog;
import com.clixifi.wabell.utils.LocaleManager;
import com.clixifi.wabell.utils.ToastUtil;
import com.clixifi.wabell.utils.network.MainApiBody;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.WalletConstants;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.dialog.GooglePayHelper;
import com.oppwa.mobile.connect.checkout.dialog.PaymentButtonFragment;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.exception.PaymentException;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;

import java.util.LinkedHashSet;
import java.util.Set;

import okhttp3.RequestBody;

public class PaymentScreen extends AppCompatActivity implements PaymentInterface {
    ActivityPaymentScreenBinding binding;
    MyHandler handler;
    int packageId = 0;
    String amount;
    PaymentPresenter presenter;
    CustomDialog dialog;
    String checkOut = "";
    PaymentButtonFragment paymentButtonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_screen);
        handler = new MyHandler(this);
        binding.setHandler(handler);

        getBund();
        if (amount.equals("0.00")){
            binding.btnDone.setVisibility(View.VISIBLE);
        }else {
            paymentButtonFragment = (PaymentButtonFragment) getFragmentManager().findFragmentById(R.id.payment_button_fragment);
            paymentButtonFragment.setPaymentBrand("VISA");
            paymentButtonFragment.getPaymentButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.ShowDialog();
                    RequestBody body = null;
                    try {
                        body = MainApiBody.paymentBody(packageId);
                    } catch (Exception e) {

                    }

                    presenter.doPayment(PaymentScreen.this, packageId);
                }
            });
        }


        presenter = new PaymentPresenter(this);
        dialog = new CustomDialog(this);

    }

    private void getBund() {
        packageId = getIntent().getIntExtra("id", 0);
        amount = getIntent().getStringExtra("amount");

        String duration = getIntent().getStringExtra("duration");
        String type = getIntent().getStringExtra("type");
        binding.txtDuration.setText(duration);
        binding.txtType.setText(type);

        if (LocaleManager.getLanguage(this).equals("ar")) {
            binding.txtPrice.setText(amount + " ريال ");
        } else {
            binding.txtPrice.setText(amount + " SAR ");
        }

    }

    @Override
    public void onPayment(String response) {
        this.checkOut = response;
        dialog.DismissDialog();
        Log.e("TAG", "onPayment: " + response);
        Set<String> paymentBrands = new LinkedHashSet<String>();

        paymentBrands.add("VISA");
        paymentBrands.add("MASTER");
        paymentBrands.add("DIRECTDEBIT_SEPA");
        paymentBrands.add("MADA");
        CheckoutSettings checkoutSettings = new CheckoutSettings(response, paymentBrands, Connect.ProviderMode.TEST);
        checkoutSettings.getGooglePayAllowedPaymentMethods();
        checkoutSettings.setGooglePayPaymentDataRequest(getGooglePayRequest());
        checkoutSettings.setCheckoutId(response);
        checkoutSettings.setShopperResultUrl("wabell://result");
        Intent intent = checkoutSettings.createCheckoutActivityIntent(this);

        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
        /*try {
            paymentButtonFragment.submitTransaction(checkoutSettings,null);
        } catch (PaymentException e) {
            Log.e("TAG", "PaymentException: " + e.toString());
        }*/




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

    private PaymentDataRequest getGooglePayRequest() {
        return GooglePayHelper.preparePaymentDataRequestBuilder(
                amount,
                "SAR",
                "146541354",
                getPaymentMethodsForGooglePay(),
                getDefaultCardNetworksForGooglePay()
        ).build();
    }
    @Override
    public void onConnection(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showErrorToast(PaymentScreen.this, R.string.noInternet);
    }
    private Integer[] getPaymentMethodsForGooglePay() {
        return new Integer[] {
                WalletConstants.PAYMENT_METHOD_CARD,
                WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
        };
    }

    private Integer[] getDefaultCardNetworksForGooglePay() {
        return new Integer[] {
                WalletConstants.CARD_NETWORK_VISA,
                WalletConstants.CARD_NETWORK_MASTERCARD,
                WalletConstants.CARD_NETWORK_AMEX
        };
    }
    @Override
    public void onFail(boolean isConnected) {
        dialog.DismissDialog();
        ToastUtil.showSuccessToast(PaymentScreen.this, R.string.successPackage);
    }

    @Override
    public void onPaymentSuccess(boolean success) {
        dialog.DismissDialog();
        alertDialog();
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


            presenter.doPayment(PaymentScreen.this, packageId);
        }

        public void back(View view) {
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    Transaction transaction = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    /* resource path if needed */
                    //String resourcePath = data.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* check the result of synchronous transaction */
                        Log.e("TAG", "onActivityResult: "+"I`m hero " );
                    } else {
                        /* wait for the asynchronous transaction callback in the onNewIntent() */
                    }
                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    /* shopper canceled the checkout process */
                    Log.e("TAG", "onActivityResult: " + "RESULT_CANCELED");
                    break;
                case CheckoutActivity.RESULT_ERROR:
                    /* error occurred */
                    PaymentError error = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR);
                    Log.e("TAG", "onActivityResult: Error "+error.getErrorInfo() );
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getScheme().equals("wabell")) {
            String checkoutId = intent.getData().getQueryParameter("id");
            Log.e("TAG", "onNewIntent: "+checkoutId );
            dialog.ShowDialog();
            presenter.requestStatus(PaymentScreen.this , packageId,checkoutId);
            /* request payment status */
        }
    }

}