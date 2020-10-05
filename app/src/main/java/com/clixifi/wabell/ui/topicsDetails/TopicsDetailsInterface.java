package com.clixifi.wabell.ui.topicsDetails;

import com.clixifi.wabell.data.Response.topicChild.ChildResponse;

public interface TopicsDetailsInterface {
    void onSuccess(ChildResponse childResponse);
    void onFail(boolean fail);
    void onConnection(boolean isConnected);
}
