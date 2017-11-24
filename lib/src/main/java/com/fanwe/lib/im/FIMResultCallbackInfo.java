package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/24.
 */
class FIMResultCallbackInfo
{
    public FIMResultCallback callback;
    public String tag;

    public FIMResultCallbackInfo(FIMResultCallback callback, String tag)
    {
        this.callback = callback;
        this.tag = tag;
    }
}
