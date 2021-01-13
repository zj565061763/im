package com.sd.lib.im.callback;

import com.sd.lib.im.msg.FIMMsg;

/**
 * IM消息发送回调
 */
public interface FIMMsgSendCallback
{
    /**
     * 调用发送
     *
     * @param msg
     */
    void onSend(FIMMsg msg);

    /**
     * 发送成功
     *
     * @param msg
     */
    void onSendSuccess(FIMMsg msg);

    /**
     * 发送失败
     *
     * @param msg
     * @param code
     * @param desc
     */
    void onSendError(FIMMsg msg, int code, String desc);
}
