package com.jingnuo.quanmbshop.class_;

import com.jingnuo.quanmbshop.utils.LogUtils;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

import static com.jingnuo.quanmbshop.activity.MainActivity.mainActivity;

public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {

        LogUtils.LOG("rongyun",message+""+i,"rongyun接收器");
        if (mainActivity != null) {
            mainActivity.setdot();
            LogUtils.LOG("rongyun",message+""+i,"rongyun接收器hongdian");
        }
        return false;
    }
}
