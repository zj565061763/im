package com.fanwe.lib.im;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by zhengjun on 2017/11/22.
 */
public abstract class FIMMsgReceiver<T> implements FIMMsg
{
    public static final String FIELD_NAME_GUESS_DATA_TYPE = "type";
    public static final int EMPTY_DATA_TYPE = -1;

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

    /**
     * 从json串中猜测数据类型
     *
     * @param json
     * @return
     */
    public int guessDataTypeFromJson(String json)
    {
        if (TextUtils.isEmpty(json))
        {
            return EMPTY_DATA_TYPE;
        }
        final String fieldName = getFieldNameGuessDataType();
        if (TextUtils.isEmpty(fieldName))
        {
            return EMPTY_DATA_TYPE;
        }
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optInt(getFieldNameGuessDataType(), -1);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return EMPTY_DATA_TYPE;
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
    public final void notifyReceiveMsg()
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
