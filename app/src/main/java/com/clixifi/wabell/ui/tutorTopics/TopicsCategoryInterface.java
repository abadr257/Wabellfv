package com.clixifi.wabell.ui.tutorTopics;

import com.clixifi.wabell.data.Response.requestTopic.RequestTopic;
import com.clixifi.wabell.data.Response.topic.Topics;

public interface TopicsCategoryInterface {
    void onSuccess(Topics topics);
    void onFail(boolean fail);
    void onNoConnection(boolean IsConnected);
    void onReq(RequestTopic requestTopic);
}
