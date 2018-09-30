package com.example.administrator.myhaicar.main.mine.MyOrder.Model;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyOrderDetailBean {


    /**
     * order_info : {"order_id":"417","order_cartypelogo":"http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png","order_num":"2017061850100515","order_carclass":"车队","order_mpnum":"2","order_color":"黑","order_cartype":"奥迪A6L2012款以下","order_countdown":0,"order_date":"2017/06/15","order_time":"上午","order_site":"3号","order_km":"24.00","order_price":"1.00","order_remarks":"备注信息~"}
     * order_bond : 2
     * app_version : 1.1.1
     */

    private OrderInfoBean order_info;
    private int order_bond;
    private String app_version;

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public int getOrder_bond() {
        return order_bond;
    }

    public void setOrder_bond(int order_bond) {
        this.order_bond = order_bond;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public static class OrderInfoBean {
        /**
         * order_id : 417
         * order_cartypelogo : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png
         * order_num : 2017061850100515
         * order_carclass : 车队
         * order_mpnum : 2
         * order_color : 黑
         * order_cartype : 奥迪A6L2012款以下
         * order_countdown : 0
         * order_date : 2017/06/15
         * order_time : 上午
         * order_site : 3号
         * order_km : 24.00
         * order_price : 1.00
         * order_remarks : 备注信息~
         */

        private String order_id;
        private String order_cartypelogo;
        private String order_num;
        private String order_carclass;
        private String order_mpnum;
        private String order_color;
        private String order_cartype;
        private int order_countdown;
        private String order_date;
        private String order_time;
        private String order_site;
        private String order_km;
        private String order_price;
        private String order_remarks;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_cartypelogo() {
            return order_cartypelogo;
        }

        public void setOrder_cartypelogo(String order_cartypelogo) {
            this.order_cartypelogo = order_cartypelogo;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getOrder_carclass() {
            return order_carclass;
        }

        public void setOrder_carclass(String order_carclass) {
            this.order_carclass = order_carclass;
        }

        public String getOrder_mpnum() {
            return order_mpnum;
        }

        public void setOrder_mpnum(String order_mpnum) {
            this.order_mpnum = order_mpnum;
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

        public String getOrder_site() {
            return order_site;
        }

        public void setOrder_site(String order_site) {
            this.order_site = order_site;
        }

        public String getOrder_km() {
            return order_km;
        }

        public void setOrder_km(String order_km) {
            this.order_km = order_km;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getOrder_remarks() {
            return order_remarks;
        }

        public void setOrder_remarks(String order_remarks) {
            this.order_remarks = order_remarks;
        }
    }
}
