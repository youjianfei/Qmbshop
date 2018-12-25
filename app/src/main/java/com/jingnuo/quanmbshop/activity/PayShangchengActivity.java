package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interface_paySuccessOrerro;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.broadcastrReceiver.PaySuccessOrErroBroadcastReciver;
import com.jingnuo.quanmbshop.class_.WechatPay;
import com.jingnuo.quanmbshop.class_.ZhifubaoPay;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class PayShangchengActivity extends BaseActivityother {

    //控件
    TextView mEditview_amount;
    RelativeLayout mRelativelayout_wechat;
    RelativeLayout mRelayout_zhifubao;
    ImageView mImageview_weixin;
    ImageView mImageview_zhidubao;
    Button mbutton_queren;

    //数据
    int pay=1;   //1  wechat    2  zhifubao

    String neirong=""; //会员    推广币

    String amount="";//金额
    String Task_id="";
    String equipment_order_no="";//装备商城付款使用


    private IWXAPI api;


    private IntentFilter intentFilter_paysuccess;//定义广播过滤器；
    private PaySuccessOrErroBroadcastReciver paysuccess_BroadcastReciver;//定义广播监听器

    @Override
    public int setLayoutResID() {
        return R.layout.activity_pay_shangcheng;
    }

    @Override
    protected void setData() {
        intentFilter_paysuccess = new IntentFilter();
        intentFilter_paysuccess.addAction("com.jingnuo.quanmb.PAYSUCCESS_ERRO");
        paysuccess_BroadcastReciver=new PaySuccessOrErroBroadcastReciver(new Interface_paySuccessOrerro() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "payResult");
                if(respose.equals("success")){//支付成功
                    Intent intent=new Intent(PayShangchengActivity.this,PaySuccessActivity.class);
                    intent.putExtra("title","支付成功");
                    intent.putExtra("typesuccess","支付成功");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(PayShangchengActivity.this, "支付失败");
                    }
                });
            }
        });
        registerReceiver(paysuccess_BroadcastReciver, intentFilter_paysuccess); //将广播监听器和过滤器注册在一起；




        mEditview_amount.setText(amount);
        if(neirong.equals("装备商城付款")){
            Task_id=8+"";
        }
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        neirong=intent.getStringExtra("neirong");
        if(neirong.equals("装备商城付款")){
            amount=intent.getStringExtra("amount");
            equipment_order_no=intent.getStringExtra("equipment_order_no");
        }

        api = WXAPIFactory.createWXAPI(PayShangchengActivity.this, Staticdata.WechatApi);//微信支付用到
        mImageview_weixin.setSelected(true);
    }

    @Override
    protected void initListener() {
        mRelativelayout_wechat.setOnClickListener(this);
        mRelayout_zhifubao.setOnClickListener(this);
        mbutton_queren.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        mEditview_amount=findViewById(R.id.edit_rechangeAmount);
        mRelativelayout_wechat=findViewById(R.id.relayout_wechat);
        mRelayout_zhifubao=findViewById(R.id.relayout_zhifubao);
        mImageview_weixin=findViewById(R.id.image_wechat);
        mImageview_zhidubao=findViewById(R.id.image_zhifubao);
        mbutton_queren=findViewById(R.id.button_queren);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.relayout_wechat:
                mImageview_weixin.setSelected(true);
                mImageview_zhidubao.setSelected(false);
                pay=1;

                break;
            case R.id.relayout_zhifubao:
                mImageview_weixin.setSelected(false);
                mImageview_zhidubao.setSelected(true);
                pay=2;
                break;
            case R.id.button_queren:
                amount=mEditview_amount.getText()+"";
                if(pay==1){
                    //微信支付
                    Map map_pay=new HashMap();
                    if(neirong.equals("装备商城付款")){
                        map_pay.put("isrecharge","N");
                        map_pay.put("body","装备商城付款");
                        map_pay.put("equipment_order_no",equipment_order_no);
                        map_pay.put("total_fee",amount);
                        map_pay.put("client_no", Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
                        map_pay.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
                        map_pay.put("task_id",Task_id);//充值会员
                    }
                    LogUtils.LOG("ceshi",map_pay.toString(),"装备商城付款");
                    new WechatPay(PayShangchengActivity.this,api,map_pay).wepay();
                    return;
                }
                if(pay==2){
                    //支付宝支付
                    Map map_zpay=new HashMap();
                    if(neirong.equals("装备商城付款")){
                        map_zpay.put("isrecharge","N");
                        map_zpay.put("subject","装备商城付款");
                        map_zpay.put("equipment_order_no",equipment_order_no);
                        map_zpay.put("total_fee",amount);
                        map_zpay.put("client_no",Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
                        map_zpay.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
                        map_zpay.put("task_id",Task_id);
                    }
                    LogUtils.LOG("ceshi",map_zpay.toString(),"支付宝装备商城付款");
                    LogUtils.LOG("ceshi", Urls.Baseurl_hu+Urls.zhifubaoPay,"支付宝qingqiu接口");
                    new ZhifubaoPay(PayShangchengActivity.this,map_zpay).zhifubaoPay();
                    return;
                }

                break;

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(paysuccess_BroadcastReciver);
    }
}
