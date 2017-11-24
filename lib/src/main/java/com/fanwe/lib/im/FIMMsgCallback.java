package com.fanwe.lib.im;

/**
 * IM消息接收回调
 */
public interface FIMMsgCallback
{
    /**
     * 是否忽略当前消息
     *
     * @param imMsg
     * @return true-忽略
     */
    boolean ignoreMsg(FIMMsg imMsg);

    /**
     * 消息回调方法
     *
     * @param imMsg
     */
    void onReceiveMsg(FIMMsg imMsg);
}
