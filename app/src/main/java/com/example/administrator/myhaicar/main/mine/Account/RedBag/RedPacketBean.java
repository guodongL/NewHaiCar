package com.example.administrator.myhaicar.main.mine.Account.RedBag;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class RedPacketBean {


    private List<RedlistinfoBean> redlistinfo;

    public List<RedlistinfoBean> getRedlistinfo() {
        return redlistinfo;
    }

    public void setRedlistinfo(List<RedlistinfoBean> redlistinfo) {
        this.redlistinfo = redlistinfo;
    }

    public static class RedlistinfoBean {
        /**
         * red_id : 283
         * money : 0
         * types : 1
         * status : 0
         */

        private String red_id;
        private String money;
        private String types;
        private String status;

        public String getRed_id() {
            return red_id;
        }

        public void setRed_id(String red_id) {
            this.red_id = red_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
