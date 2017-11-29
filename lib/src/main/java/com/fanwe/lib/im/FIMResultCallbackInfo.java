package com.fanwe.lib.im;

import com.fanwe.lib.im.callback.FIMResultCallback;

/**
 * 保存callback信息
 */
class FIMResultCallbackInfo
{
    public FIMResultCallback callback;
    public String tag;
    public long createTime = System.currentTimeMillis();

    public FIMResultCallbackInfo(FIMResultCallback callback, String tag)
    {
        this.callback = callback;
        this.tag = tag;
    }
}
