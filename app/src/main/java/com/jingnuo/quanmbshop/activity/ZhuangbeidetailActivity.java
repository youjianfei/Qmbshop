package com.jingnuo.quanmbshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.ToastUtils;

public class ZhuangbeidetailActivity extends BaseActivityother {
    //控件
    LinearLayout linearlayout_image;
    TextView textview_buy;
    TextView textview_name;
    TextView textview_m;
    TextView textview_l;
    TextView textview_xl;
    TextView textview_xxl;

    EditText edit_address;



    String  address="";
    String  chicun="";


    @Override
    public int setLayoutResID() {
        return R.layout.activity_zhuangbeidetail;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        textview_m.setOnClickListener(this);
        textview_l.setOnClickListener(this);
        textview_xl.setOnClickListener(this);
        textview_xxl.setOnClickListener(this);
        textview_buy.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        edit_address = findViewById(R.id.edit_address);
        textview_m = findViewById(R.id.textview_m);
        textview_l = findViewById(R.id.textview_l);
        textview_xl = findViewById(R.id.textview_xl);
        textview_xxl = findViewById(R.id.textview_xxl);
        textview_name = findViewById(R.id.textview_name);
        textview_buy = findViewById(R.id.textview_buy);
        linearlayout_image = findViewById(R.id.linearlayout_image);
        ImageView image = new ImageView(this);
//        Glide.with(this).load("url").into(image);
        image.setImageResource(R.mipmap.shilitu);
        int w = Staticdata.ScreenWidth;
        int h = w;
        LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(w, h);
        image.setLayoutParams(mLayoutparams);
        linearlayout_image.addView(image);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textview_m:
                selectMXL(textview_m);
                chicun="m";
                break;
            case R.id.textview_l:
                selectMXL(textview_l);
                chicun="l";
                break;
            case R.id.textview_xl:
                selectMXL(textview_xl);
                chicun="xl";
                break;
            case R.id.textview_xxl:
                selectMXL(textview_xxl);
                chicun="xxl";
                break;
            case R.id.textview_buy:
                if(chicun.equals("")){
                    ToastUtils.showToast(this,"请选择尺码");
                    return;
                }
                address=edit_address.getText()+"";
                if(address.equals("")){//地址判断
                    ToastUtils.showToast(this,"请填写收货地址");
                    return;
                }

                ToastUtils.showToast(this,"付款");
                break;
        }
    }

    void selectMXL(TextView textView){
        textview_m.setSelected(false);
        textview_l.setSelected(false);
        textview_xl.setSelected(false);
        textview_xxl.setSelected(false);
        textView.setSelected(true);
    }
}
