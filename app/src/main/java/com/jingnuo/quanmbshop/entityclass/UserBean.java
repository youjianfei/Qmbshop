package com.jingnuo.quanmbshop.entityclass;

/**
 * Created by Administrator on 2018/3/30.
 */

public class UserBean {


    /**
     * code : 1
     * data : {"appuser":{"avatarUrl":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/avatar/business.png","business_address":"13","business_avatar":8,"business_id":47,"business_level":20,"business_license":"2543","business_mobile_no":"18539931923","business_name":"挖矿大师","business_no":"60000000040","business_reputation":136,"business_type_id":"1203,1204,1205,1206,","business_x_value":"34.800276","business_y_value":"113.631177","client_no":"90000000002","commission":906,"createDate":"2018-09-12 20:21:21","emp_no":"1010","legal_person":"11221212121","legal_person_cer_no":"411325199509259148","member_level":0,"orderSum":4,"organization_name":"121121212","push_on_off":"Y","rongCloud_token":"SbXhU4d0n4HAR6/MOJP9bwqH3PJ3sG9zVpUzO7BUVXekuBOiwYIUb4TB8FL2K+qBjN57IiH8+JCh7SqO8VhPEWcfbR6FH0k+","social_credit_code":"999999999","spread_b":150,"status":"0","updateDate":"2018-10-15 14:10:44","user_token":"b83c8024b9a8d99b74aa49a60eed6429"}}
     * message : 登录成功
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
         * appuser : {"avatarUrl":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/avatar/business.png","business_address":"13","business_avatar":8,"business_id":47,"business_level":20,"business_license":"2543","business_mobile_no":"18539931923","business_name":"挖矿大师","business_no":"60000000040","business_reputation":136,"business_type_id":"1203,1204,1205,1206,","business_x_value":"34.800276","business_y_value":"113.631177","client_no":"90000000002","commission":906,"createDate":"2018-09-12 20:21:21","emp_no":"1010","legal_person":"11221212121","legal_person_cer_no":"411325199509259148","member_level":0,"orderSum":4,"organization_name":"121121212","push_on_off":"Y","rongCloud_token":"SbXhU4d0n4HAR6/MOJP9bwqH3PJ3sG9zVpUzO7BUVXekuBOiwYIUb4TB8FL2K+qBjN57IiH8+JCh7SqO8VhPEWcfbR6FH0k+","social_credit_code":"999999999","spread_b":150,"status":"0","updateDate":"2018-10-15 14:10:44","user_token":"b83c8024b9a8d99b74aa49a60eed6429"}
         */

        private AppuserBean appuser;

        public AppuserBean getAppuser() {
            return appuser;
        }

        public void setAppuser(AppuserBean appuser) {
            this.appuser = appuser;
        }

        public static class AppuserBean {
            /**
             * avatarUrl : https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/avatar/business.png
             * business_address : 13
             * business_avatar : 8
             * business_id : 47
             * business_level : 20
             * business_license : 2543
             * business_mobile_no : 18539931923
             * business_name : 挖矿大师
             * business_no : 60000000040
             * business_reputation : 136
             * business_type_id : 1203,1204,1205,1206,
             * business_x_value : 34.800276
             * business_y_value : 113.631177
             * client_no : 90000000002
             * commission : 906.0
             * createDate : 2018-09-12 20:21:21
             * emp_no : 1010
             * legal_person : 11221212121
             * legal_person_cer_no : 411325199509259148
             * member_level : 0
             * orderSum : 4
             * organization_name : 121121212
             * push_on_off : Y
             * rongCloud_token : SbXhU4d0n4HAR6/MOJP9bwqH3PJ3sG9zVpUzO7BUVXekuBOiwYIUb4TB8FL2K+qBjN57IiH8+JCh7SqO8VhPEWcfbR6FH0k+
             * social_credit_code : 999999999
             * spread_b : 150
             * status : 0
             * updateDate : 2018-10-15 14:10:44
             * user_token : b83c8024b9a8d99b74aa49a60eed6429
             */

            private String avatarUrl;
            private String business_address;
            private int business_avatar;
            private int business_id;
            private int business_level;
            private String business_license;
            private String business_mobile_no;
            private String business_name;
            private String business_no;
            private int business_reputation;
            private String business_type_id;
            private String business_x_value;
            private String business_y_value;
            private String client_no;
            private double commission;
            private String createDate;
            private String emp_no;
            private String legal_person;
            private String legal_person_cer_no;
            private int member_level;
            private int orderSum;
            private String organization_name;
            private String push_on_off;
            private String rongCloud_token;
            private String social_credit_code;
            private int spread_b;
            private String status;
            private String updateDate;
            private String user_token;
            private String security_code;
            private String user_name;
            private String is_business;

            public String getIs_business() {
                return is_business;
            }

            public void setIs_business(String is_business) {
                this.is_business = is_business;
            }

            public String getSecurity_code() {
                return security_code;
            }

            public void setSecurity_code(String security_code) {
                this.security_code = security_code;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getBusiness_address() {
                return business_address;
            }

            public void setBusiness_address(String business_address) {
                this.business_address = business_address;
            }

            public int getBusiness_avatar() {
                return business_avatar;
            }

            public void setBusiness_avatar(int business_avatar) {
                this.business_avatar = business_avatar;
            }

            public int getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(int business_id) {
                this.business_id = business_id;
            }

            public int getBusiness_level() {
                return business_level;
            }

            public void setBusiness_level(int business_level) {
                this.business_level = business_level;
            }

            public String getBusiness_license() {
                return business_license;
            }

            public void setBusiness_license(String business_license) {
                this.business_license = business_license;
            }

            public String getBusiness_mobile_no() {
                return business_mobile_no;
            }

            public void setBusiness_mobile_no(String business_mobile_no) {
                this.business_mobile_no = business_mobile_no;
            }

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public String getBusiness_no() {
                return business_no;
            }

            public void setBusiness_no(String business_no) {
                this.business_no = business_no;
            }

            public int getBusiness_reputation() {
                return business_reputation;
            }

            public void setBusiness_reputation(int business_reputation) {
                this.business_reputation = business_reputation;
            }

            public String getBusiness_type_id() {
                return business_type_id;
            }

            public void setBusiness_type_id(String business_type_id) {
                this.business_type_id = business_type_id;
            }

            public String getBusiness_x_value() {
                return business_x_value;
            }

            public void setBusiness_x_value(String business_x_value) {
                this.business_x_value = business_x_value;
            }

            public String getBusiness_y_value() {
                return business_y_value;
            }

            public void setBusiness_y_value(String business_y_value) {
                this.business_y_value = business_y_value;
            }

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

            public String getEmp_no() {
                return emp_no;
            }

            public void setEmp_no(String emp_no) {
                this.emp_no = emp_no;
            }

            public String getLegal_person() {
                return legal_person;
            }

            public void setLegal_person(String legal_person) {
                this.legal_person = legal_person;
            }

            public String getLegal_person_cer_no() {
                return legal_person_cer_no;
            }

            public void setLegal_person_cer_no(String legal_person_cer_no) {
                this.legal_person_cer_no = legal_person_cer_no;
            }

            public int getMember_level() {
                return member_level;
            }

            public void setMember_level(int member_level) {
                this.member_level = member_level;
            }

            public int getOrderSum() {
                return orderSum;
            }

            public void setOrderSum(int orderSum) {
                this.orderSum = orderSum;
            }

            public String getOrganization_name() {
                return organization_name;
            }

            public void setOrganization_name(String organization_name) {
                this.organization_name = organization_name;
            }

            public String getPush_on_off() {
                return push_on_off;
            }

            public void setPush_on_off(String push_on_off) {
                this.push_on_off = push_on_off;
            }

            public String getRongCloud_token() {
                return rongCloud_token;
            }

            public void setRongCloud_token(String rongCloud_token) {
                this.rongCloud_token = rongCloud_token;
            }

            public String getSocial_credit_code() {
                return social_credit_code;
            }

            public void setSocial_credit_code(String social_credit_code) {
                this.social_credit_code = social_credit_code;
            }

            public int getSpread_b() {
                return spread_b;
            }

            public void setSpread_b(int spread_b) {
                this.spread_b = spread_b;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getUser_token() {
                return user_token;
            }

            public void setUser_token(String user_token) {
                this.user_token = user_token;
            }
        }
    }
}
