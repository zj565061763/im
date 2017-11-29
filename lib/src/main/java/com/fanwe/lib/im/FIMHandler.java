/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.im;

import com.fanwe.lib.im.callback.FIMResultCallback;

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
    public final FIMResultCallback removeResultCallback(String callbackId)
    {
        return FIMManager.getInstance().removeResultCallback(callbackId);
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
