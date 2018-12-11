package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.ShareGoodWeb;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.HelpterInfoBean;
import com.jingnuo.quanmbshop.entityclass.ShopcenterBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_helperfirst;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopCenterNewActivity extends BaseActivityother {

    //控件
    ImageView mImageview_setting;
    CircleImageView iamgeview_shopheadpic;
    TextView textview_shopname;
    TextView textview_shoptype;
    TextView textview_guize;
    TextView textview_jiaoxue;
    TextView textview_huiyua;
    ImageView huiyuanyes;
    LinearLayout linearlayout_jineng;
    LinearLayout linearlayout_qianbao;
    LinearLayout linearlayout_tuiguangbi;
    LinearLayout linearlayout_huanyuan;
    LinearLayout linearlayout_kefu;
    LinearLayout linearlayout_jiaoxue;
    LinearLayout linearlayout_fenxiang;

    //对象

    ShareGoodWeb shareClass;//分享
    ShopcenterBean shopcenterBean;//商户


    //数据
    int type = 2;  //1 帮手  2  商户

    boolean isfirst = true;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_shop_center_new;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        shareClass = new ShareGoodWeb(this);
        request();
    }

    @Override
    protected void initListener() {
        mImageview_setting.setOnClickListener(this);
        iamgeview_shopheadpic.setOnClickListener(this);
        linearlayout_jineng.setOnClickListener(this);
        linearlayout_qianbao.setOnClickListener(this);
        linearlayout_tuiguangbi.setOnClickListener(this);
        linearlayout_huanyuan.setOnClickListener(this);
        linearlayout_kefu.setOnClickListener(this);
        linearlayout_jiaoxue.setOnClickListener(this);
        linearlayout_fenxiang.setOnClickListener(this);
        textview_guize.setOnClickListener(this);
        textview_jiaoxue.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        huiyuanyes = findViewById(R.id.image_huiyuan);
        mImageview_setting = findViewById(R.id.iv_setting);
        iamgeview_shopheadpic = findViewById(R.id.iamgeview_shopheadpic);
        textview_shopname = findViewById(R.id.textview_shopname);
        textview_shoptype = findViewById(R.id.textview_shoptype);
        linearlayout_jineng = findViewById(R.id.linearlayout_jineng);
        linearlayout_qianbao = findViewById(R.id.linearlayout_qianbao);
        linearlayout_tuiguangbi = findViewById(R.id.linearlayout_tuiguangbi);
        linearlayout_huanyuan = findViewById(R.id.linearlayout_huanyuan);
        linearlayout_kefu = findViewById(R.id.linearlayout_kefu);
        linearlayout_jiaoxue = findViewById(R.id.linearlayout_jiaoxue);
        linearlayout_fenxiang = findViewById(R.id.linearlayout_fenxiang);
        textview_guize = findViewById(R.id.textview_guize);
        textview_jiaoxue = findViewById(R.id.textview_jiaoxue);
        textview_huiyua = findViewById(R.id.textview_huiyua);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.iv_setting:
                Intent intent_setting = new Intent(this, SettingActivity.class);
                startActivity(intent_setting);
                finish();
                break;
            case R.id.iamgeview_shopheadpic:
                Intent intent_shopcenterinfo = new Intent(this, ShopCenterInfoActivity.class);
                startActivity(intent_shopcenterinfo);
                break;
            case R.id.linearlayout_jineng://技能
                Intent intent_myorder = new Intent(this, MySkillActivity.class);
                intent_myorder.putExtra("type", type);
                startActivity(intent_myorder);
                break;
            case R.id.linearlayout_qianbao://钱包
                Intent intent_wa = new Intent(ShopCenterNewActivity.this, WalletActivity.class);
                intent_wa.putExtra("money", Staticdata.static_userBean.getData().getAppuser().getCommission() + "");
                startActivity(intent_wa);
                break;
            case R.id.linearlayout_tuiguangbi://保证金
//                Intent intent_mytuiguangbi = new Intent(ShopCenterNewActivity.this, TuiguangbiWalletActivity.class);
//                intent_mytuiguangbi.putExtra("type", type);
//                intent_mytuiguangbi.putExtra("tuiguangbi", shopcenterBean.getData().getList().getSpread_b()+"");
//                startActivity(intent_mytuiguangbi);
                Intent intent_baozhngjin = new Intent(ShopCenterNewActivity.this, BaozhengjinActivity.class);
                startActivity(intent_baozhngjin);

                break;
            case R.id.linearlayout_huanyuan://会员
                Intent intent_huiyuan = new Intent(ShopCenterNewActivity.this, HuiyuanRechargeActivity.class);
                startActivity(intent_huiyuan);
                break;
            case R.id.linearlayout_kefu://客服
                Intent intent_kefuzhongxin = new Intent(this, ZixunKefuWebActivity.class);
                startActivity(intent_kefuzhongxin);
                break;
            case R.id.linearlayout_jiaoxue://教学
                Intent intent_shopcenter = new Intent(this, HelperguizeActivity.class);
                intent_shopcenter.putExtra("title", "商家教学");
                startActivity(intent_shopcenter);
                break;
            case R.id.textview_jiaoxue://教学
                Intent intent_shopcenter2 = new Intent(this, HelperguizeActivity.class);
                intent_shopcenter2.putExtra("title", "商家教学");
                startActivity(intent_shopcenter2);
                break;
            case R.id.textview_guize:
                Intent intent_shangjiaguize = new Intent(this, HelperguizeActivity.class);
                intent_shangjiaguize.putExtra("title", "商家规则");
                startActivity(intent_shangjiaguize);
                break;
            case R.id.linearlayout_fenxiang://分享
                if (Utils.isWxInstall(this)) {
                    shareClass.shareapp();
                } else {
                    ToastUtils.showToast(this, "未安装微信");
                }
                break;
        }
    }


    void request() {
        String url_info = Urls.Baseurl + Urls.shopcenter + Staticdata.static_userBean.getData()
                        .getAppuser().getUser_token() + "&business_no=" + Staticdata.static_userBean.getData().getAppuser()
                        .getBusiness_no();
        LogUtils.LOG("ceshi", " 商户网址：" + url_info, "ShopCenterActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "商户中心：" + respose, "ShopCenterActivity");
                {
                    isfirst = SharedPreferencesUtils.getBoolean(ShopCenterNewActivity.this, "QMB", "shanghu");
                    if (isfirst) {
                        new Popwindow_helperfirst(ShopCenterNewActivity.this, 2, 1.17).showpop();
                    }
                    shopcenterBean = new Gson().fromJson(respose, ShopcenterBean.class);
                    Glide.with(ShopCenterNewActivity.this).load(shopcenterBean.getData().getList().getAvatar_url()).into(iamgeview_shopheadpic);
                    textview_shopname.setText(shopcenterBean.getData().getList().getBusiness_name());
                    textview_shoptype.setText(shopcenterBean.getData().getList().getBusiness_type_id());
                    Staticdata.static_userBean.getData().getAppuser().setCommission(Double.parseDouble(shopcenterBean.getData().getList().getCommission()));
                    if(shopcenterBean.getData().getList().getMemberImgUrl()==null){
                    textview_huiyua.setVisibility(View.VISIBLE);
                }else {
                        textview_huiyua.setVisibility(View.GONE);
                    }
                    Glide.with(ShopCenterNewActivity.this).load(shopcenterBean.getData().getList().getIconImgUrl()).into(huiyuanyes);
                }

            }

            @Override
            public void onError(int error) {

            }
        }).Http(url_info, ShopCenterNewActivity.this, 0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        request();
    }
}
