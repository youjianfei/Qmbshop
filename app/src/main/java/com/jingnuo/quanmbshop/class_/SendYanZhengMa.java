package com.jingnuo.quanmbshop.class_;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.Interface.SendYanZhengmaSuccess;
import com.jingnuo.quanmbshop.activity.RegisterActivity;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SendYanZhengMa {
    SendYanZhengmaSuccess  minterfaceSueecss;
    Button view;

    public SendYanZhengMa(SendYanZhengmaSuccess minterfaceSueecss,Button view) {
        this.minterfaceSueecss = minterfaceSueecss;
        this.view=view;
    }

    public void sendyanzhengma(Activity activity, Map map){
        LogUtils.LOG("ceshi","发送+"+Urls.Baseurl+Urls.sendzhuceyanzhengma,"发送验证码位置");
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
                if (status == 1) {
                    minterfaceSueecss.onSuccesses(msg);
                    //验证码倒计时
                    timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mhandler.sendEmptyMessage(0);
                        }
                    };
                    timer.schedule(timerTask, 0, 1000);
                    view.setEnabled(false);

                } else {
                    minterfaceSueecss.onSuccesses(msg);
                }

            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl+Urls.sendzhuceyanzhengma,activity,1,map);




    }
    //验证码倒计时
    int time = 60;
    Timer timer;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    view.setText( time + " s");
                    time--;
                    if (time == 0) {
                        timer.cancel();
                        timer=null;
                        view.setText("获取验证码");
                        view.setEnabled(true);
                        time = 60;
                    }
                    break;
            }
        }


    };

}
