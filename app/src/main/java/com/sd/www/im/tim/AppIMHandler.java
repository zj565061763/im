package com.sd.www.im.tim;

import com.sd.lib.im.conversation.FIMConversationType;
import com.sd.lib.im.FIMHandler;
import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.msg.FIMMsgData;
import com.sd.lib.im.FIMMsgWrapper;
import com.sd.lib.im.callback.FIMResultCallback;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * Created by Administrator on 2017/11/23.
 */
public class AppIMHandler extends FIMHandler<TIMMessage>
{

    @Override
    public FIMMsgWrapper<TIMMessage> newMsgWrapper()
    {
        return new AppIMMsgWrapper();
    }

    @Override
    public FIMMsg sendMsg(String peer, FIMMsgData<TIMMessage> msgData, FIMConversationType conversationType, final String callbackId)
    {
        TIMConversation conversation = null;
        switch (conversationType)
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

        final TIMMessage msg = msgData.parseToSDKMsg();
        final FIMMsgWrapper<TIMMessage> receiver = newMsgWrapper();
        receiver.parse(msg);
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>()
        {
            @Override
            public void onError(int code, String msg)
            {
                FIMResultCallback callback = removeCallbackById(callbackId);
                if (callback != null)
                {
                    callback.onError(code, msg);
                }
            }

            @Override
            public void onSuccess(TIMMessage timMessage)
            {
                FIMResultCallback callback = removeCallbackById(callbackId);
                if (callback != null)
                {
                    receiver.parse(timMessage);
                    callback.onSuccess(receiver);
                }
            }
        });

        return receiver;
    }

    @Override
    public void joinGroup(String groupId, String callbackId)
    {

    }

    @Override
    public void quitGroup(String groupId, String callbackId)
    {

    }
}
