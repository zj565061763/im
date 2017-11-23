package com.fanwe.www.im.tim;

import com.fanwe.lib.im.FIMData;
import com.fanwe.lib.im.FIMManager;
import com.fanwe.lib.im.FIMMsgReceiver;
import com.fanwe.lib.im.FIMResultCallback;
import com.google.gson.Gson;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;

/**
 * Created by Administrator on 2017/11/23.
 */

public class TIMMsgReceiver extends FIMMsgReceiver<TIMMessage>
{
    public static final String DEFAULT_CHARSET = "UTF-8";

    private TIMCustomElem timCustomElem;
    private TIMGroupSystemElem timGroupSystemElem;
    private TIMImageElem timImageElem;
    private TIMSoundElem timSoundElem;
    private TIMTextElem timTextElem;

    public TIMMsgReceiver(TIMMessage sdkMsg)
    {
        super(sdkMsg);
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
    protected FIMData<TIMMessage> onParseSDKMsg() throws Exception
    {
        FIMData result = null;

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
                case Image:
                    this.timImageElem = (TIMImageElem) elem;
                    break;
                case Sound:
                    this.timSoundElem = (TIMSoundElem) elem;
                    break;
                case Text:
                    this.timTextElem = (TIMTextElem) elem;
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
                result = (FIMData) new Gson().fromJson(json, clazz);
            }
        }
        return result;
    }

    @Override
    protected void onFillData(FIMData<TIMMessage> data) throws Exception
    {

    }

    @Override
    public boolean isNeedDownloadData(FIMResultCallback callback)
    {
        return false;
    }
}
