package com.sd.lib.im.msg;

import com.sd.lib.im.conversation.FIMConversation;
import com.sd.lib.im.ext.ITimestamp;
import com.sd.lib.im.model.FIMUserInfo;

/**
 * IM消息
 */
public interface FIMMsg extends ITimestamp
{
    /**
     * 返回消息的唯一ID
     *
     * @return
     */
    String getID();

    /**
     * 返回自定义数据对象
     *
     * @return
     */
    FIMMsgData getData();

    /**
     * 数据类型
     *
     * @return
     */
    int getDataType();

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
    @Override
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
     * 发送者信息
     *
     * @return
     */
    FIMUserInfo getSender();

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

    /**
     * 返回原始消息，即第三方SDK消息对象
     *
     * @return
     */
    Object getOriginalMsg();
}
