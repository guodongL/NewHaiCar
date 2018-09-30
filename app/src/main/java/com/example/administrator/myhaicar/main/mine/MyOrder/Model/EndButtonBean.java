package com.example.administrator.myhaicar.main.mine.MyOrder.Model;

/**
 * Created by Administrator on 2017/6/9.
 */

public class EndButtonBean {

    /**
     * ret_prompt : 0
     * ret_info : 您的订单已经结束过或者暂时不能结束！请留意您的交易记录！
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
