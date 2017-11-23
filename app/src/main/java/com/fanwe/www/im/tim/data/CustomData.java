package com.fanwe.www.im.tim.data;

import com.fanwe.lib.im.FIMMsg;
import com.fanwe.lib.im.FIMMsgData;
import com.google.gson.Gson;
import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;

/**
 * Created by Administrator on 2017/11/23.
 */
public abstract class CustomData implements FIMMsgData<TIMMessage>
{
    public static final int TEXT_IM = 1;

    @Override
    public TIMMessage parseToSDKMsg()
    {
        try
        {
            TIMMessage message = new TIMMessage();
            TIMCustomElem elem = new TIMCustomElem();
            String json = new Gson().toJson(this);
            elem.setData(json.getBytes("UTF-8"));
            message.addElement(elem);
            return message;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FIMMsg parseToMsg()
    {

        return null;
    }
}
