package com.sd.lib.im.callback;

import com.sd.lib.im.common.SendMsgParam;
import com.sd.lib.im.msg.FIMMsgData;

public interface FIMSendInterceptor
{
    /**
     * 拦截消息
     *
     * @param param
     * @param data
     * @return true-拦截
     */
    boolean interceptMsg(SendMsgParam param, FIMMsgData data);
}
