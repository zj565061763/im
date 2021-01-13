package com.sd.www.im;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.im.FIMManager;
import com.sd.lib.im.callback.FIMMsgCallback;
import com.sd.lib.im.msg.FIMMsg;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FIMManager.getInstance().addMsgCallback(mMsgCallback);
    }

    /**
     * IM消息接收回调
     */
    private final FIMMsgCallback mMsgCallback = new FIMMsgCallback()
    {
        @Override
        public boolean ignoreMsg(FIMMsg fimMsg)
        {
            return false;
        }

        @Override
        public void onReceiveMsg(FIMMsg fimMsg)
        {

        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FIMManager.getInstance().removeMsgCallback(mMsgCallback);
    }
}
