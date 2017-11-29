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
package com.fanwe.lib.im.callback;

/**
 * IM通用的结果回调
 *
 * @param <T> 结果数据类型
 */
public abstract class FIMResultCallback<T>
{
    private static final String KEY = "$";

    /**
     * 返回callback对应的tag，可用于ui销毁的时候移除callback
     *
     * @return
     */
    public String getTag()
    {
        String name = this.getClass().getName();
        if (name.contains(KEY))
        {
            name = name.substring(0, name.indexOf(KEY));
        }
        return name;
    }

    /**
     * 成功回调
     *
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 失败回调
     *
     * @param code 错误码
     * @param desc 失败描述
     */
    public abstract void onError(int code, String desc);
}
