package com.clixifi.wabell.ui.homeTutor;

import com.clixifi.wabell.data.Response.RequestLogs.RequestLogsArray;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.UserTutorCounters;

public interface HomeTutorInterface {
    void onSuccess(UserTutorCounters userTutorCounters) ;
    void onFail(boolean fail );
    void onNoConnection(boolean isConnected);
    void onUpdate(ResultBoolean resultBoolean) ;
    void onRequestLogs(RequestLogsArray array) ;
    void onRequestFail(boolean fail) ;
}
