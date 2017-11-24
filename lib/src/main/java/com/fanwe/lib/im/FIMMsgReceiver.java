package com.fanwe.lib.im;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONObject;

/**
 * 第三方IM消息接收处理类
 *
 * @param <M>第三方IM消息类型
 */
public abstract class FIMMsgReceiver<M> implements FIMMsg
{
    public static final String FIELD_NAME_GUESS_DATA_TYPE = "type";

    private M mSDKMsg;
    private FIMMsgData mData;

    public FIMMsgReceiver(M sdkMsg)
    {
        parse(sdkMsg);
    }

    /**
     * 返回第三发IM消息对象
     *
     * @return
     */
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

    /**
     * 返回用于猜测数据类型的json字段名称
     *
     * @return
     */
    protected String getFieldNameGuessDataType()
    {
        return FIELD_NAME_GUESS_DATA_TYPE;
    }

    /**
     * 从json串中猜测对应的数据Class对象
     *
     * @param json
     * @return
     */
    public Class guessDataClassFromJson(String json)
    {
        if (TextUtils.isEmpty(json))
        {
            return null;
        }
        final String fieldName = getFieldNameGuessDataType();
        if (TextUtils.isEmpty(fieldName))
        {
            return null;
        }
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            int dataType = jsonObject.optInt(fieldName, -1);
            return FIMManager.getInstance().getDataClass(dataType);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
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
            throw new IllegalArgumentException("sdkMsg must not be null");
        }
        try
        {
            mSDKMsg = sdkMsg;
            mData = onParseSDKMsg();
        } catch (Exception e)
        {
            onError(e);
        }

        if (mData != null)
        {
            onFillData(mData);
            return true;
        } else
        {
            return false;
        }
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

    /**
     * 填充解析好的数据
     *
     * @param data
     */
    protected abstract void onFillData(FIMMsgData<M> data);

    /**
     * 解析异常回调
     *
     * @param e
     */
    protected void onError(Exception e)
    {

    }
}
