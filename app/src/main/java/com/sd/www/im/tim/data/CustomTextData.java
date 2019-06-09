package com.sd.www.im.tim.data;

import com.sd.lib.im.annotation.MsgData;

@MsgData(type = CustomData.TEXT_IM)
public class CustomTextData extends CustomData
{
    @Override
    public FillDataTask getFillDataTask()
    {
        return super.getFillDataTask();
    }
}
