package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public interface FIMMsg
{
    int getDataType();

    FIMData getData();

    boolean isSelf();

    String getConversationtPeer();

    long getTimestamp();

    FIMMsgState getState();

    FIMConversationType getConversationType();
}
