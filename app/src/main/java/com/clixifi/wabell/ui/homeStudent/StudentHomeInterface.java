package com.clixifi.wabell.ui.homeStudent;

import com.clixifi.wabell.data.Response.TutorList.TutorListArray;
import com.clixifi.wabell.data.Response.featuredTutors.FeaturedArray;

public interface StudentHomeInterface {
    void onFeaturedTutors(FeaturedArray featuredArray);
    void onFailFeatured(boolean failFeatured) ;
    void onConnection(boolean isConnected);

    void onLogs(TutorListArray array);
    void onFailLogs(boolean failLogs) ;
}
