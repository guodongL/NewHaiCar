package com.example.administrator.myhaicar.main.mine.MyCar.Model;

import java.util.List;

/**
 * Created by 果冻 on 2017/3/30.
 */

public class CarManagerBean {


    private List<CarlistinfoBean> carlistinfo;

    public List<CarlistinfoBean> getCarlistinfo() {
        return carlistinfo;
    }

    public void setCarlistinfo(List<CarlistinfoBean> carlistinfo) {
        this.carlistinfo = carlistinfo;
    }

    public static class CarlistinfoBean {
        /**
         * car_id : 400
         * car_name : 奥迪A6L2012款以下
         * car_color : 黑
         * car_number :
         * car_driv : http://apptest.yingqin8.com/haiche/app/upfiles/carimg/dr62432001497769862.png
         * car_peop : http://apptest.yingqin8.com/haiche/app/upfiles/carimg/pe62432001497769862.png
         * car_sate : 审核已通过
         * car_logo : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png
         * car_sate_info : 已通过审核，我们将为您匹配到相应车队，您可以在 我的车队 查看。
         */

        private String car_id;
        private String car_name;
        private String car_color;
        private String car_number;
        private String car_srt_id;
        private String car_driv;
        private String car_peop;
        private String car_sate;
        private String car_logo;
        private String car_sate_info;
        public String getCar_srt_id() {
            return car_srt_id;
        }

        public void setCar_srt_id(String car_srt_id) {
            this.car_srt_id = car_srt_id;
        }
        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
        }

        public String getCar_name() {
            return car_name;
        }

        public void setCar_name(String car_name) {
            this.car_name = car_name;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getCar_driv() {
            return car_driv;
        }

        public void setCar_driv(String car_driv) {
            this.car_driv = car_driv;
        }

        public String getCar_peop() {
            return car_peop;
        }

        public void setCar_peop(String car_peop) {
            this.car_peop = car_peop;
        }

        public String getCar_sate() {
            return car_sate;
        }

        public void setCar_sate(String car_sate) {
            this.car_sate = car_sate;
        }

        public String getCar_logo() {
            return car_logo;
        }

        public void setCar_logo(String car_logo) {
            this.car_logo = car_logo;
        }

        public String getCar_sate_info() {
            return car_sate_info;
        }

        public void setCar_sate_info(String car_sate_info) {
            this.car_sate_info = car_sate_info;
        }
    }
}
