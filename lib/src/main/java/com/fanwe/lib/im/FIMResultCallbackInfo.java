package com.fanwe.lib.im;

/**
 * 保存callback信息
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
