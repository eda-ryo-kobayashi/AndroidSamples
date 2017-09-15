package com.eda.androidsamples.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by kobayashiryou on 2017/09/12.
 */

public class KeepAliveService extends Service {
    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}
