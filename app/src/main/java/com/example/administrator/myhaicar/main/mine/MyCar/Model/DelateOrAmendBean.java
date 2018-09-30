package com.example.administrator.myhaicar.main.mine.MyCar.Model;

/**
 * Created by Administrator on 2017/6/21.
 */

public class DelateOrAmendBean {

    /**
     * treatedok : 0
     * treatedok_info : 该车辆有未结束订单，不能删除和修改！
     */

    private String treatedok;
    private String treatedok_info;

    public String getTreatedok() {
        return treatedok;
    }

    public void setTreatedok(String treatedok) {
        this.treatedok = treatedok;
    }

    public String getTreatedok_info() {
        return treatedok_info;
    }

    public void setTreatedok_info(String treatedok_info) {
        this.treatedok_info = treatedok_info;
    }
}
