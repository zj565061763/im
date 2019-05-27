package com.sd.lib.im.msg;

/**
 * IM消息数据
 *
 * @param <M> 第三方IM消息类型
 */
public interface FIMMsgData<M>
{
    FIMMsgData DEFAULT = new FIMMsgData()
    {
        @Override
        public int getType()
        {
            return Integer.MIN_VALUE;
        }

        @Override
        public Object parseToSDKMsg()
        {
            return null;
        }

        @Override
        public FIMMsg parseToMsg()
        {
            return null;
        }

        @Override
        public void fillData(Object sdkMsg)
        {
        }
    };

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

    /**
     * 填充第三方SDK消息中的数据到当前对象中
     *
     * @param sdkMsg
     */
    void fillData(M sdkMsg);
}
