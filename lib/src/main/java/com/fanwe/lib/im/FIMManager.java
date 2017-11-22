package com.fanwe.lib.im;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by zhengjun on 2017/11/22.
 */
public class FIMManager
{
    private static FIMManager sInstance;
    private FIMHandler mIMHandler;

    private Map<FIMResultCallback, String> mMapCallback = new WeakHashMap<>();
    private long mCallbackId = 0;

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
    public boolean sendMsgC2C(String id, FIMData data, FIMResultCallback<FIMMsg> callback)
    {
        return getIMHandler().sendMsg(id, data, FIMConversationType.C2C, generateCallbackId(callback));
    }

    /**
     * 发送Group消息
     *
     * @param id   group id
     * @param data 要发送的数据
     * @return
     */
    public boolean sendMsgGroup(String id, FIMData data, FIMResultCallback<FIMMsg> callback)
    {
        return getIMHandler().sendMsg(id, data, FIMConversationType.Group, generateCallbackId(callback));
    }

    public synchronized FIMResultCallback getCallback(String callbackId)
    {
        if (TextUtils.isEmpty(callbackId))
        {
            return null;
        }
        Iterator<Map.Entry<FIMResultCallback, String>> it = mMapCallback.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<FIMResultCallback, String> item = it.next();
            if (callbackId.equals(item.getValue()))
            {
                return item.getKey();
            }
        }
        return null;
    }

    private synchronized String generateCallbackId(FIMResultCallback callback)
    {
        if (callback == null)
        {
            return null;
        }
        if (mCallbackId >= Long.MAX_VALUE)
        {
            mCallbackId = 0;
        }
        mCallbackId++;
        String result = String.valueOf(mCallbackId);
        mMapCallback.put(callback, result);
        return result;
    }
}
