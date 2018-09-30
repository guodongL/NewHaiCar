package com.example.administrator.myhaicar.main.mine.Account.Transaction;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TradingBean {


    private List<RecolistinfoBean> recolistinfo;

    public List<RecolistinfoBean> getRecolistinfo() {
        return recolistinfo;
    }

    public void setRecolistinfo(List<RecolistinfoBean> recolistinfo) {
        this.recolistinfo = recolistinfo;
    }

    public static class RecolistinfoBean {
        /**
         * reco_id : 1394
         * reco_num : 2017061314411640031574057720
         * reco_type : 活动费用
         * reco_mount : 8.00
         * reco_balance : null
         * reco_remarks : 交易成功
         * reco_payment : 余额
         * reco_created : 2017-06-13
         */

        private String reco_id;
        private String reco_num;
        private String reco_type;
        private String reco_mount;
        private Object reco_balance;
        private String reco_remarks;
        private String reco_payment;
        private String reco_created;

        public String getReco_id() {
            return reco_id;
        }

        public void setReco_id(String reco_id) {
            this.reco_id = reco_id;
        }

        public String getReco_num() {
            return reco_num;
        }

        public void setReco_num(String reco_num) {
            this.reco_num = reco_num;
        }

        public String getReco_type() {
            return reco_type;
        }

        public void setReco_type(String reco_type) {
            this.reco_type = reco_type;
        }

        public String getReco_mount() {
            return reco_mount;
        }

        public void setReco_mount(String reco_mount) {
            this.reco_mount = reco_mount;
        }

        public Object getReco_balance() {
            return reco_balance;
        }

        public void setReco_balance(Object reco_balance) {
            this.reco_balance = reco_balance;
        }

        public String getReco_remarks() {
            return reco_remarks;
        }

        public void setReco_remarks(String reco_remarks) {
            this.reco_remarks = reco_remarks;
        }

        public String getReco_payment() {
            return reco_payment;
        }

        public void setReco_payment(String reco_payment) {
            this.reco_payment = reco_payment;
        }

        public String getReco_created() {
            return reco_created;
        }

        public void setReco_created(String reco_created) {
            this.reco_created = reco_created;
        }
    }
}
