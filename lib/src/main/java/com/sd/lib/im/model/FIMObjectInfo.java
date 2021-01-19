package com.sd.lib.im.model;

class FIMObjectInfo
{
    // 唯一id
    private final String id;
    // 头像
    private String headImage;
    // 显示名称
    private String displayName;

    public FIMObjectInfo(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public String getHeadImage()
    {
        return headImage;
    }

    public void setHeadImage(String headImage)
    {
        this.headImage = headImage;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
}
