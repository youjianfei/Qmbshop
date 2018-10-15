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

    boolean isfirst=true;

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
    }

    @Override
    protected void initView() {
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

                break;
            case R.id.linearlayout_tuiguangbi://推广币


                break;
            case R.id.linearlayout_huanyuan://会员

                break;
            case R.id.linearlayout_kefu://客服
                Intent intent_kefuzhongxin = new Intent(this, ZixunKefuWebActivity.class);
                startActivity(intent_kefuzhongxin);
                break;
            case R.id.linearlayout_jiaoxue://教学
                Intent intent_shopcenter = new Intent(this, HelperguizeActivity.class);
                intent_shopcenter.putExtra("title", "接单规则");
                startActivity(intent_shopcenter);
                break;
            case R.id.linearlayout_fenxiang://分享  todo 微信分享的内容网址是否需要更改
                if (Utils.isWxInstall(this)) {
                    shareClass.shareapp();
                } else {
                    ToastUtils.showToast(this, "未安装微信");
                }
                break;
        }
    }


    void request() {
        String url_info = type == 1 ? Urls.Baseurl + Urls.helperInfo + Staticdata.static_userBean.getData()
                .getAppuser().getUser_token() + "&client_no=" + Staticdata.static_userBean.getData().getAppuser()
                .getClient_no()
                :
                Urls.Baseurl + Urls.shopcenter + Staticdata.static_userBean.getData()
                        .getAppuser().getUser_token() + "&client_no=" + Staticdata.static_userBean.getData().getAppuser()
                        .getClient_no();
        LogUtils.LOG("ceshi", "帮手 商户网址：" + url_info, "ShopCenterActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "商户中心：" + respose, "ShopCenterActivity");
                if (type == 1) {
//                    isfirst= SharedPreferencesUtils.getBoolean(ShopCenterActivity.this, "QMB", "bangshou");
//                    if(isfirst){
//                        new Popwindow_helperfirst(ShopCenterActivity.this,1,1.17).showpop();
//                    }
//
//                    helpterInfoBean = new Gson().fromJson(respose, HelpterInfoBean.class);
//                    if(helpterInfoBean.getData().getList().getMemberImgUrl()==null||helpterInfoBean.getData().getList().getMemberImgUrl().equals("")){
//                        mImageview_vip.setVisibility(View.GONE);
//                    }else {
//                        mImageview_vip.setVisibility(View.VISIBLE);
//                        Glide.with(ShopCenterActivity.this).load(helpterInfoBean.getData().getList().getMemberImgUrl()).error(R.mipmap.vip1).into(mImageview_vip);
//                    }
//                    Glide.with(ShopCenterActivity.this).load(helpterInfoBean.getData().getList().getAvatar_url()).into(imageview_head);
//                    Glide.with(ShopCenterActivity.this).load(helpterInfoBean.getData().getList().getIconImgUrl()).error(R.mipmap.lv1).into(mImageview_lv);
//                    mTextview_name.setText(helpterInfoBean.getData().getList().getHelper_name());
////                    mTextview_level.setText(helpterInfoBean.getData().getList().geth());
//                    mTextview_text_tui_count.setText(helpterInfoBean.getData().getList().getSpread_b()+"个");
//                    if(helpterInfoBean.getData().getList().getMember_enddate()!=null){
//                        mTextview_text_huiyuan.setText(helpterInfoBean.getData().getList().getMember_enddate().substring(0,10)+"到期");
//                    }
////                    mTextview_namenext.setVisibility(View.GONE);
//                    mTextview_money.setText(helpterInfoBean.getData().getList().getCommission()+"");
//                    setstar((float) helpterInfoBean.getData().getList().getEvaluation_star());
                } else {
                    isfirst= SharedPreferencesUtils.getBoolean(ShopCenterNewActivity.this, "QMB", "shanghu");
                    if (isfirst){
                        new Popwindow_helperfirst (ShopCenterNewActivity.this,2,1.17).showpop();
                    }
                    shopcenterBean = new Gson().fromJson(respose, ShopcenterBean.class);
                    if(shopcenterBean.getData().getList().getPush_on_off().equals("Y")){//推送开关状态
//                        image_tujianrenwukaiguan.setSelected(true);
                    }else {
//                        image_tujianrenwukaiguan.setSelected(false);
                    }
                    if(shopcenterBean.getData().getList().getMemberImgUrl()==null||shopcenterBean.getData().getList().getMemberImgUrl().equals("")){
//                        mImageview_vip.setVisibility(View.GONE);
                    }else {
//                        mImageview_vip.setVisibility(View.VISIBLE);
//                        Glide.with(ShopCenterNewActivity.this).load(shopcenterBean.getData().getList().getMemberImgUrl()).error(R.mipmap.vip1).into(mImageview_vip);
                    }
                    Glide.with(ShopCenterNewActivity.this).load(shopcenterBean.getData().getList().getAvatar_url()).into(iamgeview_shopheadpic);
//                    Glide.with(ShopCenterNewActivity.this).load(shopcenterBean.getData().getList().getIconImgUrl()).error(R.mipmap.lv1).into(mImageview_lv);
                    textview_shopname.setText(shopcenterBean.getData().getList().getBusiness_name());
//                    mTextview_namenext.setText(shopcenterBean.getData().getList().getBusiness_address()+" | ");
//                    mTextview_money.setText(shopcenterBean.getData().getList().getCommission()+"");
//                    mTextview_text_tui_count.setText(shopcenterBean.getData().getList().getSpread_b()+"个");
                    if(shopcenterBean.getData().getList().getMember_enddate()!=null){
//                        mTextview_text_huiyuan.setText(shopcenterBean.getData().getList().getMember_enddate().substring(0,10)+"到期");
                    }
//                    setstar((float) shopcenterBean.getData().getList().getEvaluation_star());
                    textview_shoptype.setText(shopcenterBean.getData().getList().getBusiness_type_id());
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
