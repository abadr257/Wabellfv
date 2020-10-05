package com.clixifi.wabell.ui.tutorExpEdu;

public interface ExpAndEduInterface {
    void onAddedSuccess(boolean success);
    void onAddedFail(boolean fail);
    void onNoConnection(boolean isConnected);
    void onEmptyFields(boolean empty);
}
