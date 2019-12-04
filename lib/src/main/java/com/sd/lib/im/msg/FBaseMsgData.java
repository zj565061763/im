package com.sd.lib.im.msg;

import com.sd.lib.im.annotation.MsgData;

public abstract class FBaseMsgData<M> implements FIMMsgData<M>
{
    private int type;

    public FBaseMsgData()
    {
        final MsgData msgData = getClass().getAnnotation(MsgData.class);
        if (msgData == null)
            throw new IllegalArgumentException("(MsgData) annotation was not found in clazz: " + getClass().getName());

        this.type = msgData.type();
    }

    @Override
    public final int getType()
    {
        return type;
    }

    @Override
    public FillDataTask getFillDataTask()
    {
        return null;
    }

    @Override
    public String getConversationDescription()
    {
        return "";
    }
}
