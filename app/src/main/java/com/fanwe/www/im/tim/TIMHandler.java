package com.fanwe.www.im.tim;

import com.fanwe.lib.im.FIMConversationType;
import com.fanwe.lib.im.FIMData;
import com.fanwe.lib.im.FIMHandler;
import com.fanwe.lib.im.FIMManager;
import com.fanwe.lib.im.FIMResultCallback;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * Created by Administrator on 2017/11/22.
 */
public class TIMHandler implements FIMHandler<TIMMessage>
{
    @Override
    public boolean sendMsg(String peer, FIMData<TIMMessage> data, FIMConversationType type, final String callbackId)
    {
        TIMConversation conversation = null;
        switch (type)
        {
            case C2C:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, peer);
                break;
            case Group:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, peer);
                break;
            default:
                break;
        }

        try
        {

            TIMMessage message = data.parseToSDKMsg();
            conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>()
            {
                @Override
                public void onError(int code, String msg)
                {
                    FIMResultCallback callback = FIMManager.getInstance().getCallback(callbackId);
                    if (callback != null)
                    {
                        callback.onError(code, msg);
                    }
                }

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    FIMResultCallback callback = FIMManager.getInstance().getCallback(callbackId);
                    if (callback != null)
                    {

                    }
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
