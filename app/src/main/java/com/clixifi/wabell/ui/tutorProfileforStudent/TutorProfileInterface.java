package com.clixifi.wabell.ui.tutorProfileforStudent;

import com.clixifi.wabell.data.Response.AddFav.AddFavorite;
import com.clixifi.wabell.data.Response.ResultBoolean;
import com.clixifi.wabell.data.Response.TutorProfileData.TutorProfileForStudent;

public interface TutorProfileInterface {
    void onSuccess(TutorProfileForStudent tutor) ;
    void onFail(boolean fail);
    void onConnection(boolean isConnected);

    void OnAddedToFavorite(AddFavorite addFavorite);
    void onDeleteFav(ResultBoolean result);
}
