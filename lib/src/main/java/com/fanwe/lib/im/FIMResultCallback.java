package com.fanwe.lib.im;

/**
 * IM通用的结果回调
 */
public interface FIMResultCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String msg);
}
