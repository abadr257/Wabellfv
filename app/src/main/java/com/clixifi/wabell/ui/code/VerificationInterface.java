package com.clixifi.wabell.ui.code;

public interface VerificationInterface {
    void onVerifySuccess(boolean success , String id);
    void onNoConnection(boolean isConnected);
    void onFail(boolean fail);
    void onResendSuccess(boolean isSuccess);
    void onEmptyFields(boolean isEmpty);
}
