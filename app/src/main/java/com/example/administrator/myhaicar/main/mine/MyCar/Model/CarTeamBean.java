package com.example.administrator.myhaicar.main.mine.MyCar.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class CarTeamBean {


    private List<CarlistinfoBean> carlistinfo;

    public List<CarlistinfoBean> getCarlistinfo() {
        return carlistinfo;
    }

    public void setCarlistinfo(List<CarlistinfoBean> carlistinfo) {
        this.carlistinfo = carlistinfo;
    }

    public static class CarlistinfoBean {
        /**
         * car_srt_id : 37
         * car_name : 奥迪A6L2012款以下
         * car_color : 黑
         * car_peonum : 4
         * car_logo : http://apptest.yingqin8.com/haiche/ueditor/php/upload/image/1496985135.png
         */

        private String car_srt_id;
        private String car_name;
        private String car_color;
        private int car_peonum;
        private String car_logo;

        public String getCar_srt_id() {
            return car_srt_id;
        }

        public void setCar_srt_id(String car_srt_id) {
            this.car_srt_id = car_srt_id;
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

        public int getCar_peonum() {
            return car_peonum;
        }

        public void setCar_peonum(int car_peonum) {
            this.car_peonum = car_peonum;
        }

        public String getCar_logo() {
            return car_logo;
        }

        public void setCar_logo(String car_logo) {
            this.car_logo = car_logo;
        }
    }
}
