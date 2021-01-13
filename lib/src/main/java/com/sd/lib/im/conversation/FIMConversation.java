package com.sd.lib.im.conversation;

/**
 * IM会话
 */
public interface FIMConversation
{
    /**
     * 会话id
     *
     * @return
     */
    String getPeer();

    /**
     * 会话类型
     *
     * @return
     */
    FIMConversationType getType();

    /**
     * 该会话的未读数量
     *
     * @return
     */
    int getUnreadCount();
}
