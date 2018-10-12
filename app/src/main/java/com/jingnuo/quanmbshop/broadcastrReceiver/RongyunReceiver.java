package com.jingnuo.quanmbshop.broadcastrReceiver;

import android.content.Context;
import android.content.Intent;

import com.jingnuo.quanmbshop.activity.LaunchActivity;
import com.jingnuo.quanmbshop.activity.MainActivity;
import com.jingnuo.quanmbshop.activity.ShanghuMainActivity;
import com.jingnuo.quanmbshop.activity.TuijianrenwuActivity;
import com.jingnuo.quanmbshop.utils.LogUtils;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class RongyunReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        LogUtils.LOG("rongyun",pushNotificationMessage.toString(),"融云广播接收器");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        LogUtils.LOG("rongyun",pushNotificationMessage.toString(),"点击融云广播接收器");
        Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);

        return false;
    }
}
