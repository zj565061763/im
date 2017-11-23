package com.fanwe.lib.im;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * IM管理基类
 *
 * @param <M> 第三方IM消息类型
 */
public abstract class FIMManager<M>
{
    private Map<FIMResultCallback, String> mMapCallback = new WeakHashMap<>();
    private long mCallbackId = 0;

    private Map<Integer, Class> mMapDataClass = new HashMap<>();

    private List<FIMMsgCallback> mListMsgCallback = new ArrayList<>();

    protected FIMManager()
    {
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
     * @param peer 对方id
     * @param data 要发送的数据
     */
    public final M sendMsgC2C(String peer, FIMMsgData<M> data, FIMResultCallback<FIMMsg> callback)
    {
        return onSendMsg(peer, data, FIMConversationType.C2C, generateCallbackId(callback));
    }

    /**
     * 发送Group消息
     *
     * @param peer group id
     * @param data 要发送的数据
     */
    public final M sendMsgGroup(String peer, FIMMsgData<M> data, FIMResultCallback<FIMMsg> callback)
    {
        return onSendMsg(peer, data, FIMConversationType.Group, generateCallbackId(callback));
    }

    public final synchronized FIMResultCallback getCallback(String callbackId)
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

    /**
     * 发送消息
     *
     * @param peer       对方id
     * @param data       消息数据
     * @param type       消息类型
     * @param callbackId 回调对象id
     * @return
     */
    abstract protected M onSendMsg(String peer, FIMMsgData<M> data, FIMConversationType type, final String callbackId);
}
