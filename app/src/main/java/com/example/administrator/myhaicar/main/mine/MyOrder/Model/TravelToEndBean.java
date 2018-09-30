package com.example.administrator.myhaicar.main.mine.MyOrder.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class TravelToEndBean {

    /**
     * listinfo : [{"order_id":"410","order_color":"黑","order_cartype":"奔驰S级","order_countdown":0,"order_date":"2017/06/08","order_time":"上午","order_addess":"擦掉6","order_type":"头车"},{"order_id":"407","order_color":"白","order_cartype":"宝马5系","order_countdown":0,"order_date":"2017/06/07","order_time":"上午","order_addess":"擦掉3","order_type":"头车"}]
     * app_version : 1.1.1
     */

    private String app_version;
    private List<ListinfoBean> listinfo;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public List<ListinfoBean> getListinfo() {
        return listinfo;
    }

    public void setListinfo(List<ListinfoBean> listinfo) {
        this.listinfo = listinfo;
    }

    public static class ListinfoBean {
        /**
         * order_id : 410
         * order_color : 黑
         * order_cartypelogo : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png
         * order_cartype : 奔驰S级
         * order_countdown : 0
         * order_date : 2017/06/08
         * order_time : 上午
         * order_addess : 擦掉6
         * order_type : 头车
         */

        private String order_id;
        private String order_color;
        private String order_cartypelogo;
        private String order_cartype;
        private int order_countdown;
        private String order_date;
        private String order_time;
        private String order_addess;
        private String order_type;
        public String getOrder_cartypelogo() {
            return order_cartypelogo;
        }

        public void setOrder_cartypelogo(String order_cartypelogo) {
            this.order_cartypelogo = order_cartypelogo;
        }
        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_color() {
            return order_color;
        }

        public void setOrder_color(String order_color) {
            this.order_color = order_color;
        }

        public String getOrder_cartype() {
            return order_cartype;
        }

        public void setOrder_cartype(String order_cartype) {
            this.order_cartype = order_cartype;
        }

        public int getOrder_countdown() {
            return order_countdown;
        }

        public void setOrder_countdown(int order_countdown) {
            this.order_countdown = order_countdown;
        }

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getOrder_addess() {
            return order_addess;
        }

        public void setOrder_addess(String order_addess) {
            this.order_addess = order_addess;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
