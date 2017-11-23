package com.fanwe.www.im.tim;

import com.fanwe.lib.im.FIMConversationType;
import com.fanwe.lib.im.FIMHandler;
import com.fanwe.lib.im.FIMManager;
import com.fanwe.lib.im.FIMMsg;
import com.fanwe.lib.im.FIMMsgData;
import com.fanwe.lib.im.FIMResultCallback;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * Created by Administrator on 2017/11/23.
 */
public class AppIMHandler implements FIMHandler<TIMMessage>
{
    @Override
    public FIMMsg sendMsg(String peer, FIMMsgData<TIMMessage> data, FIMConversationType type, final String callbackId)
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
                        AppIMMsgReceiver receiver = new AppIMMsgReceiver(timMessage);
                        callback.onSuccess(receiver);
                    }
                }
            });

            AppIMMsgReceiver receiver = new AppIMMsgReceiver(message);

            return receiver;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
