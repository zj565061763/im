package com.sd.www.im.tim.data;

import com.tencent.TIMMessage;

/**
 * Created by Administrator on 2017/11/23.
 */

public class CustomTextData extends CustomData
{
    @Override
    public int getType()
    {
        return TEXT_IM;
    }

    @Override
    public void fillData(TIMMessage sdkMsg)
    {
    }
}
