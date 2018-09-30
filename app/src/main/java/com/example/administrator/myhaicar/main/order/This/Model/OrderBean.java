package com.example.administrator.myhaicar.main.order.This.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class OrderBean {


    /**
     * hotlistinfo : [{"order_id":"413","order_cartypelogo":"http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985172.png","order_cartype":"奔驰S级","order_color":"黑","order_carclass":"车队","order_date":"2018/04/11","order_time":"上午","order_km":"2.00","order_price":"2.00","order_site":"擦掉9","order_rob":0},{"order_id":"409","order_cartypelogo":"http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985172.png","order_cartype":"奔驰S级","order_color":"黑","order_carclass":"头车","order_date":"2018/04/11","order_time":"上午","order_km":"1.00","order_price":"10.00","order_site":"擦掉5","order_rob":0},{"order_id":"405","order_cartypelogo":"http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985172.png","order_cartype":"奔驰S级","order_color":"黑","order_carclass":"车队","order_date":"2018/04/12","order_time":"上午","order_km":"1.00","order_price":"1.00","order_site":"擦掉1","order_rob":0}]
     * app_version : 1.1.1
     */

    private String app_version;
    private List<HotlistinfoBean> hotlistinfo;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public List<HotlistinfoBean> getHotlistinfo() {
        return hotlistinfo;
    }

    public void setHotlistinfo(List<HotlistinfoBean> hotlistinfo) {
        this.hotlistinfo = hotlistinfo;
    }

    public static class HotlistinfoBean {
        /**
         * order_id : 413
         * order_cartypelogo : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985172.png
         * order_cartype : 奔驰S级
         * order_color : 黑
         * order_carclass : 车队
         * order_date : 2018/04/11
         * order_time : 上午
         * order_km : 2.00
         * order_price : 2.00
         * order_site : 擦掉9
         * order_rob : 0
         */

        private String order_id;
        private String order_cartypelogo;
        private String order_cartype;
        private String order_color;
        private String order_carclass;
        private String order_date;
        private String order_time;
        private String order_km;
        private String order_price;
        private String order_site;
        private int order_rob;

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

        public String getOrder_cartype() {
            return order_cartype;
        }

        public void setOrder_cartype(String order_cartype) {
            this.order_cartype = order_cartype;
        }

        public String getOrder_color() {
            return order_color;
        }

        public void setOrder_color(String order_color) {
            this.order_color = order_color;
        }

        public String getOrder_carclass() {
            return order_carclass;
        }

        public void setOrder_carclass(String order_carclass) {
            this.order_carclass = order_carclass;
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

        public String getOrder_site() {
            return order_site;
        }

        public void setOrder_site(String order_site) {
            this.order_site = order_site;
        }

        public int getOrder_rob() {
            return order_rob;
        }

        public void setOrder_rob(int order_rob) {
            this.order_rob = order_rob;
        }
    }
}
