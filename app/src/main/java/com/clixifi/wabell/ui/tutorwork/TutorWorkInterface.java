package com.clixifi.wabell.ui.tutorwork;

public interface TutorWorkInterface {
    void onAddedSuccess(boolean success);
    void onAddedFail(boolean fail);
    void onNoConnection(boolean isConnected);
    void onEmptyFields(boolean empty);
}
