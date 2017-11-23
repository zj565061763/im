package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */

public interface FIMMsgData<T>
{
    /**
     * 返回数据类型
     *
     * @return
     */
    int getType();

    /**
     * 将当前数据解析为第三方SDK的消息
     *
     * @return
     * @throws Exception
     */
    T parseToSDKMsg() throws Exception;
}
