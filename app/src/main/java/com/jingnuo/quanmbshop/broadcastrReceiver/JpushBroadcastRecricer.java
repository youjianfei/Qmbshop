package com.jingnuo.quanmbshop.broadcastrReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jingnuo.quanmbshop.activity.DealActivity;
import com.jingnuo.quanmbshop.activity.ShanghuMainActivity;
import com.jingnuo.quanmbshop.activity.SystemMessageActivity;
import com.jingnuo.quanmbshop.activity.TuijianrenwuActivity;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static com.jingnuo.quanmbshop.activity.ShanghuMainActivity.shanghuMainActivity;


public class JpushBroadcastRecricer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = "";//判断跳转页面

        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        if (bundle.getString(JPushInterface.EXTRA_EXTRA) != null) {

        }
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.LOG("ceshi", "接收的广播+" + extras, "极光广播接收器");
        if (extras != null && !extras.equals("")) {
            if (shanghuMainActivity != null) {
                shanghuMainActivity.setdot();
            }
            try {
                JSONObject object = new JSONObject(extras);
                type = (String) object.get("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        LogUtils.LOG("ceshi", type, "推送1");
        if (type.equals("1")) {
            Staticdata.newmessageTYpe = "type1";
        } else if (type.equals("2")) {
            Staticdata.newmessageTYpe = "type2";
        } else if (type.equals("3")) {
            Staticdata.newmessageTYpe = "type3";
        } else if (type.equals("4")) {
            Staticdata.newmessageTYpe = "type4";
        } else if (type.equals("5")) {//预约下单提醒   属于交易提醒
            Staticdata.newmessageTYpe = "type3";
            if (shanghuMainActivity != null) {
                shanghuMainActivity.speak();
            }
        }

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (type.equals("2")) {
//                Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
//                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Intent intent_bargain = new Intent(context, BarginmessageListActivity.class);
//                intent_bargain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Intent[] intents = {mainIntent, intent_bargain};
//                context.startActivities(intents);
            } else if (type.equals("3")) {
                Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent_deal = new Intent(context, DealActivity.class);
                intent_deal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent[] intents = {mainIntent, intent_deal};
                context.startActivities(intents);
            } else if (type.equals("1")) {

                Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent_system = new Intent(context, SystemMessageActivity.class);
                intent_system.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent[] intents = {mainIntent, intent_system};
                context.startActivities(intents);
            } else if (type.equals("4")) {
                Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);
            } else if (type.equals("5")) {
                Intent mainIntent = new Intent(context, ShanghuMainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);

            }

        }
    }
}
