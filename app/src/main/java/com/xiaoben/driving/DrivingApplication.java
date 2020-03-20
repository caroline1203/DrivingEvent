package com.xiaoben.driving;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class DrivingApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
