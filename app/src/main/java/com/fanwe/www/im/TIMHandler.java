package com.fanwe.www.im;

import com.fanwe.lib.im.FIMConversationType;
import com.fanwe.lib.im.FIMData;
import com.fanwe.lib.im.FIMHandler;
import com.fanwe.lib.im.FIMManager;
import com.fanwe.lib.im.FIMResultCallback;
import com.google.gson.Gson;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * Created by Administrator on 2017/11/22.
 */
public class TIMHandler implements FIMHandler
{
    @Override
    public boolean sendMsg(String id, FIMData data, FIMConversationType type, final String callbackId)
    {
        TIMConversation conversation = null;
        switch (type)
        {
            case C2C:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, id);
                break;
            case Group:
                conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, id);
                break;
            default:
                break;
        }

        try
        {
            String json = new Gson().toJson(data);

            TIMMessage message = new TIMMessage();
            TIMCustomElem elem = new TIMCustomElem();
            elem.setData(json.getBytes("UTF-8"));
            message.addElement(elem);
            conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>()
            {
                @Override
                public void onError(int code, String msg)
                {
                    FIMResultCallback callback = FIMManager.getInstance().getResultCallback(callbackId);
                    if (callback != null)
                    {
                        callback.onError(code, msg);
                    }
                }

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    FIMResultCallback callback = FIMManager.getInstance().getResultCallback(callbackId);
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
