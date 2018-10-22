package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.UserBean;
import com.jingnuo.quanmbshop.utils.InstalltionId;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.PasswordJiami;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

import static com.jingnuo.quanmbshop.data.Staticdata.ScreenWidth;
import static com.jingnuo.quanmbshop.data.Staticdata.UUID;
import static com.jingnuo.quanmbshop.data.Staticdata.Userphonenumber;
import static com.jingnuo.quanmbshop.data.Staticdata.isLogin;


public class LaunchActivity extends BaseActivityother {
    //布局
    LinearLayout mLinearlauout_root;
    //数据
    int Screenhight = 0;
    int Screenwidth = 0;

    String phonenumber = "";
    String password = "";
    String publicEncryptedResult = "";//加密的密码


    UserBean userBean;

    boolean isFirstlogin = true;


    @Override
    public int setLayoutResID() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_launch;
    }

    @Override
    protected void setData() {
        phonenumber = SharedPreferencesUtils.getString(LaunchActivity.this, "QMB", "phonenumber");
        password = SharedPreferencesUtils.getString(LaunchActivity.this, "QMB", "password");

        if (phonenumber.equals("") || password.equals("")) {
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    mhandler.sendEmptyMessage(0);
                }
            };
            mTimer.schedule(timerTask, 1500);
        } else {
            Map map_autoLogind = new HashMap();
            publicEncryptedResult = PasswordJiami.passwordjiami(password);//对密码加密
            LogUtils.LOG("ceshi", publicEncryptedResult + "1111111111", "fragment_account");
            Userphonenumber = phonenumber;//将电话号设为全局变量
            map_autoLogind.put("username", phonenumber);
            map_autoLogind.put("password", publicEncryptedResult);
            map_autoLogind.put("Jpush_id", Staticdata.JpushID);
            map_autoLogind.put("uuid", UUID);
            //登陆成功后 设置全局变量islogin为 true
            request(map_autoLogind);

        }

//        mTimer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mhandler.sendEmptyMessage(0);
//            }
//        };
//        mTimer.schedule(timerTask, 1500);
    }

    @Override
    protected void initData() {
        Staticdata.JpushID= JPushInterface.getRegistrationID(getApplicationContext());
        UUID = InstalltionId.id(this);//第一次运行生成一个id
        isFirstlogin = SharedPreferencesUtils.getBoolean(this, "QMB", "isfirstlogin");
        Screenhight = SizeUtils.getScreenHeightPx(this);
        Screenwidth = SizeUtils.getScreenWidthPx(this);
        Staticdata.ScreenHight = Screenhight;
        Staticdata.ScreenWidth = Screenwidth;
        ImageView image = new ImageView(this);
        image.setBackgroundResource(R.mipmap.launchpic);
        LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(Staticdata.ScreenWidth, (int) (Staticdata.ScreenWidth * 1.77));
        image.setLayoutParams(mLayoutparams);
        mLinearlauout_root.addView(image);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mLinearlauout_root = findViewById(R.id.Linearlayout_launch);

    }

    void request(Map map) {
        LogUtils.LOG("ceshi", "登录MAP+" + map, "fragment_account");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {

                int status = 0;
                String msg = "";
                try {
                    JSONObject object = new JSONObject(respose);
                    status = (Integer) object.get("code");//登录状态
                    msg = (String) object.get("message");//登录返回信息
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {//登录成功
                    userBean = new Gson().fromJson(respose, UserBean.class);
                    Staticdata.static_userBean = userBean;
                    LogUtils.LOG("ceshi", userBean.getData().getAppuser().getUser_token(), "fragment_account");
                    isLogin = true;
                    Utils.connect(userBean.getData().getAppuser().getRongCloud_token());
                    Userphonenumber = userBean.getData().getAppuser().getBusiness_mobile_no();//将电话号设为全局变量
                    mTimer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mhandler.sendEmptyMessage(1);
                        }
                    };
                    mTimer.schedule(timerTask, 1000);
                } else {
                    Intent intent_ = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent_);
                    finish();
                }
            }

            @Override
            public void onError(int error) {
                Intent intent_ = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent_);
                finish();

            }
        }).postHttp(Urls.Baseurl + Urls.login, LaunchActivity.this, 1, map);


    }

    Timer mTimer;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mTimer.cancel();
//                    if (isFirstLogin) {
//                        Intent intent = new Intent(LaunchActivity.this, FirstLoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                    LogUtils.LOG("ceshi", "跳转主页", "main");
                    if (isFirstlogin) {
                        Intent intent = new Intent(LaunchActivity.this, FirstLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
                case 1:
                    if(userBean.getData().getAppuser().getIs_business().equals("N")){
                        new Volley_Utils(new Interface_volley_respose() {
                            @Override
                            public void onSuccesses(String respose) {

                                int status = 0;
                                String msg = "";
                                String state = "";
                                try {
                                    JSONObject object = new JSONObject(respose);
                                    status = (Integer) object.get("code");//
                                    msg = (String) object.get("message");//
                                    state = (String) object.get("status");//
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (state.equals("00")) {//审核通过
                                    Intent intent_shopcenter = new Intent(LaunchActivity.this, ShopCenterNewActivity.class);
                                    intent_shopcenter.putExtra("type", 2);//2  商户
                                    startActivity(intent_shopcenter);
                                    finish();
                                } else if (state.equals("01")) {//没提交
                                    Intent intent_shopin = new Intent(LaunchActivity.this, ShopInActivity.class);
                                    startActivity(intent_shopin);
                                    finish();

                                } else if (state.equals("02")) {//正在审核
//                            Intent intent_shopinext=new Intent(getActivity(), ShopInNextActivity.class);
//                            getActivity().startActivity(intent_shopinext);
                                    Intent intent_submit = new Intent(LaunchActivity.this, SubmitSuccessActivity.class);
                                    intent_submit.putExtra("state", "2");
                                    startActivity(intent_submit);
                                    finish();

                                } else if (state.equals("03")) {//没提交审核
                                    Intent intent_shopin = new Intent(LaunchActivity.this, ShopInActivity.class);
                                    startActivity(intent_shopin);
                                    finish();
                                }
                            }

                            @Override
                            public void onError(int error) {

                            }
                        }).Http(Urls.Baseurl + Urls.shopIn_state + Staticdata.static_userBean.getData().getAppuser().getUser_token(), LaunchActivity.this, 0);

                    }else {
                        Intent intent_login = new Intent(LaunchActivity.this, ShanghuMainActivity.class);
                        startActivity(intent_login);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
