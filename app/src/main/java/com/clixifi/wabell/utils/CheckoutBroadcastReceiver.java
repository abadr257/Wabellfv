package com.clixifi.wabell.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;

public class CheckoutBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (CheckoutActivity.ACTION_ON_BEFORE_SUBMIT.equals(action)) {
            String paymentBrand = intent.getStringExtra(CheckoutActivity.EXTRA_PAYMENT_BRAND);
            String checkoutId = intent.getStringExtra(CheckoutActivity.EXTRA_CHECKOUT_ID);
            ComponentName senderComponentName = intent.getParcelableExtra(
                    CheckoutActivity.EXTRA_SENDER_COMPONENT_NAME);


            /* You can use this callback to request a new checkout ID if selected payment brand requires
               some specific parameters or just send back the same checkout id to continue checkout process */
            intent = new Intent(CheckoutActivity.ACTION_ON_BEFORE_SUBMIT);
            intent.setComponent(senderComponentName);
            intent.setPackage(senderComponentName.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CheckoutActivity.EXTRA_CHECKOUT_ID, checkoutId);

            /* You also can abort the transaction by sending the CheckoutActivity.EXTRA_TRANSACTION_ABORTED */
            intent.putExtra(CheckoutActivity.EXTRA_TRANSACTION_ABORTED, true);

            context.startActivity(intent);
        }
    }
}
