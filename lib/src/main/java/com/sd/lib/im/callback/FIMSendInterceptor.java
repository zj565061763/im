package com.sd.lib.im.callback;

import com.sd.lib.im.common.SendMsgParam;
import com.sd.lib.im.msg.FIMMsgData;

public interface FIMSendInterceptor
{
    boolean interceptMsg(SendMsgParam param, FIMMsgData data);
}
