package com.clixifi.wabell.ui.messagesTap;

import com.clixifi.wabell.data.CallsArray;

public interface MessagesInterFace {

    void onMessages(CallsArray array );
    void onFail(boolean fail);
    void onConnection(boolean isConnected);

}
