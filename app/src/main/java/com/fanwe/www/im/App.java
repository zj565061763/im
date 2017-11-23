package com.fanwe.www.im;

import android.app.Application;

import com.fanwe.lib.im.FIMManager;
import com.fanwe.www.im.tim.TIMHandler;

/**
 * Created by Administrator on 2017/11/22.
 */

public class App extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        FIMManager.getInstance().setIMHandler(new TIMHandler());
    }
}
