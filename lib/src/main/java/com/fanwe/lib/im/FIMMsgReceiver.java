package com.fanwe.lib.im;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by zhengjun on 2017/11/22.
 */
public abstract class FIMMsgReceiver<T> implements FIMMsg
{
    public static final String FIELD_NAME_GUESS_DATA_TYPE = "type";

    private T mSDKMsg;

    public FIMMsgReceiver(T sdkMsg)
    {
        mSDKMsg = sdkMsg;
    }

    public final T getSDKMsg()
    {
        return mSDKMsg;
    }

    protected String getFieldNameGuessDataType()
    {
        return FIELD_NAME_GUESS_DATA_TYPE;
    }

    public int guessDataTypeFromJson(String json)
    {
        if (TextUtils.isEmpty(json))
        {
            return -1;
        }
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optInt(getFieldNameGuessDataType(), -1);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析第三方的SDK消息
     */
    public final void parse()
    {
        try
        {
            onParseSDKMsg();
        } catch (Exception e)
        {
            onError(e);
        }
    }

    /**
     * 通知FIM接收新消息
     */
    protected final void notifyReceiveMsg()
    {
        FIMManager.getInstance().mInternalMsgCallback.onReceiveMsg(this);
    }

    /**
     * 解析第三方的SDK消息
     */
    protected abstract void onParseSDKMsg() throws Exception;

    protected void onError(Exception e)
    {

    }
}
