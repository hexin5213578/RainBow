package com.YiDian.RainBow.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: SPUtil
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public class SPUtil {

    public static final String FILE_NAME = "userInfo";
    public static final String KEY_TOKEN = "token";
    //存储是否已经登录  0已登录 1未登录或退出登录
    public static final String IS_LOGIN = "islogin";
    //是否完善资料  0已完善 1未完善
    public static final String IS_PERFECT = "isperfect";
    public static final String HEAD_IMG = "headimg";
    public static final String KEY_PHONE = "phone";
    public static final String USER_NAME = "username";
    public static final String USER_ID = "user_id";


    public static final String COUNT = "count";

    public static final String JSON_Dynamic = "dynamic";
    public static final String JSOn_drafts = "drafts";
    public static final String JSON_Friend = "friend";
    public static final String JSON_Fans = "fans";
    public static final String JSON_Follow = "follow";


    private SPUtil() {
    }

    private static class SingleInstance{
        private static SPUtil sSPUtil = new SPUtil();
    }
    public static SPUtil getInstance(){
        return SingleInstance.sSPUtil;
    }
    //存储信息
    @SuppressLint("CommitPrefEdits")
    public void saveData(Context context, String fileName, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //存储信息
    @SuppressLint("CommitPrefEdits")
    public void saveDataOffloat(Context context, String fileName, String key, float value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }
    //读取信息
    public String getData(Context context, String fileName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        return string;
    }
    //读取信息
    public float getDataOffloat(Context context, String fileName, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        float string = sharedPreferences.getFloat(key, 0);
        return string;
    }
    //清除
    public static void unReg(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //存储集合
    public static void setMap(Context context,String key, LinkedHashMap<String, String> datas) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savemap", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        JSONArray mJsonArray = new JSONArray();
        Iterator<Map.Entry<String, String>> iterator = datas.entrySet().iterator();
        JSONObject object = new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
            }
        }
        mJsonArray.put(object);
        edit.putString(key, mJsonArray.toString());
        edit.commit();
    }

    //获取集合
    public static LinkedHashMap<String, String> getMap( Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savemap", Context.MODE_PRIVATE);

        LinkedHashMap<String, String> datas = new LinkedHashMap<>();
        String result = sharedPreferences.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        datas.put(name, value);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
