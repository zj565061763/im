package com.sd.lib.im.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class FIMUtils
{
    private FIMUtils()
    {
    }

    /**
     * 获取type字段
     *
     * @param json
     * @return
     */
    public static int getTypeFromJson(String json)
    {
        try
        {
            final JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getInt("type");
        } catch (JSONException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}
