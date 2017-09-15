package com.eda.androidsamples.customtabs;

import android.support.customtabs.CustomTabsClient;

/**
 * Created by kobayashiryou on 2017/09/12.
 */

public interface ServiceConnectionCallback {
    /**
     * Called when the service is connected.
     * @param client a CustomTabsClient
     */
    void onServiceConnected(CustomTabsClient client);

    /**
     * Called when the service is disconnected.
     */
    void onServiceDisconnected();
}
