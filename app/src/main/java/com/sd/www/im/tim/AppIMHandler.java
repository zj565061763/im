package com.sd.www.im.tim;

import com.sd.lib.im.FIMHandler;
import com.sd.lib.im.FIMMsgParser;
import com.sd.lib.im.callback.FIMResultCallback;
import com.sd.lib.im.common.SendMsgParam;
import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.msg.FIMMsgData;
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
    public FIMMsgParser<TIMMessage> newMsgParser()
    {
        return new AppIMMsgParser();
    }

    @Override
    public FIMMsg sendMsg(SendMsgParam param, FIMMsgData<TIMMessage> msgData, final String callbackId)
    {
        TIMConversation conversation = null;
        switch (param.conversationType)
        {
            case C2C:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, param.peer);
                break;
            case Group:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, param.peer);
                break;
            default:
                break;
        }

        final TIMMessage msg = msgData.parseToSDKMsg();
        final FIMMsgParser<TIMMessage> receiver = newMsgParser();
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
