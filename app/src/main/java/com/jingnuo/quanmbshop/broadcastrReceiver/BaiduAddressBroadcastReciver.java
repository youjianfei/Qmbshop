package com.jingnuo.quanmbshop.broadcastrReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jingnuo.quanmbshop.Interface.InterfaceBaiduAddress;
import com.jingnuo.quanmbshop.Interface.Interface_paySuccessOrerro;


/**
 * Created by 00 on 2017/1/3.
 */

public class BaiduAddressBroadcastReciver extends BroadcastReceiver {

   private InterfaceBaiduAddress interfaceBaiduAddress;

    public BaiduAddressBroadcastReciver(InterfaceBaiduAddress interfaceBaiduAddress) {
        this.interfaceBaiduAddress = interfaceBaiduAddress;
    }

    @Override
        public void onReceive(Context context, Intent intent) {
        interfaceBaiduAddress.onResult(intent.getStringExtra("address"));
    }

}
