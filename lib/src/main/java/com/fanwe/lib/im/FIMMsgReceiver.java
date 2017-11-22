package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
public abstract class FIMMsgReceiver<T> implements FIMMsg
{
    public final void parseMsg(T msg)
    {
        onParseMsg(msg);

        FIMMsgImpl msgImpl = new FIMMsgImpl();
        msgImpl.setDataType(getDataType());
        msgImpl.setData(getData());
        msgImpl.setConversationtPeer(getConversationtPeer());
        msgImpl.setSelf(isSelf());
        msgImpl.setTimestamp(getTimestamp());

        FIMManager.getInstance().mInternalMsgCallback.onReceiveMsg(msgImpl);
    }

    protected abstract void onParseMsg(T msg);
}
