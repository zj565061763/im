package com.fanwe.lib.im;

/**
 * IM处理类
 *
 * @param <M> 第三方IM消息类型
 */
public interface FIMHandler<M>
{
    /**
     * 返回新创建的第三方IM消息接收对象
     *
     * @return
     */
    FIMMsgReceiver<M> newMsgReceiver();

    /**
     * 发送消息
     *
     * @param peer       对方id
     * @param data       消息数据
     * @param type       消息类型
     * @param callbackId 回调对象id
     * @return
     */
    FIMMsg sendMsg(String peer, FIMMsgData<M> data, FIMConversationType type, String callbackId);
}
