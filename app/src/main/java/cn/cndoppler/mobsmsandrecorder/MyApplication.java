package cn.cndoppler.mobsmsandrecorder;

import android.app.Application;

import com.mob.MobSDK;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this,"223fb42c543e2","e5919b1df2329f8151f41d8bd5dc6f79");
    }
}
