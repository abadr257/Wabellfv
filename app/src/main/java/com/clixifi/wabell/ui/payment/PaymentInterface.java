package com.clixifi.wabell.ui.payment;

public interface PaymentInterface {
    void onPayment(String response);
    void onConnection(boolean isConnected);
    void onFail(boolean isConnected);
}
