package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.GlideLoader22;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.ZhuangbeidetailsBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZhuangbeidetailActivity extends BaseActivityother {
    //控件
    LinearLayout linearlayout_image;
    ImageView image;

    Banner banner;

    TextView textview_buy;
    TextView textview_m;
    TextView textview_l;
    TextView textview_xl;
    TextView textview_xxl;
    TextView textview_name;

    EditText edit_address;


    String address = "";
    String chicun = "";
    int product_id;
    String  equipment_order_no;

    Map  map_buy;

    ZhuangbeidetailsBean zhuangbeidetailsBean;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_zhuangbeidetail;
    }

    @Override
    protected void setData() {
        requestxiangqing();
    }

    void requestxiangqing() {//请求装备详情
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                zhuangbeidetailsBean=new Gson().fromJson(respose,ZhuangbeidetailsBean.class);
                textview_name.setText(zhuangbeidetailsBean.getData().getProduct_name());
                String imageurl[]=zhuangbeidetailsBean.getData().getImg_url().split(",");
//                Glide.with(ZhuangbeidetailActivity.this).load(imageurl.substring(0,imageurl.length()-1)).into(image);

                List<String> images = new ArrayList<>();
                for (int i = 0; i < imageurl.length; i++) {
                    images.add(imageurl[i]);
                }
                //设置图片集合
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                banner.setDelayTime( 2000*10);
                banner. stopAutoPlay();
            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui + Urls.zhuangbeilxiangqing + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
                "&product_id=" + product_id, this, 0);

    }


    @Override
    protected void initData() {
        product_id = getIntent().getIntExtra("product_id", 0);
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
        textview_name = findViewById(R.id.textview_name);
        textview_m = findViewById(R.id.textview_m);
        textview_l = findViewById(R.id.textview_l);
        textview_xl = findViewById(R.id.textview_xl);
        textview_xxl = findViewById(R.id.textview_xxl);
        textview_name = findViewById(R.id.textview_name);
        textview_buy = findViewById(R.id.textview_buy);
        linearlayout_image = findViewById(R.id.linearlayout_image);
//         image = new ImageView(this);
//        image.setImageResource(R.mipmap.shilitu);
        int w = Staticdata.ScreenWidth;
        int h = w;
        LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(w, h);
        linearlayout_image.setLayoutParams(mLayoutparams);
//        linearlayout_image.addView(image);
        banner = findViewById(R.id.banner);
        banner.setImageLoader(new GlideLoader22());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textview_m:
                selectMXL(textview_m);
                chicun = "M";
                break;
            case R.id.textview_l:
                selectMXL(textview_l);
                chicun = "L";
                break;
            case R.id.textview_xl:
                selectMXL(textview_xl);
                chicun = "XL";
                break;
            case R.id.textview_xxl:
                selectMXL(textview_xxl);
                chicun = "XXL";
                break;
            case R.id.textview_buy:
                if (chicun.equals("")) {
                    ToastUtils.showToast(this, "请选择尺码");
                    return;
                }
                address = edit_address.getText() + "";
                if (address.equals("")) {//地址判断
                    ToastUtils.showToast(this, "请填写收货地址");
                    return;
                }
//                ToastUtils.showToast(this, "付款");
                map_buy=new HashMap();
                map_buy.put("product_id",product_id+"");
                map_buy.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
                map_buy.put("address",address);
                map_buy.put("product_size",chicun);
                requsetxiadan(map_buy);
                break;
        }
    }

    void selectMXL(TextView textView) {
        textview_m.setSelected(false);
        textview_l.setSelected(false);
        textview_xl.setSelected(false);
        textview_xxl.setSelected(false);
        textView.setSelected(true);
    }
    void requsetxiadan(Map map_xiadan){
        new  Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "装备下单");
                try {
                    JSONObject object=new JSONObject(respose);
                    equipment_order_no = (String) object.get("data");//订单号
                    Intent intent=new Intent(ZhuangbeidetailActivity.this,PayShangchengActivity.class);
                    intent.putExtra("neirong","装备商城付款");
                    intent.putExtra("equipment_order_no",equipment_order_no);
                    intent.putExtra("amount",""+zhuangbeidetailsBean.getData().getProduct_price());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_cui+Urls.zhuangbeilxiadan,ZhuangbeidetailActivity.this,1,map_xiadan);

    }
}
