package com.example.administrator.myhaicar.main.mine.MyCar.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class TeamDetailBean {

    private List<CarlistinfoBean> carlistinfo;

    public List<CarlistinfoBean> getCarlistinfo() {
        return carlistinfo;
    }

    public void setCarlistinfo(List<CarlistinfoBean> carlistinfo) {
        this.carlistinfo = carlistinfo;
    }

    public static class CarlistinfoBean {
        /**
         * usr_headimg : http://yingqin8.com/appdata/upfiles/headimg/1497233903.png
         * usr_name : all
         * usr_vip : 1
         * usr_lev : 排副
         */

        private String usr_headimg;
        private String usr_name;
        private int usr_vip;
        private String usr_lev;

        public String getUsr_headimg() {
            return usr_headimg;
        }

        public void setUsr_headimg(String usr_headimg) {
            this.usr_headimg = usr_headimg;
        }

        public String getUsr_name() {
            return usr_name;
        }

        public void setUsr_name(String usr_name) {
            this.usr_name = usr_name;
        }

        public int getUsr_vip() {
            return usr_vip;
        }

        public void setUsr_vip(int usr_vip) {
            this.usr_vip = usr_vip;
        }

        public String getUsr_lev() {
            return usr_lev;
        }

        public void setUsr_lev(String usr_lev) {
            this.usr_lev = usr_lev;
        }
    }
}
