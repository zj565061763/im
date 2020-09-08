package com.sd.lib.im;

import android.text.TextUtils;
import android.util.Log;

import com.sd.lib.im.annotation.MsgData;
import com.sd.lib.im.callback.FIMMsgCallback;
import com.sd.lib.im.callback.FIMMsgSendCallback;
import com.sd.lib.im.callback.FIMResultCallback;
import com.sd.lib.im.common.SendMsgParam;
import com.sd.lib.im.conversation.FIMConversationType;
import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.msg.FIMMsgData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IM管理基类
 */
public class FIMManager
{
    private static FIMManager sInstance;

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
                    sInstance = new FIMManager();
            }
        }
        return sInstance;
    }

    private final Map<Integer, Class<? extends FIMMsgData>> mMapMsgDataClass = new HashMap<>();

    private final Map<FIMMsgCallback, String> mMsgCallbackHolder = new ConcurrentHashMap<>();
    private final Map<FIMMsgSendCallback, String> mMsgSendCallbackHolder = new ConcurrentHashMap<>();

    private final Map<String, CallbackInfo> mMapCallbackInfo = new ConcurrentHashMap<>();

    private FIMHandler mIMHandler;
    private boolean mIsDebug;

    /**
     * 设置是否调试模式
     *
     * @param debug
     */
    public void setDebug(boolean debug)
    {
        mIsDebug = debug;
    }

    private String getDebugTag()
    {
        return FIMManager.class.getSimpleName();
    }

    /**
     * 设置IM处理对象
     *
     * @param handler
     */
    public void setIMHandler(FIMHandler handler)
    {
        mIMHandler = handler;
    }

    /**
     * 返回IM处理类
     *
     * @return
     */
    private FIMHandler getIMHandler()
    {
        if (mIMHandler == null)
            throw new NullPointerException("you must set a FIMHandler to FIMManager before this");
        return mIMHandler;
    }

    /**
     * 注册消息数据class
     *
     * @param clazz 消息数据对应的class
     */
    public void registerMsgDataClass(Class<? extends FIMMsgData> clazz)
    {
        final MsgData msgData = clazz.getAnnotation(MsgData.class);
        if (msgData == null)
            throw new IllegalArgumentException("(MsgData) annotation was not found in clazz: " + clazz.getName());

        final int type = msgData.type();
        mMapMsgDataClass.put(type, clazz);
    }

    /**
     * 返回type对应的消息数据类型
     *
     * @param type
     * @return
     */
    public Class<? extends FIMMsgData> getMsgDataClass(int type)
    {
        return mMapMsgDataClass.get(type);
    }

    /**
     * 添加消息回调
     *
     * @param callback
     */
    public void addMsgCallback(FIMMsgCallback callback)
    {
        if (callback == null)
            return;

        mMsgCallbackHolder.put(callback, "");

        if (mIsDebug)
            Log.i(getDebugTag(), "FIMMsgCallback add size " + mMsgCallbackHolder.size() + " " + callback + " " + Thread.currentThread().getName());
    }

    /**
     * 移除消息回调
     *
     * @param callback
     */
    public void removeMsgCallback(FIMMsgCallback callback)
    {
        if (callback == null)
            return;

        if (mMsgCallbackHolder.remove(callback) != null)
        {
            if (mIsDebug)
                Log.e(getDebugTag(), "FIMMsgCallback remove size " + mMsgCallbackHolder.size() + " " + callback + " " + Thread.currentThread().getName());
        }
    }

    void notifyReceiveMsg(FIMMsg fimMsg)
    {
        for (FIMMsgCallback item : mMsgCallbackHolder.keySet())
        {
            if (item.ignoreMsg(fimMsg))
            {
                // 忽略当前消息
            } else
            {
                item.onReceiveMsg(fimMsg);
            }
        }
    }

    /**
     * 添加消息发送回调
     *
     * @param callback
     */
    public void addMsgSendCallback(FIMMsgSendCallback callback)
    {
        if (callback == null)
            return;

        mMsgSendCallbackHolder.put(callback, "");

        if (mIsDebug)
            Log.i(getDebugTag(), "FIMMsgSendCallback add size " + mMsgSendCallbackHolder.size() + " " + callback + " " + Thread.currentThread().getName());
    }

    /**
     * 移除消息发送回调
     *
     * @param callback
     */
    public void removeMsgSendCallback(FIMMsgSendCallback callback)
    {
        if (mMsgSendCallbackHolder.remove(callback) != null)
        {
            if (mIsDebug)
                Log.e(getDebugTag(), "FIMMsgSendCallback remove size " + mMsgSendCallbackHolder.size() + " " + callback + " " + Thread.currentThread().getName());
        }
    }

    Collection<FIMMsgSendCallback> getMsgSendCallback()
    {
        return mMsgSendCallbackHolder.keySet();
    }

    /**
     * 返回新创建的第三方IM消息接收对象
     *
     * @return
     */
    public FIMMsgParser newMsgParser()
    {
        return getIMHandler().newMsgParser();
    }

    /**
     * 发送C2C消息
     *
     * @param peer 对方id
     * @param data 要发送的数据
     * @return
     */
    public FIMMsg sendMsgC2C(String peer, FIMMsgData data, FIMResultCallback<FIMMsg> callback)
    {
        final SendMsgParam param = new SendMsgParam.Builder()
                .setPeer(peer)
                .setConversationType(FIMConversationType.C2C)
                .build();

        return sendMsg(param, data, callback);
    }

    /**
     * 发送Group消息
     *
     * @param peer group id
     * @param data 要发送的数据
     * @return
     */
    public FIMMsg sendMsgGroup(String peer, FIMMsgData data, FIMResultCallback<FIMMsg> callback)
    {
        final SendMsgParam param = new SendMsgParam.Builder()
                .setPeer(peer)
                .setConversationType(FIMConversationType.Group)
                .build();

        return sendMsg(param, data, callback);
    }

    /**
     * 发送消息
     *
     * @param param    {@link SendMsgParam}
     * @param data     要发送的数据
     * @param callback
     * @return
     */
    public FIMMsg sendMsg(SendMsgParam param, FIMMsgData data, FIMResultCallback<FIMMsg> callback)
    {
        return getIMHandler().sendMsg(param, data, generateCallbackId(callback));
    }

    /**
     * 加入群组
     *
     * @param groupId  群组id
     * @param callback
     */
    public void joinGroup(String groupId, FIMResultCallback callback)
    {
        getIMHandler().joinGroup(groupId, generateCallbackId(callback));
    }

    /**
     * 退出群组
     *
     * @param groupId  群组id
     * @param callback
     */
    public void quitGroup(String groupId, FIMResultCallback callback)
    {
        getIMHandler().quitGroup(groupId, generateCallbackId(callback));
    }

    /**
     * 移除并返回结果回调
     *
     * @param callbackId 回调对应的id
     * @return
     */
    FIMResultCallback removeCallbackById(String callbackId)
    {
        if (TextUtils.isEmpty(callbackId))
            return null;

        final CallbackInfo info = mMapCallbackInfo.remove(callbackId);
        return info == null ? null : info.callback;
    }

    /**
     * 根据tag移除结果回调
     *
     * @param tag
     * @return 移除的数量
     */
    public int removeCallbackByTag(String tag)
    {
        if (TextUtils.isEmpty(tag) || mMapCallbackInfo.isEmpty())
            return 0;

        int count = 0;
        final Iterator<Map.Entry<String, CallbackInfo>> it = mMapCallbackInfo.entrySet().iterator();
        while (it.hasNext())
        {
            final CallbackInfo info = it.next().getValue();
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
    private String generateCallbackId(FIMResultCallback callback)
    {
        if (callback == null)
            return null;

        final String callbackId = String.valueOf(UUID.randomUUID());
        final CallbackInfo info = new CallbackInfo(callback, callback.getTag());

        mMapCallbackInfo.put(callbackId, info);
        return callbackId;
    }

    /**
     * 保存callback信息
     */
    private static final class CallbackInfo
    {
        public final FIMResultCallback callback;
        public final String tag;

        public CallbackInfo(FIMResultCallback callback, String tag)
        {
            this.callback = callback;
            this.tag = tag;
        }
    }
}
