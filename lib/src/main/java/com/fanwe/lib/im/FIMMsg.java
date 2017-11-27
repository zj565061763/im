package com.fanwe.lib.im;

/**
 * IM消息
 */
public interface FIMMsg
{
    /**
     * 返回数据类型
     *
     * @return
     */
    int getDataType();

    /**
     * 返回自定义数据对象
     *
     * @return
     */
    FIMMsgData getData();

    /**
     * 消息是否是自己发送的
     *
     * @return
     */
    boolean isSelf();

    /**
     * 返回消息的时间戳（毫秒）
     *
     * @return
     */
    long getTimestamp();

    /**
     * 返回消息的状态
     *
     * @return
     */
    FIMMsgState getState();

    /**
     * 返回消息对应的会话对象
     *
     * @return
     */
    FIMConversation getConversation();

    /**
     * 移除消息
     *
     * @return
     */
    boolean remove();

    /**
     * 把当前消息通知给消息接收回调
     */
    void notifyReceiveMsg();
}
