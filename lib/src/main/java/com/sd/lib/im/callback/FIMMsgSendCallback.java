package com.sd.lib.im.callback;

import com.sd.lib.im.msg.FIMMsg;

public interface FIMMsgSendCallback
{
    void onSend(FIMMsg msg);

    void onSendSuccess(FIMMsg msg);

    void onSendError(int code, String desc);
}
