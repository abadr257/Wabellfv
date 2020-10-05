package com.clixifi.wabell.ui.tutorBiography;

public interface BioInterface {
    void onAddedSuccess(boolean success);
    void onAddedFail(boolean fail);
    void onNoConnection(boolean isConnected);
    void onEmptyFields(boolean empty);
}
