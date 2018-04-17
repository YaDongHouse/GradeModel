package com.yadong.gradle.Binders;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.yadong.gradle.IBird;

/**
 * @packInfo:com.yadong.gradle.Binders
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/15
 * Time: 13:29
 */

public class BirdBinder extends IBird.Stub {

    private static final String TAG = "BirdBinder";

    @Override
    public void fly() throws RemoteException {
        Log.d(TAG, "I'm bird,i can fly.");
    }
}
