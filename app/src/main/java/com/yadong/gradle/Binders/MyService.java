package com.yadong.gradle.Binders;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @packInfo:com.yadong.gradle.Binders
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/15
 * Time: 14:06
 */

public class MyService extends Service {

    private BinderPool.AnimalBinder mBinder = new BinderPool.AnimalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
