package com.clixifi.wabell.ui.topicsSummary;

import com.clixifi.wabell.data.Response.topicChild.ChildResponse;

public interface SummaryInterface {
    void onSummarySuccess(ChildResponse response);
    void onCancel(boolean response);
    void onFail(boolean fail);
    void onConnection(boolean isConnected);
    void onNothing(boolean isNothing);
}
