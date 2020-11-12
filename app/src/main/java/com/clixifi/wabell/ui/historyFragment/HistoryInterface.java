package com.clixifi.wabell.ui.historyFragment;

import com.clixifi.wabell.data.HistoryArray;

public interface HistoryInterface {
    void onHistory(HistoryArray array );

    void onFail(boolean fail);
    void onConnection(boolean isConnected);
}
