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
     * @param msg  失败描述
     */
    public abstract void onError(int code, String msg);
}
