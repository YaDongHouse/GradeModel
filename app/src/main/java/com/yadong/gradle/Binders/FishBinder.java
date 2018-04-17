package com.yadong.gradle.Binders;

import android.os.RemoteException;
import android.util.Log;

import com.yadong.gradle.IFish;

/**
 * @packInfo:com.yadong.gradle.Binders
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/15
 * Time: 13:31
 */

public class FishBinder extends IFish.Stub{
    private static final String TAG = "FishBinder";

    @Override
    public void swim() throws RemoteException {
        Log.d(TAG, "I'm fish, I can swim");
    }
}
