package com.example.administrator.myhaicar.main.mine.MyCar.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class CarNameBean {

    private List<BrandListBean> brand_list;

    public List<BrandListBean> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<BrandListBean> brand_list) {
        this.brand_list = brand_list;
    }

    public static class BrandListBean {
        /**
         * brand_id : 27
         * brand_name : 奥迪
         * car_driv : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png
         */

        private String brand_id;
        private String brand_name;
        private String car_driv;

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getCar_driv() {
            return car_driv;
        }

        public void setCar_driv(String car_driv) {
            this.car_driv = car_driv;
        }
    }
}
