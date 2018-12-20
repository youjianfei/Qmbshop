package com.jingnuo.quanmbshop.entityclass;

import java.util.List;

/**
 * Created by 飞 on 2018/12/20.
 */

public class ShangchengliebiaoBean {

    /**
     * code : 1
     * data : [{"click_url":"","end_date":"2018-12-19 19:48:10","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian13.png","start_date":"2018-12-17 18:46:05"},{"click_url":"","end_date":"2018-12-19 19:48:10","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian13.png","start_date":"2018-12-17 18:46:05"},{"click_url":"","end_date":"2018-12-19 19:48:10","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian13.png","start_date":"2018-12-17 18:46:05"},{"click_url":"","end_date":"2018-12-19 19:48:10","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian12.png","start_date":"2018-12-17 18:46:05"},{"click_url":"","end_date":"2018-12-19 19:48:10","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian11.png","start_date":"2018-12-17 18:46:05"}]
     * message : 查询信息成功
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
         * click_url :
         * end_date : 2018-12-19 19:48:10
         * img_url : https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/zhoubian13.png
         * start_date : 2018-12-17 18:46:05
         */

        private String click_url;
        private String end_date;
        private String img_url;
        private String start_date;

        public String getClick_url() {
            return click_url;
        }

        public void setClick_url(String click_url) {
            this.click_url = click_url;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
    }
}
