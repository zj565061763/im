package com.fanwe.lib.im;

/**
 * Created by Administrator on 2017/11/23.
 */

public interface FIMConversation
{
    String getPeer();

    FIMConversationType getType();

    long getUnreadMessageNum();
}
