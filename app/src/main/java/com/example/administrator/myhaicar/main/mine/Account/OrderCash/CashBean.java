package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class CashBean {

    private List<BondlistBean> bondlist;

    public List<BondlistBean> getBondlist() {
        return bondlist;
    }

    public void setBondlist(List<BondlistBean> bondlist) {
        this.bondlist = bondlist;
    }

    public static class BondlistBean {
        /**
         * order_id : 410
         * created : null
         * total_amount : 5
         * out_trade_no : 2017060716211707174033757223
         * refund : 未退
         * paytype : 支付宝
         * payyon : 已支付
         */

        private String order_id;
        private Object created;
        private String total_amount;
        private String out_trade_no;
        private String refund;
        private String paytype;
        private String payyon;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public Object getCreated() {
            return created;
        }

        public void setCreated(Object created) {
            this.created = created;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getRefund() {
            return refund;
        }

        public void setRefund(String refund) {
            this.refund = refund;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getPayyon() {
            return payyon;
        }

        public void setPayyon(String payyon) {
            this.payyon = payyon;
        }
    }
}
