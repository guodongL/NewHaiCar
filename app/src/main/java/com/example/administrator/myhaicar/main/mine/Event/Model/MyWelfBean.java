package com.example.administrator.myhaicar.main.mine.Event.Model;

import java.util.List;

/**
 * Created by 果冻 on 2017/6/16.
 */

public class MyWelfBean {

    private List<ActiInfoBean> acti_info;

    public List<ActiInfoBean> getActi_info() {
        return acti_info;
    }

    public void setActi_info(List<ActiInfoBean> acti_info) {
        this.acti_info = acti_info;
    }

    public static class ActiInfoBean {
        /**
         * acti_id : 29
         * acti_state : 0
         * acti_date : 4月24日
         * acti_price : 150.00
         * acti_pic : http://yingqin8.com/ueditor/php/upload/image/1492399555.jpg
         * acti_mpnum : 0
         * acti_vip : 0
         * acti_title : 初见的味道
         * acti_url : http://yingqin8.com/app/activity/view.php?id=29
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
    }
}
