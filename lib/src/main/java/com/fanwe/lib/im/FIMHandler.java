package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/23.
 */
public interface FIMHandler<M>
{
    FIMHandler DEFAULT = new FIMHandler()
    {
        @Override
        public FIMMsg sendMsg(String peer, FIMMsgData data, FIMConversationType type, String callbackId)
        {
            return null;
        }
    };

    /**
     * 发送消息
     *
     * @param peer       对方id
     * @param data       消息数据
     * @param type       消息类型
     * @param callbackId 回调对象id
     * @return
     */
    FIMMsg sendMsg(String peer, FIMMsgData<M> data, FIMConversationType type, final String callbackId);
}
