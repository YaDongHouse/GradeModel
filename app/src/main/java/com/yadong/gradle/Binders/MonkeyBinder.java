package com.yadong.gradle.Binders;

import android.os.RemoteException;
import android.util.Log;

import com.yadong.gradle.IMonkey;

/**
 * @packInfo:com.yadong.gradle.Binders
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/15
 * Time: 13:33
 */

public class MonkeyBinder extends IMonkey.Stub {
    private static final String TAG = "MonkeyBinder";

    @Override
    public void climbTree() throws RemoteException {
        Log.d(TAG, "I'm mokey, I can climb the tree.");
    }
}
