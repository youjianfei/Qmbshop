package com.jingnuo.quanmbshop.entityclass;

import java.util.List;

public class HuiyuanTaocanBean {


    /**
     * code : 1
     * data : [{"discount":"","member_id":1,"member_level":150,"months":1,"org_price":"","package_name":"一个月","price":800,"spread_b":0},{"discount":"五折","member_id":2,"member_level":500,"months":3,"org_price":"2400.00","package_name":"三个月","price":1200,"spread_b":0},{"discount":"五折","member_id":3,"member_level":1200,"months":6,"org_price":"4800.00","package_name":"六个月","price":2400,"spread_b":0},{"discount":"五折","member_id":4,"member_level":2500,"months":12,"org_price":"9600.00","package_name":"一年","price":4800,"spread_b":0}]
     * msg : 获取成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * discount :
         * member_id : 1
         * member_level : 150
         * months : 1
         * org_price :
         * package_name : 一个月
         * price : 800.0
         * spread_b : 0
         */

        private String discount;
        private int member_id;
        private int member_level;
        private int months;
        private String org_price;
        private String package_name;
        private double price;
        private int spread_b;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public int getMember_level() {
            return member_level;
        }

        public void setMember_level(int member_level) {
            this.member_level = member_level;
        }

        public int getMonths() {
            return months;
        }

        public void setMonths(int months) {
            this.months = months;
        }

        public String getOrg_price() {
            return org_price;
        }

        public void setOrg_price(String org_price) {
            this.org_price = org_price;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getSpread_b() {
            return spread_b;
        }

        public void setSpread_b(int spread_b) {
            this.spread_b = spread_b;
        }
    }
}
