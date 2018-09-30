package com.example.administrator.myhaicar.main.login.This.Model;

/**
 * Created by Administrator on 2017/6/6.
 */

public class NoLoginBean {

    /**
     * ret_prompt : 0
     * ret_info : 登录失败-验证码错误
     * app_version : 1.1.1
     */

    private String ret_prompt;
    private String ret_info;
    private String app_version;

    public String getRet_prompt() {
        return ret_prompt;
    }

    public void setRet_prompt(String ret_prompt) {
        this.ret_prompt = ret_prompt;
    }

    public String getRet_info() {
        return ret_info;
    }

    public void setRet_info(String ret_info) {
        this.ret_info = ret_info;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
