package com.fanwe.lib.im;

/**
 * IM通用的结果回调
 */
public abstract class FIMResultCallback<T>
{
    private static final String KEY = "$";

    public String getTag()
    {
        String name = this.getClass().getName();
        if (name.contains(KEY))
        {
            name = name.substring(0, name.indexOf(KEY));
        }
        return name;
    }

    public abstract void onSuccess(T result);

    public abstract void onError(int code, String msg);
}
