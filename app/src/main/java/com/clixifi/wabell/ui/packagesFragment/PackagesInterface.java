package com.clixifi.wabell.ui.packagesFragment;

import com.clixifi.wabell.data.CurrentPackages;
import com.clixifi.wabell.data.GetResult;
import com.clixifi.wabell.data.PackagesArray;
import com.clixifi.wabell.data.Response.User.ResultForProfile;

public interface PackagesInterface {
    void onPackages(PackagesArray array);
    void onCurrentPackage(GetResult<CurrentPackages> currentPackages);

    void onFail(boolean fail);
    void onConnection(boolean isConnected);

}
