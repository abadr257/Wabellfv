package com.clixifi.wabell.ui.favMasters;

import com.clixifi.wabell.data.Response.favMasters.FavMastersStudent;

public interface FavMastersInterface {
    void onSuccess(FavMastersStudent fav);
    void onFail(boolean fail);
    void onConnection(boolean isConnected);

}
