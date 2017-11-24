package com.fanwe.lib.im;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * IM管理基类
 */
public class FIMManager
{
    private static FIMManager sInstance;

    private FIMHandler mIMHandler;

    private Map<String, FIMResultCallbackInfo> mMapResultCallback = new HashMap<>();
    private Map<Integer, Class> mMapDataClass = new HashMap<>();
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

    public final void setIMHandler(FIMHandler handler)
    {
        mIMHandler = handler;
    }

    public final FIMHandler getIMHandler()
    {
        if (mIMHandler == null)
        {
            mIMHandler = FIMHandler.DEFAULT;
        }
        return mIMHandler;
    }

    /**
     * 添加消息回调
     *
     * @param callback
     */
    public final void addMsgCallback(FIMMsgCallback callback)
    {
        if (callback == null || mListMsgCallback.contains(callback))
        {
            return;
        }
        mListMsgCallback.add(callback);
    }

    /**
     * 移除消息回调
     *
     * @param callback
     */
    public final void removeMsgCallback(FIMMsgCallback callback)
    {
        mListMsgCallback.remove(callback);
    }

    /**
     * 添加数据Class
     *
     * @param dataType 数据类型
     * @param clazz    对应的Class
     * @param <D>
     */
    public final <D extends FIMMsgData> void addDataClass(int dataType, Class<D> clazz)
    {
        if (clazz == null)
        {
            return;
        }
        mMapDataClass.put(dataType, clazz);
    }

    /**
     * 返回数据对应的Class
     *
     * @param dataType 数据类型
     * @param <D>
     * @return
     */
    public final <D extends FIMMsgData> Class<D> getDataClass(int dataType)
    {
        return mMapDataClass.get(dataType);
    }

    FIMMsgCallback mInternalMsgCallback = new FIMMsgCallback()
    {

        @Override
        public boolean ignoreMsg(FIMMsg imMsg)
        {
            return false;
        }

        @Override
        public void onReceiveMsg(FIMMsg imMsg)
        {
            Iterator<FIMMsgCallback> it = mListMsgCallback.iterator();
            while (it.hasNext())
            {
                FIMMsgCallback item = it.next();

                if (item.ignoreMsg(imMsg))
                {
                    // 忽略当前消息
                } else
                {
                    item.onReceiveMsg(imMsg);
                }
            }
        }
    };

    /**
     * 发送C2C消息
     *
     * @param peer 对方id
     * @param data 要发送的数据
     * @return
     */
    public final FIMMsg sendMsgC2C(String peer, FIMMsgData data, FIMResultCallback<FIMMsg> callback)
    {
        return getIMHandler().sendMsg(peer, data, FIMConversationType.C2C, generateCallbackId(callback));
    }

    /**
     * 发送Group消息
     *
     * @param peer group id
     * @param data 要发送的数据
     * @return
     */
    public final FIMMsg sendMsgGroup(String peer, FIMMsgData data, FIMResultCallback<FIMMsg> callback)
    {
        return getIMHandler().sendMsg(peer, data, FIMConversationType.Group, generateCallbackId(callback));
    }

    /**
     * 移除并返回结果回调
     *
     * @param callbackId
     * @return
     */
    public final synchronized FIMResultCallback removeResultCallback(String callbackId)
    {
        FIMResultCallbackInfo info = mMapResultCallback.remove(callbackId);
        if (info != null)
        {
            return info.callback;
        } else
        {
            return null;
        }
    }

    /**
     * 根据tag移除结果回调
     *
     * @param tag
     * @return 移除的数量
     */
    public final synchronized int removeResultCallbackByTag(String tag)
    {
        int count = 0;
        if (TextUtils.isEmpty(tag))
        {
            return count;
        }

        Iterator<Map.Entry<String, FIMResultCallbackInfo>> it = mMapResultCallback.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, FIMResultCallbackInfo> item = it.next();
            FIMResultCallbackInfo info = item.getValue();
            if (tag.equals(info.tag))
            {
                it.remove();
                count++;
            }
        }

        return count;
    }

    /**
     * 生成callback的标识
     *
     * @param callback
     * @return
     */
    private synchronized String generateCallbackId(FIMResultCallback callback)
    {
        if (callback == null)
        {
            return null;
        }

        final String callbackId = String.valueOf(UUID.randomUUID());
        FIMResultCallbackInfo info = new FIMResultCallbackInfo(callback, callback.getTag());

        mMapResultCallback.put(callbackId, info);
        return callbackId;
    }
}
