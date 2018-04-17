package com.yadong.gradle;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yadong.gradle.Binders.BinderPool;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BinderPool mBinderPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
    }

    private static final String TAG = "MainActivity";
    @Override
    public void onClick(View v) {
//        boolean b = InstallUtils.hasRootPerssion();
//        Log.d(TAG, "onClick:执行了 "+b);
        new Thread(new Runnable() {
            @Override
            public void run() {
               mBinderPool = BinderPool.getInstance(MainActivity.this);
                IBinder birdBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_BIRD);
                IBinder fishBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_FISH);
                IBinder monkeyBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_MONKEY);

                IBird  bird = IBird.Stub.asInterface(birdBinder);
                IFish fish = IFish.Stub.asInterface(fishBinder);
                IMonkey monkey = IMonkey.Stub.asInterface(monkeyBinder);
                try {
                    bird.fly();
                    fish.swim();
                    monkey.climbTree();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
