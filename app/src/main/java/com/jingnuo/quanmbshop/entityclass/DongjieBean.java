package com.jingnuo.quanmbshop.entityclass;

/**
 * Created by 飞 on 2018/12/20.
 */

public class DongjieBean {

    /**
     * code : 1
     * data : {"day":4,"lv":4,"money":50,"txt":"您豆腐干豆腐干相关问题,"}
     * message : 查询信息成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * day : 4
         * lv : 4
         * money : 50.0
         * txt : 您豆腐干豆腐干相关问题,
         */

        private int day;
        private int lv;
        private double money;
        private String txt;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getLv() {
            return lv;
        }

        public void setLv(int lv) {
            this.lv = lv;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }
}
