package com.jingnuo.quanmbshop.entityclass;

import java.util.List;

/**
 * Created by 飞 on 2018/12/26.
 */

public class BBSBean {


    /**
     * code : 1
     * data : [{"appBbsList":[],"business_name":"强强科技","business_no":"60000000046","commentCount":0,"content":"nihaonihao17","createdate":"2018-12-26 10:29:35","faceid":1,"fatherid":0,"forumid":1,"forwards":0,"head_url":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/6939ad43-7460-431d-a8c1-f0873adf7d5a1537958513156.png","hits":3,"iD":17,"imageid":"","img_url":"","ip":"","layer":0,"likes":0,"ordernum":0,"posted":"Y","rootid":0,"subject":""},{"appBbsList":[],"business_name":"强强科技","business_no":"60000000046","commentCount":0,"content":"nihaonihao16","createdate":"2018-12-23 10:31:48","faceid":1,"fatherid":0,"forumid":1,"forwards":0,"head_url":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/6939ad43-7460-431d-a8c1-f0873adf7d5a1537958513156.png","hits":1,"iD":16,"imageid":"","img_url":"","ip":"","layer":0,"likes":0,"ordernum":0,"posted":"Y","rootid":0,"subject":""},{"appBbsList":[],"business_name":"强强科技","business_no":"60000000046","commentCount":0,"content":"nihaonihao15","createdate":"2018-12-26 10:17:37","faceid":1,"fatherid":0,"forumid":1,"forwards":0,"head_url":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/6939ad43-7460-431d-a8c1-f0873adf7d5a1537958513156.png","hits":0,"iD":15,"imageid":"","img_url":"","ip":"","layer":0,"likes":0,"ordernum":0,"posted":"N","rootid":0,"subject":""},{"appBbsList":[],"business_name":"强强科技","business_no":"60000000046","commentCount":11,"content":"nihaonihao1","createdate":"2018-12-25 11:59:28","faceid":1,"fatherid":0,"forumid":1,"forwards":0,"head_url":"http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/6939ad43-7460-431d-a8c1-f0873adf7d5a1537958513156.png","hits":20,"iD":1,"imageid":"102,103","img_url":"https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E6%8E%A8%E5%B9%BFhot.png,https://qmb-img.oss-cn-hangzhou.aliyuncs.com/image/icon/%E7%A7%9F%E8%BD%A6hot.png,","ip":"","layer":0,"likes":0,"ordernum":0,"posted":"N","rootid":0,"subject":""}]
     * message : 操作成功
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
         * appBbsList : []
         * business_name : 强强科技
         * business_no : 60000000046
         * commentCount : 0
         * content : nihaonihao17
         * createdate : 2018-12-26 10:29:35
         * faceid : 1
         * fatherid : 0
         * forumid : 1
         * forwards : 0
         * head_url : http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/6939ad43-7460-431d-a8c1-f0873adf7d5a1537958513156.png
         * hits : 3
         * iD : 17
         * imageid :
         * img_url :
         * ip :
         * layer : 0
         * likes : 0
         * ordernum : 0
         * posted : Y
         * rootid : 0
         * subject :
         */

        private String business_name;
        private String business_no;
        private int commentCount;
        private String content;
        private String createdate;
        private int faceid;
        private int fatherid;
        private int forumid;
        private int forwards;
        private String head_url;
        private int hits;
        private int iD;
        private String imageid;
        private String img_url;
        private String ip;
        private String isLike;
        private int layer;
        private int likes;
        private int ordernum;
        private String posted;
        private int rootid;
        private String subject;
        private List<?> appBbsList;

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
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

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public int getFaceid() {
            return faceid;
        }

        public void setFaceid(int faceid) {
            this.faceid = faceid;
        }

        public int getFatherid() {
            return fatherid;
        }

        public void setFatherid(int fatherid) {
            this.fatherid = fatherid;
        }

        public int getForumid() {
            return forumid;
        }

        public void setForumid(int forumid) {
            this.forumid = forumid;
        }

        public int getForwards() {
            return forwards;
        }

        public void setForwards(int forwards) {
            this.forwards = forwards;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public int getHits() {
            return hits;
        }

        public void setHits(int hits) {
            this.hits = hits;
        }

        public int getID() {
            return iD;
        }

        public void setID(int iD) {
            this.iD = iD;
        }

        public String getImageid() {
            return imageid;
        }

        public void setImageid(String imageid) {
            this.imageid = imageid;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getLayer() {
            return layer;
        }

        public void setLayer(int layer) {
            this.layer = layer;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(int ordernum) {
            this.ordernum = ordernum;
        }

        public String getPosted() {
            return posted;
        }

        public void setPosted(String posted) {
            this.posted = posted;
        }

        public int getRootid() {
            return rootid;
        }

        public void setRootid(int rootid) {
            this.rootid = rootid;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public List<?> getAppBbsList() {
            return appBbsList;
        }

        public void setAppBbsList(List<?> appBbsList) {
            this.appBbsList = appBbsList;
        }
    }
}
