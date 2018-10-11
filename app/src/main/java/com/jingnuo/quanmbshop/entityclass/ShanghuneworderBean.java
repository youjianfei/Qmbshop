package com.jingnuo.quanmbshop.entityclass;

import java.util.List;

public class ShanghuneworderBean {

    /**
     * code : 1
     * data : [{"client_no":"90000000087","commission":5,"createDate":"2018-09-27 14:16:07","distance":61,"headUrl":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/22a2db07-26b9-4883-9a19-a09a3550152c1537437081951.png","is_helper_bid":"Y","nick_name":"缔造飘逸7440","release_Address":"升龙·汇金广场","specialty_name":"电脑维修","task_ID":4283,"task_Startdate":"2018-09-27 14:16:07","task_description":"gjfghj","user_reputation":100},{"client_no":"90000000087","commission":5,"createDate":"2018-09-27 14:16:07","distance":61,"headUrl":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/22a2db07-26b9-4883-9a19-a09a3550152c1537437081951.png","is_helper_bid":"Y","nick_name":"缔造飘逸7440","release_Address":"升龙·汇金广场","specialty_name":"电脑维修","task_ID":4283,"task_Startdate":"2018-09-27 14:16:07","task_description":"gjfghj","user_reputation":100}]
     * message : 获取列表成功
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * client_no : 90000000087
         * commission : 5.0
         * createDate : 2018-09-27 14:16:07
         * distance : 61
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

        private String client_no;
        private double commission;
        private String createDate;
        private int distance;
        private String headUrl;
        private String is_helper_bid;
        private String nick_name;
        private String release_Address;
        private String specialty_name;
        private int task_ID;
        private String task_Startdate;
        private String task_description;
        private int user_reputation;

        public String getClient_no() {
            return client_no;
        }

        public void setClient_no(String client_no) {
            this.client_no = client_no;
        }

        public double getCommission() {
            return commission;
        }

        public void setCommission(double commission) {
            this.commission = commission;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
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

        public String getRelease_Address() {
            return release_Address;
        }

        public void setRelease_Address(String release_Address) {
            this.release_Address = release_Address;
        }

        public String getSpecialty_name() {
            return specialty_name;
        }

        public void setSpecialty_name(String specialty_name) {
            this.specialty_name = specialty_name;
        }

        public int getTask_ID() {
            return task_ID;
        }

        public void setTask_ID(int task_ID) {
            this.task_ID = task_ID;
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

        public int getUser_reputation() {
            return user_reputation;
        }

        public void setUser_reputation(int user_reputation) {
            this.user_reputation = user_reputation;
        }
    }
}
