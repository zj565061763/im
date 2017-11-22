package com.fanwe.lib.im;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengjun on 2017/11/22.
 */
public class FIMManager
{
    private static FIMManager sInstance;
    private FIMHandler mIMHandler;

    private List<FIMMsgCallback> mListMsgCallback = new ArrayList<>();

    private FIMManager()
    {
    }

    public static FIMManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (FIMManager.class)
            {
                if (sInstance == null)
                {
                    sInstance = new FIMManager();
                }
            }
        }
        return sInstance;
    }

    public void setIMHandler(FIMHandler handler)
    {
        mIMHandler = handler;
    }

    public FIMHandler getIMHandler()
    {
        if (mIMHandler == null)
        {
            mIMHandler = FIMHandler.DEFAULT;
        }
        return mIMHandler;
    }

    public void addMsgCallback(FIMMsgCallback callback)
    {
        if (callback == null || mListMsgCallback.contains(callback))
        {
            return;
        }
        mListMsgCallback.add(callback);
    }

    public void removeMsgCallback(FIMMsgCallback callback)
    {
        mListMsgCallback.remove(callback);
    }

    FIMMsgCallback mInternalMsgCallback = new FIMMsgCallback()
    {
        @Override
        public void onReceiveMsg(FIMMsg msg)
        {
            Iterator<FIMMsgCallback> it = mListMsgCallback.iterator();
            while (it.hasNext())
            {
                FIMMsgCallback item = it.next();
                item.onReceiveMsg(msg);
            }
        }
    };

    /**
     * 发送C2C消息
     *
     * @param id   对方id
     * @param data 要发送的数据
     * @return
     */
    public boolean sendMsgC2C(String id, FIMData data)
    {
        return getIMHandler().sendMsg(id, data, FIMConversationType.C2C);
    }

    /**
     * 发送Group消息
     *
     * @param id   group id
     * @param data 要发送的数据
     * @return
     */
    public boolean sendMsgGroup(String id, FIMData data)
    {
        return getIMHandler().sendMsg(id, data, FIMConversationType.Group);
    }

}
