package com.fanwe.www.im.tim;

import com.fanwe.lib.im.FIMData;
import com.fanwe.lib.im.FIMManager;
import com.fanwe.lib.im.FIMMsgReceiver;
import com.google.gson.Gson;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMMessage;

/**
 * Created by Administrator on 2017/11/23.
 */

public class TIMMsgReceiver extends FIMMsgReceiver<TIMMessage>
{
    public static final String DEFAULT_CHARSET = "UTF-8";

    private TIMCustomElem timCustomElem;
    private TIMGroupSystemElem timGroupSystemElem;

    private FIMData mData;

    public TIMMsgReceiver(TIMMessage sdkMsg)
    {
        super(sdkMsg);
    }

    @Override
    public int getDataType()
    {
        if (mData != null)
        {
            return mData.getType();
        }
        return 0;
    }

    @Override
    public FIMData getData()
    {
        return mData;
    }

    @Override
    public boolean isSelf()
    {
        return getSDKMsg().isSelf();
    }

    @Override
    public String getConversationtPeer()
    {
        String peer = null;
        if (timGroupSystemElem != null)
        {
            peer = timGroupSystemElem.getGroupId();
        } else
        {
            peer = getSDKMsg().getConversation().getPeer();
        }
        return peer;
    }

    @Override
    public long getTimestamp()
    {
        return getSDKMsg().timestamp();
    }

    @Override
    protected void onParseSDKMsg() throws Exception
    {
        long count = getSDKMsg().getElementCount();
        TIMElem elem = null;
        for (int i = 0; i < count; i++)
        {
            elem = getSDKMsg().getElement(i);
            if (elem == null)
            {
                continue;
            }
            TIMElemType elemType = elem.getType();
            switch (elemType)
            {
                case Custom:
                    timCustomElem = (TIMCustomElem) elem;
                    break;
                case GroupSystem:
                    timGroupSystemElem = (TIMGroupSystemElem) elem;
                    break;
                default:
                    break;
            }
        }

        byte[] data = null;
        if (timGroupSystemElem != null)
        {
            data = timGroupSystemElem.getUserData();
        }
        if (data == null && timCustomElem != null)
        {
            data = timCustomElem.getData();
        }
        if (data != null)
        {
            String json = new String(data, DEFAULT_CHARSET);
            int dataType = guessDataTypeFromJson(json);
            Class clazz = FIMManager.getInstance().getDataClass(dataType);
            if (clazz != null)
            {
                mData = (FIMData) new Gson().fromJson(json, clazz);
            }
        }
    }

}
