package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public interface FIMHandler<T>
{
    FIMHandler DEFAULT = new FIMHandler()
    {
        @Override
        public void sendMsg(String peer, FIMMsgData data, FIMConversationType type, String callbackId)
        {
        }
    };

    /**
     * 发送消息
     *
     * @param peer       对方id
     * @param data       要发送的数据
     * @param type       会话类型
     * @param callbackId 回调id
     */
    void sendMsg(String peer, FIMMsgData<T> data, FIMConversationType type, String callbackId);
}
