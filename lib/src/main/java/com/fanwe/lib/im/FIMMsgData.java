package com.fanwe.lib.im;

/**
 * IM消息数据
 *
 * @param <M> 第三方IM消息类型
 */
public interface FIMMsgData<M>
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
     */
    M parseToSDKMsg();

    /**
     * 将数据解析为FIM消息
     *
     * @return
     */
    FIMMsg parseToMsg();
}
