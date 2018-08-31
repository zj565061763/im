package com.sd.www.im;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sd.lib.im.FIMManager;
import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.callback.FIMMsgCallback;

public class MainActivity extends AppCompatActivity implements FIMMsgCallback
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FIMManager.getInstance().setDebug(true);
        FIMManager.getInstance().addMsgCallback(this);
    }

    @Override
    public boolean ignoreMsg(FIMMsg fimMsg)
    {
        return false;
    }

    @Override
    public void onReceiveMsg(FIMMsg fimMsg)
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FIMManager.getInstance().removeMsgCallback(this);
    }
}
