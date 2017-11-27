package com.fanwe.lib.im;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 第三方IM消息接收处理类
 *
 * @param <M>第三方IM消息类型
 */
public abstract class FIMMsgReceiver<M> implements FIMMsg
{
    public static final String FIELD_NAME_GUESS_DATA_TYPE = "type";
    public static final int EMPTY_DATA_TYPE = -1;

    private Class<M> mSDKMsgClass;
    private M mSDKMsg;
    private FIMMsgData mData;

    public FIMMsgReceiver()
    {
        Type[] arrType = getType(getClass());
        if (arrType != null && arrType.length == 1)
        {
            Type type = arrType[0];
            mSDKMsgClass = (Class<M>) type;
        }
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
     * 从json串中猜测对应的数据类型
     *
     * @param json
     * @return
     */
    protected int guessDataTypeFromJson(String json)
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
            int dataType = jsonObject.optInt(fieldName, -1);
            return dataType;
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
    public boolean parse(M sdkMsg)
    {
        if (sdkMsg == null)
        {
            throw new NullPointerException("sdkMsg must not be null");
        }
        if (!mSDKMsgClass.isInstance(sdkMsg))
        {
            throw new IllegalArgumentException("sdkMsg must be instance of " + mSDKMsgClass);
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

    private static Type[] getType(Class clazz)
    {
        Type[] types = null;
        if (clazz != null)
        {
            Type type = clazz.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            types = parameterizedType.getActualTypeArguments();
        }

        return types;
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
     * 是否有需要下载的数据，true-需要<br>
     * 如果需要下载数据，并且callback不为null，则开始下载数据
     *
     * @param callback
     * @return
     */
    public abstract boolean isNeedDownloadData(FIMResultCallback callback);

    /**
     * 解析异常回调
     *
     * @param e
     */
    protected void onError(Exception e)
    {

    }
}
