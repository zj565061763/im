package com.sd.www.im;

import android.app.Application;

import com.sd.lib.im.FIMManager;

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        FIMManager.getInstance().setDebug(true);
    }
}
