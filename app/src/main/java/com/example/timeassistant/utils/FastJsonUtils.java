package com.example.timeassistant.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by xqc on 2016/6/1.
 * ZKTeco App
 */
public class FastJsonUtils {
    private static final String TAG = "FastJsonUtils";
    private static final boolean DEBUG = true;

    public static <T> List<T> decode(String json, Class<T> clazz) {
        Log.d(TAG, "json:before " );
//        if (DEBUG) Log.d(TAG, "decode: " + json);
        List<T> list = JSON.parseArray(json, clazz);
        /*for (T item : list) {
            Log.d(TAG, "decode: " + item.toString());
        }*/
        Log.d(TAG, "json:after " + list.size());
        return list;
    }

    public static <T> List<T> decode(byte[] data, Class<T> clazz) {
        return decode(new String(data).trim(), clazz);
    }

    public static <T> List<T> decodeGBK(byte[] data, Class<T> clazz) {
        try {
            return decode(new String(data, "GB18030").trim(), clazz);
//            return decode(new String(data, "UTF-8").trim(), clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> decodeUTF(byte[] data, Class<T> clazz) {
        try {
//            return decode(new String(data, "GB18030").trim(), clazz);
            return decode(new String(data, "UTF-8").trim(), clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(List list) {
        if (DEBUG) {
            for (Object item : list) {
                Log.d(TAG, "toJson: " + item.toString());
            }
        }
        String str = JSON.toJSONString(list);
        if (DEBUG) Log.d(TAG, "toJson: " + str);
        return str;
    }

    public static String toJson2(List list) {
        if (DEBUG) {
            for (Object item : list) {
                Log.d(TAG, "toJson: " + item.toString());
            }
        }
        String str = JSON.toJSONString(list, new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                if (name.equals("shiftId") || name.equals("select") || name.equals("name") || name.equals("isSelect"))
                    return false;
                return true;
            }
        });
        if (DEBUG) Log.d(TAG, "toJson: " + str);
        return str;
    }
}
