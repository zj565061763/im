package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */

public interface FIMData<T>
{
    /**
     * 返回数据类型
     * @return
     */
    int getType();

    /**
     * 解析为第三方SDK的消息
     * @return
     * @throws Exception
     */
    T parseToSDKMsg() throws Exception;
}
