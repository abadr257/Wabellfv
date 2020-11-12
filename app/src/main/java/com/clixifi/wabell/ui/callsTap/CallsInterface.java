package com.clixifi.wabell.ui.callsTap;


import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;

public interface CallsInterface {
    void onSuccess(RequestLogsArray array );

    void onFail(boolean fail);
    void onConnection(boolean isConnected);
}
