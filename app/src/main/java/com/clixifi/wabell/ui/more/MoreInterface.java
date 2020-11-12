package com.clixifi.wabell.ui.more;

import com.clixifi.wabell.data.WhatsAppResponse;

public interface MoreInterface {
    void onFail(boolean fail);
    void onConnection(boolean fail);
    void onSuccess(WhatsAppResponse number);
}
