package com.fanwe.lib.im;

/**
 * 消息接收回调
 */
public interface FIMMsgCallback
{
    /**
     * 是否忽略当前消息
     *
     * @param msg
     * @return true-忽略
     */
    boolean ignoreMsg(FIMMsg msg);

    /**
     * 消息回调方法
     *
     * @param msg
     */
    void onReceiveMsg(FIMMsg msg);
}
