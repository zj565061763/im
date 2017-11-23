package com.fanwe.www.im.tim;

import com.fanwe.lib.im.FIMConversationType;
import com.fanwe.lib.im.FIMManager;
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
public class AppIMManager extends FIMManager<TIMMessage>
{
    private static final AppIMManager INSTANCE = new AppIMManager();

    private AppIMManager()
    {
    }

    public static AppIMManager getInstance()
    {
        return INSTANCE;
    }

    @Override
    protected TIMMessage onSendMsg(String peer, FIMMsgData<TIMMessage> data, FIMConversationType type, final String callbackId)
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
                    FIMResultCallback callback = getCallback(callbackId);
                    if (callback != null)
                    {
                        callback.onError(code, msg);
                    }
                }

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    FIMResultCallback callback = getCallback(callbackId);
                    if (callback != null)
                    {
                        TIMMsgReceiver receiver = new TIMMsgReceiver(timMessage);
                        receiver.parse();
                        callback.onSuccess(receiver);
                    }
                }
            });
            return message;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
