package com.sd.lib.im.callback;

import com.sd.lib.im.msg.FIMMsg;

/**
 * IM消息接收回调
 */
public interface FIMMsgCallback
{
    /**
     * 是否忽略当前消息
     *
     * @param fimMsg
     * @return true-忽略
     */
    boolean ignoreMsg(FIMMsg fimMsg);

    /**
     * 消息回调方法
     *
     * @param fimMsg
     */
    void onReceiveMsg(FIMMsg fimMsg);
}
