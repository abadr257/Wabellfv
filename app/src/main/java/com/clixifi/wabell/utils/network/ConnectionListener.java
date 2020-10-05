package com.clixifi.wabell.utils.network;

public interface ConnectionListener<T> {
    void onSuccess(ConnectionResponse<T> connectionResponse);

    void onFail(Throwable throwable);
}
