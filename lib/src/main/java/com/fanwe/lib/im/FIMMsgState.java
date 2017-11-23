package com.fanwe.lib.im;

public enum FIMMsgState
{
    /**
     * 非法值
     */
    Invalid,
    /**
     * 发送失败
     */
    SendFail,
    /**
     * 发送中
     */
    Sending,
    /**
     * 发送成功
     */
    SendSuccess,
    /**
     * 被标记为已删除
     */
    HasDeleted;
}
