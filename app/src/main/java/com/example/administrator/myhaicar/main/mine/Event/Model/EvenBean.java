package com.example.administrator.myhaicar.main.mine.Event.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class EvenBean {

    /**
     * actilistinfo : [{"acti_id":"27","acti_state":"0","acti_date":"4月1日\u2014\u20144月2日","acti_price":"0.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1490258600.jpg","acti_mpnum":"30","acti_vip":"0","acti_title":"黑暗来袭，我们需要你的帮助","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=27","usr_isorno":0,"acti_sort":"8","acti_hot":"0","acti_content":"在此召集全城狼人杀高手","acti_priceold":"0.00","acti_view_num":"0","acti_apnum":"2"},{"acti_id":"28","acti_state":"0","acti_date":"六月底前","acti_price":"8.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1491988334.jpg","acti_mpnum":"10","acti_vip":"1","acti_title":"八元洗车！会员专享！","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=28","usr_isorno":0,"acti_sort":"9","acti_hot":"0","acti_content":"","acti_priceold":"48.00","acti_view_num":"0","acti_apnum":"1"},{"acti_id":"26","acti_state":"0","acti_date":"201000000","acti_price":"600.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1489044769.png","acti_mpnum":"20","acti_vip":"1","acti_title":"长白山天池自驾游1","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=26","usr_isorno":0,"acti_sort":"1","acti_hot":"1","acti_content":"饿哦日图规划空心菜vfbh","acti_priceold":"0.00","acti_view_num":"0","acti_apnum":"0"},{"acti_id":"22","acti_state":"1","acti_date":"2017.3.31前","acti_price":"0.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1488863953.png","acti_mpnum":"100","acti_vip":"1","acti_title":"123123123123123123","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=22","usr_isorno":0,"acti_sort":"2","acti_hot":"1","acti_content":"今后发达国家恢复","acti_priceold":"12.30","acti_view_num":"1","acti_apnum":"0"},{"acti_id":"23","acti_state":"0","acti_date":"2017.3.31前","acti_price":"5434.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1488863953.png","acti_mpnum":"100","acti_vip":"0","acti_title":"2嗨车送百份千元豪礼！会员免费拿！2","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=23","usr_isorno":0,"acti_sort":"5","acti_hot":"1","acti_content":"今后发达国家恢复","acti_priceold":"0.00","acti_view_num":"23","acti_apnum":"5"},{"acti_id":"24","acti_state":"0","acti_date":"2017.3.31前","acti_price":"0.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1488863953.png","acti_mpnum":"10","acti_vip":"0","acti_title":"嗨车送百份千元豪礼！会员免费拿！","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=24","usr_isorno":0,"acti_sort":"3","acti_hot":"0","acti_content":"今后发达国家恢复","acti_priceold":"12.30","acti_view_num":"43","acti_apnum":"2"},{"acti_id":"25","acti_state":"0","acti_date":"2017.3.31前","acti_price":"0.00","acti_pic":"http://apptest.yingqin8.com/ueditor/php/upload/image/1488863953.png","acti_mpnum":"100","acti_vip":"1","acti_title":"隧道发生的","acti_url":"http://apptest.yingqin8.com/haiche/app/activity/view.php?id=25","usr_isorno":0,"acti_sort":"8","acti_hot":"1","acti_content":"今后发达国家恢复","acti_priceold":"12.30","acti_view_num":"789","acti_apnum":"2"}]
     * acti_end : 1
     * app_version : 1.1.1
     */

    private String acti_end;
    private String app_version;
    private List<ActilistinfoBean> actilistinfo;

    public String getActi_end() {
        return acti_end;
    }

    public void setActi_end(String acti_end) {
        this.acti_end = acti_end;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public List<ActilistinfoBean> getActilistinfo() {
        return actilistinfo;
    }

    public void setActilistinfo(List<ActilistinfoBean> actilistinfo) {
        this.actilistinfo = actilistinfo;
    }

    public static class ActilistinfoBean {
        /**
         * acti_id : 27
         * acti_state : 0
         * acti_date : 4月1日——4月2日
         * acti_price : 0.00
         * acti_pic : http://apptest.yingqin8.com/ueditor/php/upload/image/1490258600.jpg
         * acti_mpnum : 30
         * acti_vip : 0
         * acti_title : 黑暗来袭，我们需要你的帮助
         * acti_url : http://apptest.yingqin8.com/haiche/app/activity/view.php?id=27
         * usr_isorno : 0
         * acti_sort : 8
         * acti_hot : 0
         * acti_content : 在此召集全城狼人杀高手
         * acti_priceold : 0.00
         * acti_view_num : 0
         * acti_apnum : 2
         */

        private String acti_id;
        private String acti_state;
        private String acti_date;
        private String acti_price;
        private String acti_pic;
        private String acti_mpnum;
        private String acti_vip;
        private String acti_title;
        private String acti_url;
        private int usr_isorno;
        private String acti_sort;
        private String acti_hot;
        private String acti_content;
        private String acti_priceold;
        private String acti_view_num;
        private String acti_apnum;

        public String getActi_id() {
            return acti_id;
        }

        public void setActi_id(String acti_id) {
            this.acti_id = acti_id;
        }

        public String getActi_state() {
            return acti_state;
        }

        public void setActi_state(String acti_state) {
            this.acti_state = acti_state;
        }

        public String getActi_date() {
            return acti_date;
        }

        public void setActi_date(String acti_date) {
            this.acti_date = acti_date;
        }

        public String getActi_price() {
            return acti_price;
        }

        public void setActi_price(String acti_price) {
            this.acti_price = acti_price;
        }

        public String getActi_pic() {
            return acti_pic;
        }

        public void setActi_pic(String acti_pic) {
            this.acti_pic = acti_pic;
        }

        public String getActi_mpnum() {
            return acti_mpnum;
        }

        public void setActi_mpnum(String acti_mpnum) {
            this.acti_mpnum = acti_mpnum;
        }

        public String getActi_vip() {
            return acti_vip;
        }

        public void setActi_vip(String acti_vip) {
            this.acti_vip = acti_vip;
        }

        public String getActi_title() {
            return acti_title;
        }

        public void setActi_title(String acti_title) {
            this.acti_title = acti_title;
        }

        public String getActi_url() {
            return acti_url;
        }

        public void setActi_url(String acti_url) {
            this.acti_url = acti_url;
        }

        public int getUsr_isorno() {
            return usr_isorno;
        }

        public void setUsr_isorno(int usr_isorno) {
            this.usr_isorno = usr_isorno;
        }

        public String getActi_sort() {
            return acti_sort;
        }

        public void setActi_sort(String acti_sort) {
            this.acti_sort = acti_sort;
        }

        public String getActi_hot() {
            return acti_hot;
        }

        public void setActi_hot(String acti_hot) {
            this.acti_hot = acti_hot;
        }

        public String getActi_content() {
            return acti_content;
        }

        public void setActi_content(String acti_content) {
            this.acti_content = acti_content;
        }

        public String getActi_priceold() {
            return acti_priceold;
        }

        public void setActi_priceold(String acti_priceold) {
            this.acti_priceold = acti_priceold;
        }

        public String getActi_view_num() {
            return acti_view_num;
        }

        public void setActi_view_num(String acti_view_num) {
            this.acti_view_num = acti_view_num;
        }

        public String getActi_apnum() {
            return acti_apnum;
        }

        public void setActi_apnum(String acti_apnum) {
            this.acti_apnum = acti_apnum;
        }
    }
}
