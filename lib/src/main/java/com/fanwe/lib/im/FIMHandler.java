package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public interface FIMHandler
{
    FIMHandler DEFAULT = new FIMHandler()
    {
        @Override
        public boolean sendMsg(String id, FIMData data, FIMConversationType type, String callbackId)
        {
            return false;
        }
    };

    boolean sendMsg(String id, FIMData data, FIMConversationType type, String callbackId);
}
