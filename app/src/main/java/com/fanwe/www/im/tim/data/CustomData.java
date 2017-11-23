package com.fanwe.www.im.tim.data;

import com.fanwe.lib.im.FIMData;
import com.google.gson.Gson;
import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;

/**
 * Created by Administrator on 2017/11/23.
 */
public abstract class CustomData implements FIMData<TIMMessage>
{
    public static final int TEXT_IM = 1;

    @Override
    public TIMMessage parseToSDKMsg() throws Exception
    {
        TIMMessage message = new TIMMessage();
        TIMCustomElem elem = new TIMCustomElem();
        String json = new Gson().toJson(this);
        elem.setData(json.getBytes("UTF-8"));
        message.addElement(elem);
        return message;
    }
}
