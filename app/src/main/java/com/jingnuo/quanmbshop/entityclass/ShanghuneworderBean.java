package com.jingnuo.quanmbshop.entityclass;

import java.util.List;

public class ShanghuneworderBean {


    /**
     * code : 1
     * data : {"list":[{"client_no":"90000000087","commission":5,"createDate":"2018-09-27 14:16:07","headUrl":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/22a2db07-26b9-4883-9a19-a09a3550152c1537437081951.png","is_helper_bid":"Y","nick_name":"缔造飘逸7440","release_Address":"升龙·汇金广场","specialty_name":"电脑维修","task_ID":4283,"task_Startdate":"2018-09-27 14:16:07","task_description":"gjfghj","user_reputation":100},{"client_no":"90000000087","commission":5,"createDate":"2018-09-27 14:16:07","headUrl":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/22a2db07-26b9-4883-9a19-a09a3550152c1537437081951.png","is_helper_bid":"Y","nick_name":"缔造飘逸7440","release_Address":"升龙·汇金广场","specialty_name":"电脑维修","task_ID":4283,"task_Startdate":"2018-09-27 14:16:07","task_description":"gjfghj","user_reputation":100}]}
     * message : 获取列表成功
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * client_no : 90000000087
             * commission : 5
             * createDate : 2018-09-27 14:16:07
             * headUrl : http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/22a2db07-26b9-4883-9a19-a09a3550152c1537437081951.png
             * is_helper_bid : Y
             * nick_name : 缔造飘逸7440
             * release_Address : 升龙·汇金广场
             * specialty_name : 电脑维修
             * task_ID : 4283
             * task_Startdate : 2018-09-27 14:16:07
             * task_description : gjfghj
             * user_reputation : 100
             */

            private String app_type;
            private String client_no;
            private int commission;
            private String createDate;
            private String headUrl;
            private String is_helper_bid;
            private String nick_name;
            private String release_address;
            private String specialty_name;
            private String task_description;
            private String order_status;
            private String task_time;
            private String order_no;
            private int task_id;
            private int task_order_id;
            private int distance;
            private double order_amount;
            private String task_Startdate;

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public double getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(double order_amount) {
                this.order_amount = order_amount;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }



            public String getApp_type() {
                return app_type;
            }

            public void setApp_type(String app_type) {
                this.app_type = app_type;
            }

            public String getClient_no() {
                return client_no;
            }

            public void setClient_no(String client_no) {
                this.client_no = client_no;
            }

            public int getCommission() {
                return commission;
            }

            public void setCommission(int commission) {
                this.commission = commission;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getIs_helper_bid() {
                return is_helper_bid;
            }

            public void setIs_helper_bid(String is_helper_bid) {
                this.is_helper_bid = is_helper_bid;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getRelease_address() {
                return release_address;
            }

            public void setRelease_address(String release_address) {
                this.release_address = release_address;
            }

            public String getSpecialty_name() {
                return specialty_name;
            }

            public void setSpecialty_name(String specialty_name) {
                this.specialty_name = specialty_name;
            }

            public int getTask_id() {
                return task_id;
            }

            public void setTask_id(int task_id) {
                this.task_id = task_id;
            }

            public int getTask_order_id() {
                return task_order_id;
            }

            public void setTask_order_id(int task_order_id) {
                this.task_order_id = task_order_id;
            }

            public String getTask_Startdate() {
                return task_Startdate;
            }

            public void setTask_Startdate(String task_Startdate) {
                this.task_Startdate = task_Startdate;
            }

            public String getTask_description() {
                return task_description;
            }

            public void setTask_description(String task_description) {
                this.task_description = task_description;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getTask_time() {
                return task_time;
            }

            public void setTask_time(String task_time) {
                this.task_time = task_time;
            }
        }
    }
}
