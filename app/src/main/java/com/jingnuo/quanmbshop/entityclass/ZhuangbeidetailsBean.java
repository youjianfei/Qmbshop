package com.jingnuo.quanmbshop.entityclass;

/**
 * Created by 飞 on 2018/12/21.
 */

public class ZhuangbeidetailsBean {

    /**
     * code : 1
     * data : {"description":"","img_id":"1,2,3","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E7%81%B0.png,https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E8%93%9D.png,https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E7%BA%A2.png,","pay_type":"","product_name":"","product_price":0}
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
         * description :
         * img_id : 1,2,3
         * img_url : https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E7%81%B0.png,https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E8%93%9D.png,https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E7%BA%A2.png,
         * pay_type :
         * product_name :
         * product_price : 0.0
         */

        private String description;
        private String img_id;
        private String img_url;
        private String pay_type;
        private String product_name;
        private double product_price;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImg_id() {
            return img_id;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public double getProduct_price() {
            return product_price;
        }

        public void setProduct_price(double product_price) {
            this.product_price = product_price;
        }
    }
}
