package com.example.administrator.myhaicar.main.order.This.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class CarGridViewBean {

    /**
     * carsort : [{"car_sortid":"200","car_sortname":"奔驰S级"},{"car_sortid":"115","car_sortname":"宝马5系"}]
     * app_version : 1.1.1
     */

    private String app_version;
    private List<CarsortBean> carsort;

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public List<CarsortBean> getCarsort() {
        return carsort;
    }

    public void setCarsort(List<CarsortBean> carsort) {
        this.carsort = carsort;
    }

    public static class CarsortBean {
        /**
         * car_sortid : 200
         * car_sortname : 奔驰S级
         */

        private String car_sortid;
        private String car_sortname;

        public String getCar_sortid() {
            return car_sortid;
        }

        public void setCar_sortid(String car_sortid) {
            this.car_sortid = car_sortid;
        }

        public String getCar_sortname() {
            return car_sortname;
        }

        public void setCar_sortname(String car_sortname) {
            this.car_sortname = car_sortname;
        }
    }
}
