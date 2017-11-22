package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public interface FIMResultCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String msg);
}
