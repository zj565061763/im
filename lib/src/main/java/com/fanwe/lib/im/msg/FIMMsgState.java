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
package com.fanwe.lib.im.msg;

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
