package com.jingnuo.quanmbshop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.popwinow.Popwindow_TuiBaozhengjin;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaozhengjinActivity extends BaseActivityother {
    TextView text_money;
    TextView textview_isjiaona;
    Button    button_rechange;
    Button    button_tuibaozhnegjin;


    Map map_lookBaozhengjin;
    //数据
     int isjiaona;
     String money="";
     String needmoney="";
     String baozhengjinmsg="";


    @Override
    public int setLayoutResID() {
        return R.layout.activity_baozhengjin;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        map_lookBaozhengjin=new HashMap();
        map_lookBaozhengjin.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
        map_lookBaozhengjin.put("business_no", Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
        request(map_lookBaozhengjin);
    }

    @Override
    protected void initListener() {
        button_tuibaozhnegjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isjiaona==0){
                    ToastUtils.showToast(BaozhengjinActivity.this,"未缴纳保证金");
                }else {
                    new   Popwindow_TuiBaozhengjin(BaozhengjinActivity.this, new Interence_complteTask() {
                        @Override
                        public void onResult(boolean result) {
                            if(result){
                                requestTui(map_lookBaozhengjin);
                            }
                        }
                    }).showPopwindow();
                }
            }
        });
        button_rechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isjiaona==0){
                    Intent intent=new Intent(BaozhengjinActivity.this,PayBaozhengmoneyActivity.class);
                    intent.putExtra("money",needmoney);
                    startActivity(intent);
                }else {
                    ToastUtils.showToast(BaozhengjinActivity.this,"已缴纳保证金");
                }

            }
        });
    }

    @Override
    protected void initView() {
        text_money=findViewById(R.id.text_money);
        textview_isjiaona=findViewById(R.id.textview_isjiaona);
        button_rechange=findViewById(R.id.button_rechange);
        button_tuibaozhnegjin=findViewById(R.id.button_tuibaozhnegjin);
    }
    void request(final Map map){//请求保证金详情
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("cehsi","保证金"+respose,"保证金");
                try {
                    JSONObject object=new JSONObject(respose);
                    isjiaona = (Integer) object.get("code");//是否缴纳
                    money = (String) object.get("ensure_money");//缴纳显示金额
                    baozhengjinmsg = (String) object.get("msg");//提示
                    needmoney = (String) object.get("ensure_money_business");//需要缴纳的金额

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                text_money.setText(money);
                LogUtils.LOG("ceshi",baozhengjinmsg,"baozhengjin");
                textview_isjiaona.setText(baozhengjinmsg);
                if(isjiaona==1){
                    text_money.setTextColor(BaozhengjinActivity.this.getResources().getColor(R.color.green3));
                    textview_isjiaona.setTextColor(BaozhengjinActivity.this.getResources().getColor(R.color.green3));
                }else {
                    text_money.setTextColor(BaozhengjinActivity.this.getResources().getColor(R.color.red));
                    textview_isjiaona.setTextColor(BaozhengjinActivity.this.getResources().getColor(R.color.red));

                }
            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_hu+Urls.lookBaozhengjin,this,1,map);

    }
    void requestTui(Map map){
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("cehsi","退还保证金"+respose,"保证金");

                int  stata=0;
                String message="";
                try {
                    JSONObject object=new JSONObject(respose);
                    stata = (Integer) object.get("code");//是否成功
                    message = (String) object.get("msg");//缴纳显示金额
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(stata==1){
                    Intent intent=new Intent(BaozhengjinActivity.this,BaozhengjinTuikuanActivity.class);
                    intent.putExtra("money",money);
                    startActivity(intent);
                }else {
                    ToastUtils.showToast(BaozhengjinActivity.this,message);
                }


            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_hu+Urls.TuiBaozhengjin,this,1,map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        request(map_lookBaozhengjin);
    }
}
