package com.sd.lib.im;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.msg.FIMMsgData;

import org.json.JSONObject;

/**
 * 第三方IM消息接收处理类
 *
 * @param <M>第三方IM消息类型
 */
public abstract class FIMMsgParser<M> implements FIMMsg
{
    private M mSDKMsg;
    private FIMMsgData mData;

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
    public final FIMMsgData getData()
    {
        return mData != null ? mData : FIMMsgData.EMPTY;
    }

    @Override
    public int getDataType()
    {
        return getData().getType();
    }

    /**
     * 解析第三方的SDK消息
     *
     * @return
     */
    public final boolean parse(M sdkMsg)
    {
        if (sdkMsg == null)
            throw new NullPointerException("sdkMsg must not be null");

        mSDKMsg = sdkMsg;
        mData = null;

        String json = null;
        try
        {
            json = onParseToJson(sdkMsg);
        } catch (Exception e)
        {
            onError(e);
        }
        if (TextUtils.isEmpty(json))
            return false;

        final int type = getTypeFromJson(json);
        if (type == FIMMsgData.TYPE_NONE)
            return false;

        final Class clazz = FIMManager.getInstance().getMsgDataClass(type);
        if (clazz == null)
        {
            onError(new RuntimeException("Msg data class for " + type + " was not found"));
            return false;
        }

        try
        {
            mData = onParseToMsgData(type, json, clazz);
        } catch (Exception e)
        {
            onError(e);
        }

        if (mData != null)
            mData.fillData(sdkMsg);

        return mData != null;
    }

    /**
     * 从数据json中获取数据类型
     *
     * @param json
     * @return 返回猜测的数据类型，-1表示猜测失败
     */
    protected int getTypeFromJson(String json)
    {
        try
        {
            return new JSONObject(json).getInt("type");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return FIMMsgData.TYPE_NONE;
    }

    /**
     * 将消息的数据解析为json
     *
     * @param sdkMsg
     * @return
     * @throws Exception
     */
    protected abstract String onParseToJson(M sdkMsg) throws Exception;

    /**
     * 解析为消息数据
     *
     * @param type  数据类型
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    protected abstract FIMMsgData<M> onParseToMsgData(int type, String json, Class clazz) throws Exception;

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
     * 解析异常
     *
     * @param e
     */
    protected void onError(Exception e)
    {
    }
}
