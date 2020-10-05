package com.clixifi.wabell.ui.homeTutor;

import com.clixifi.wabell.data.Response.UserTutorCounters;

public interface HomeTutorInterface {
    void onSuccess(UserTutorCounters userTutorCounters) ;
    void onFail(boolean fail );
    void onNoConnection(boolean isConnected);
}
