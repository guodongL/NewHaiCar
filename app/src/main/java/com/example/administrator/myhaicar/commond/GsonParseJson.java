package com.example.administrator.myhaicar.commond;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2017/4/20.
 */

public class GsonParseJson {
    public static <T> T parseJsonToMessageBean(String json){
        Gson gson = new Gson();
        T bean = gson.fromJson(json, new TypeToken<T>() {
        }.getType());
        return bean;
    }

}
