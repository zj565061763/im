package com.fanwe.lib.im;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONObject;

/**
 * 第三方IM消息接收处理类
 *
 * @param <M>
 */
public abstract class FIMMsgReceiver<M> implements FIMMsg
{
    public static final String FIELD_NAME_GUESS_DATA_TYPE = "type";
    public static final int EMPTY_DATA_TYPE = -1;

    private M mSDKMsg;
    private FIMMsgData mData;

    public FIMMsgReceiver(M sdkMsg)
    {
        parse(sdkMsg);
    }

    public final M getSDKMsg()
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
    public FIMMsgData getData()
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
    public synchronized boolean parse(M sdkMsg)
    {
        if (sdkMsg == null)
        {
            return false;
        }
        try
        {
            mSDKMsg = sdkMsg;
            mData = onParseSDKMsg();
            if (mData != null)
            {
                onFillData(mData);
                return true;
            } else
            {
                return false;
            }
        } catch (Exception e)
        {
            onError(e);
        }
        return false;
    }

    @Override
    public final void notifyReceiveMsg()
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            FIMManager.getInstance().mInternalMsgCallback.onReceiveMsg(this);
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
    protected abstract FIMMsgData<M> onParseSDKMsg() throws Exception;

    protected abstract void onFillData(FIMMsgData<M> data) throws Exception;

    protected void onError(Exception e)
    {

    }
}
