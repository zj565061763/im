package com.sd.lib.im.msg;

/**
 * IM消息数据
 *
 * @param <M> 第三方IM消息类型
 */
public interface FIMMsgData<M>
{
    int TYPE_NONE = Integer.MIN_VALUE;

    FIMMsgData EMPTY = new FIMMsgData()
    {
        @Override
        public int getType()
        {
            return TYPE_NONE;
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

        @Override
        public FillDataTask getFillDataTask()
        {
            return null;
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

    /**
     * 返回需要异步填充数据的任务
     *
     * @return 如果不为null-说明需要异步填充数据；null-不需要异步填充数据
     */
    FillDataTask getFillDataTask();

    interface FillDataTask
    {
        void execute(FillDataCallback callback);
    }

    interface FillDataCallback
    {
        void onSuccess();

        void onError(int code, int desc);
    }
}
