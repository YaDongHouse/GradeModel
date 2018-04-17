package com.yadong.gradle.Binders;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.yadong.gradle.BuildConfig;
import com.yadong.gradle.IAnimal;

import java.util.concurrent.CountDownLatch;

/**
 * @packInfo:com.yadong.gradle.Binders
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/1/15
 * Time: 13:36
 */

public class BinderPool {

    private static final String TAG = "BinderPool";


    public static final int NO_ANIMAL = 0;
    public static final int ANIMAL_CODE_BIRD = 1;
    public static final int ANIMAL_CODE_FISH = 2;
    public static final int ANIMAL_CODE_MONKEY = 3;

    private Context mContext;

    private static BinderPool sInstance;
    private CountDownLatch mCountDownLatch;
    private IAnimal mAnimalPool;

    public BinderPool(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context){
        if (sInstance == null){
            synchronized (BinderPool.class){
                if (sInstance == null){
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService(){
        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent(mContext,MyService.class);
        mContext.bindService(intent,mConnection,Context.BIND_AUTO_CREATE);

        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAnimalPool = IAnimal.Stub.asInterface(service);
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected:");
        }
    };

    public IBinder queryAnimal(int animalCode){
        IBinder binder = null;
        try {
            if (mAnimalPool != null){
                binder = mAnimalPool.queryAnimal(animalCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    public static class AnimalBinder extends IAnimal.Stub{


        @Override
        public IBinder queryAnimal(int animalCode) throws RemoteException {
            IBinder binder = null;
            switch (animalCode) {
                case ANIMAL_CODE_BIRD:
                    binder = new BirdBinder();
                    break;
                case ANIMAL_CODE_FISH:
                    binder = new FishBinder();
                    break;
                case ANIMAL_CODE_MONKEY:
                    binder = new MonkeyBinder();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
    
}
