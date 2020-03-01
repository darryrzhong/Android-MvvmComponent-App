package com.drz.base.storage;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用模块: storage
 * <p>
 * 类描述: 腾讯MMKV序列化存储工具类
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-27
 */
public class MmkvHelper {
    private static MMKV mmkv;

    private MmkvHelper(){
        mmkv = MMKV.defaultMMKV();
    }

    public static MmkvHelper getInstance(){
        return MmkvHolder.INSTANCE;
    }
    private static class MmkvHolder{
        private static final MmkvHelper INSTANCE = new MmkvHelper();
    }

    public  MMKV getMmkv() {
        return mmkv;
    }

    /**
     * 存入map集合
     *
     * @param key 标识
     * @param map 数据集合
     */
    public   void saveInfo(String key, Map<String, Object> map)
    {
        Gson gson = new Gson();
        JSONArray mJsonArray = new JSONArray();
        JSONObject object = null;
        try
        {
            object = new JSONObject(gson.toJson(map));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        mJsonArray.put(object);
        mmkv.encode(key, mJsonArray.toString());
    }

    /**
     * 获取map集合
     *
     * @param key 标识
     */
    public   Map<String, String> getInfo(String key)
    {
        Map<String, String> itemMap = new HashMap<>();
        String result = mmkv.decodeString(key, "");
        try
        {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject itemObject = array.getJSONObject(i);

                JSONArray names = itemObject.names();
                if (names != null)
                {
                    for (int j = 0; j < names.length(); j++)
                    {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
            }
        }
        catch (JSONException e)
        {

        }
        return itemMap;
    }

    /**
     * 扩展MMKV类使其支持对象存储
     *
     */

    public   <T> T getObject(String key, Class<T> t)
    {
        String str = mmkv.decodeString(key, null);
        if (!TextUtils.isEmpty(str))
        {
            return new GsonBuilder().create().fromJson(str, t);
        }
        else
        {
            return null;
        }
    }

    public void putObject(String key, Object object)
    {
        String jsonString = new GsonBuilder().create().toJson(object);
        mmkv.encode(key, jsonString);
    }

}
