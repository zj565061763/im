package com.fanwe.lib.im;

/**
 * Created by zhengjun on 2017/11/22.
 */
class FIMMsgImpl implements FIMMsg
{
    /**
     * 数据类型
     */
    private int dataType;
    /**
     * 数据实体
     */
    private FIMData data;

    /**
     * 是否自己发送的
     */
    private boolean isSelf = false;
    /**
     * 会话的对方id或者群组Id
     */
    private String conversationtPeer;
    /**
     * 消息在服务端生成的时间戳
     */
    private long timestamp;

    @Override
    public int getDataType()
    {
        return dataType;
    }

    public void setDataType(int dataType)
    {
        this.dataType = dataType;
    }

    @Override
    public FIMData getData()
    {
        return data;
    }

    public void setData(FIMData data)
    {
        this.data = data;
    }

    @Override
    public boolean isSelf()
    {
        return isSelf;
    }

    public void setSelf(boolean self)
    {
        isSelf = self;
    }

    @Override
    public String getConversationtPeer()
    {
        return conversationtPeer;
    }

    public void setConversationtPeer(String conversationtPeer)
    {
        this.conversationtPeer = conversationtPeer;
    }

    @Override
    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
}
