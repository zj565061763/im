package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public interface FIMHandler<T>
{
    FIMHandler DEFAULT = new FIMHandler()
    {
        @Override
        public boolean sendMsg(String peer, FIMData data, FIMConversationType type, String callbackId)
        {
            return false;
        }
    };

    /**
     * 发送消息
     *
     * @param peer       对方id
     * @param data       要发送的数据
     * @param type       会话类型
     * @param callbackId 回调id
     * @return
     */
    boolean sendMsg(String peer, FIMData<T> data, FIMConversationType type, String callbackId);
}
