package com.sd.lib.im.common;

import com.sd.lib.im.conversation.FIMConversationType;

/**
 * 发送消息参数配置
 */
public class SendMsgParam
{
    /** 会话方id */
    public final String peer;
    /** 会话类型 */
    public final FIMConversationType conversationType;
    /** 是否在线消息，即只有用户在线才会接收到，且不会做本地缓存 */
    public final boolean isOnlineMsg;
    /** 优先级 */
    public final int priority;

    private SendMsgParam(Builder builder)
    {
        this.peer = builder.peer;
        this.conversationType = builder.conversationType;
        this.isOnlineMsg = builder.isOnlineMsg;
        this.priority = builder.priority;
        // 暂时不做参数校验
    }

    public static final class Builder
    {
        private String peer;
        private FIMConversationType conversationType;
        private boolean isOnlineMsg;
        private int priority;

        public Builder setPeer(String peer)
        {
            this.peer = peer;
            return this;
        }

        public Builder setConversationType(FIMConversationType conversationType)
        {
            this.conversationType = conversationType;
            return this;
        }

        public Builder setOnlineMsg(boolean isOnlineMsg)
        {
            this.isOnlineMsg = isOnlineMsg;
            return this;
        }

        public Builder setPriority(int priority)
        {
            this.priority = priority;
            return this;
        }

        public SendMsgParam build()
        {
            return new SendMsgParam(this);
        }
    }
}
