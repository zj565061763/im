package com.fanwe.lib.im;

import android.os.Handler;
import android.os.Looper;
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
    private FIMData mData;

    public FIMMsgReceiver(T sdkMsg)
    {
        mSDKMsg = sdkMsg;
    }

    public final T getSDKMsg()
    {
        return mSDKMsg;
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
     *
     * @return
     */
    public final FIMData<T> parse()
    {
        try
        {
            mData = onParseSDKMsg();
            if (mData != null)
            {
                onFillData(mData);
            }
            return mData;
        } catch (Exception e)
        {
            onError(e);
        }
        return null;
    }

    /**
     * 通知FIM接收新消息
     */
    public final void notifyReceiveMsg()
    {
        final FIMMsg msg = this;
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            FIMManager.getInstance().mInternalMsgCallback.onReceiveMsg(msg);
        } else
        {
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                @Override
                public void run()
                {
                    notifyReceiveMsg();
                }
            });
        }
    }

    /**
     * 是否有需要下载的数据，true-需要<br>
     * 如果需要下载数据，并且callback不为null，则开始下载数据
     *
     * @param callback
     * @return
     */
    public abstract boolean isNeedDownloadData(FIMResultCallback callback);

    /**
     * 将第三方的SDK消息解析为数据
     *
     * @return
     * @throws Exception
     */
    protected abstract FIMData<T> onParseSDKMsg() throws Exception;

    protected abstract void onFillData(FIMData<T> data) throws Exception;

    protected void onError(Exception e)
    {

    }
}
