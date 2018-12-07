package com.jingnuo.quanmbshop.data;

import android.graphics.Bitmap;

import com.jingnuo.quanmbshop.entityclass.PopwindowGridBean;
import com.jingnuo.quanmbshop.entityclass.QueRenHelp_Bean;
import com.jingnuo.quanmbshop.entityclass.TaskDetailBean;
import com.jingnuo.quanmbshop.entityclass.UserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Staticdata {
    public static String UUID = "";
    public static String JpushID="";
    public  static String WechatApi="wx0b7afce06b6cc74d";//微信appid
    public  static String WechatApipay="40F4131427068E08451D37F02021473A";//微信支付签名密钥


    //登陆时候Rsa加密的公钥
    public static String PUBLIC_KEY_STR = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXtNyajfT+Z1+p4wLQXIzlbiSJUXG8lhS9ZeCjDHXclZa6G2xUWvliVXSPc8gAuWC1yz1OibXwspGq48kzqPoyqlNr37uBBjUArquKD/ky0BaADOeD+eHy7jnkXdshSUZAdYpIYEKuM3y+/9qSI+qkJZV/Z8xHKDLUIXYToL/AWwIDAQAB";
    //手机屏幕的宽高
    public static int ScreenHight = 0;
    public static int ScreenWidth = 0;

    //设备定位
    public static String  xValue="";
    public static String  yValue="";
    public static String  city_location="";
    public static String  aoi="";

    //用户是否已经登陆  true表示登录   flase表示未登录    待解决   退出程序后还为true
    public static boolean isLogin = false;//默认为未登录
    //用户的token
    public static String Userphonenumber = "";



    public  static UserBean static_userBean=new UserBean();

    //发布任务需要向下一页传递map  这里偷懒把map写为全局变量
    public static Map  map_task = new HashMap();
    public static List<List<String>> imagePathlist=new ArrayList();
    public static List<Bitmap> mlistdata_pic=new ArrayList<>();//展示 选择的图片得bitmap



    //向下一页传入数据，写为全局变量
    public  static Map map_wechat=new HashMap();

    //广场弹窗得选择框记录功能，这里写为静态变量
    public  static List<PopwindowGridBean.FilterBean> mData_filter_task=new ArrayList<>();

    //发布任务支付成功
    public static boolean PayissuetaskSuccess =false;

    //商家出价的价格
    public  static  String price="等待商户出价";

    //判断新消息的类型
    public  static  String  newmessageTYpe="notype";

    //是否是匹配单确认完成
    public  static boolean ispipei = false;

    public  static TaskDetailBean taskDetailBeanStatic=new TaskDetailBean();

    //排序的选择
    public  static  String  paixuname="";

}
