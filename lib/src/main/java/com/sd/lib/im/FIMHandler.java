package com.sd.lib.im;

import com.sd.lib.im.callback.FIMResultCallback;
import com.sd.lib.im.conversation.FIMConversationType;
import com.sd.lib.im.msg.FIMMsg;
import com.sd.lib.im.msg.FIMMsgData;

/**
 * IM处理类
 *
 * @param <M> 第三方IM消息类型
 */
public abstract class FIMHandler<M>
{
    /**
     * 返回新创建的第三方IM消息接收对象
     *
     * @return
     */
    public abstract FIMMsgReceiver<M> newMsgReceiver();

    /**
     * 移除并返回结果回调
     *
     * @param callbackId 回调id
     * @return
     */
    public final FIMResultCallback removeCallbackById(String callbackId)
    {
        return FIMManager.getInstance().removeCallbackById(callbackId);
    }

    /**
     * 发送消息
     *
     * @param peer             对方id
     * @param msgData          消息数据
     * @param conversationType 消息类型
     * @param callbackId       回调id
     * @return
     */
    public abstract FIMMsg sendMsg(String peer, FIMMsgData<M> msgData, FIMConversationType conversationType, String callbackId);

    /**
     * 加入群组
     *
     * @param groupId    群组id
     * @param callbackId 回调id
     */
    public abstract void joinGroup(String groupId, String callbackId);

    /**
     * 退出群组
     *
     * @param groupId    群组id
     * @param callbackId 回调id
     */
    public abstract void quitGroup(String groupId, String callbackId);
}
