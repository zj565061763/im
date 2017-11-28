package com.fanwe.lib.im;

import com.fanwe.lib.im.callback.FIMResultCallback;

/**
 * IM处理类
 *
 * @param <M> 第三方IM消息类型
 */
public abstract class FIMHandler<M>
{
    /**
     * 返回新创建的第三方IM消息接收对象
     *
     * @return
     */
    protected abstract FIMMsgReceiver<M> newMsgReceiver();

    /**
     * 移除并返回结果回调
     *
     * @param callbackId 回调对应的id
     * @return
     */
    protected final FIMResultCallback removeResultCallback(String callbackId)
    {
        return FIMManager.getInstance().removeResultCallback(callbackId);
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
    protected abstract FIMMsg sendMsg(String peer, FIMMsgData<M> data, FIMConversationType type, String callbackId);
}
