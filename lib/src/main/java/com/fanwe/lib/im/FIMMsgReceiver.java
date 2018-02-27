/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.im;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.fanwe.lib.im.callback.FIMResultCallback;
import com.fanwe.lib.im.msg.FIMMsg;
import com.fanwe.lib.im.msg.FIMMsgData;

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
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        if (types != null && types.length == 1)
        {
            mSDKMsgClass = (Class<M>) types[0];
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
        if (!TextUtils.isEmpty(json))
        {
            final String fieldName = getFieldNameGuessDataType();
            if (!TextUtils.isEmpty(fieldName))
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(json);
                    return jsonObject.getInt(fieldName);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
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

    @Override
    public final void notifyReceiveMsg()
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            FIMManager.getInstance().notifyReceiveMsg(this);
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
