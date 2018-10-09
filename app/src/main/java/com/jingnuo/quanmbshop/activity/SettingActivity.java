package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.ShareGoodWeb;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.DataCleanManager;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.Map;

import io.rong.imkit.RongIM;

import static com.jingnuo.quanmbshop.utils.Utils.deleteAllFiles;

public class SettingActivity extends BaseActivityother {

    private UMShareAPI mShareAPI;//第三方登录登录

    //控件

    TextView mTextview_cleancache;
    TextView mTextview_textview_cleancacheSize;
    //    TextView mTextview_share;
    TextView textview_aboutus;
    TextView textview_changephonenumber;
    TextView textview_phonenumber;
    TextView textview_changepassword;
    TextView textview_changesafepassword;
    TextView textview_exit;


    //对象

    ShareGoodWeb shareClass;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setData() {
        try {
            mTextview_textview_cleancacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black ), 0);//状态栏颜色
        shareClass = new ShareGoodWeb(this);
        textview_phonenumber.setText(Staticdata.static_userBean.getData().getAppuser().getMobile_no());
        if(!Staticdata.static_userBean.getData().getAppuser().getSecurity_code().equals("")){
            textview_changesafepassword.setText("设置安全密码");
        }

    }

    @Override
    protected void initListener() {
        mTextview_cleancache.setOnClickListener(this);
//        mTextview_share.setOnClickListener(this);
        textview_changephonenumber.setOnClickListener(this);
        textview_aboutus.setOnClickListener(this);
        textview_phonenumber.setOnClickListener(this);
        textview_changepassword.setOnClickListener(this);
        textview_changesafepassword.setOnClickListener(this);
        textview_exit.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        mTextview_cleancache = findViewById(R.id.textview_cleancache);
        mTextview_textview_cleancacheSize = findViewById(R.id.textview_cleancacheSize);
//        mTextview_share=findViewById(R.id.textview_shareAPP);
        textview_changephonenumber = findViewById(R.id.textview_changephonenumber);
        textview_aboutus = findViewById(R.id.textview_aboutus);
        textview_phonenumber = findViewById(R.id.textview_phonenumber);
        textview_changepassword = findViewById(R.id.textview_changepassword);
        textview_changesafepassword = findViewById(R.id.textview_changesafepassword);
        textview_exit = findViewById(R.id.textview_exit);
    }

    File root;//分享的图片要放的文件夹

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textview_changephonenumber://修改手机
                Intent intent_change_phone2 = new Intent(this, ChangephoneNumberActivity.class);
                startActivity(intent_change_phone2);
                break;
            case R.id.textview_phonenumber://修改手机
                Intent intent_change_phone = new Intent(this, ChangephoneNumberActivity.class);
                startActivity(intent_change_phone);
                break;
            case R.id.textview_changepassword://修改密码
                Intent intent_change = new Intent(this, ChangepasswordActivity.class);
                startActivity(intent_change);
                break;
            case R.id.textview_changesafepassword:
                Intent intent_setsafe=new Intent(this,SetSafepassword1Activity.class);
                if(textview_changesafepassword.getText().equals("修改安全密码")){
                    intent_setsafe.putExtra("change","change");
                }else {
                    intent_setsafe.putExtra("change","nochange");
                }
                startActivity(intent_setsafe);
                break;
            case R.id.textview_aboutus:
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

            case R.id.textview_cleancache:

                root = new File(Environment.getExternalStorageDirectory() + "/picyasuo/");//找到根目录下DICM文件夹
                if (!root.exists()) {
                    root.mkdirs();
                }
                deleteAllFiles(root);//删除上次分享的残余图片
                try {
                    DataCleanManager.clearAllCache(SettingActivity.this);
                    ToastUtils.showToast(this, "清除缓存成功");
                    mTextview_textview_cleancacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.textview_exit:
                logout();
                SharedPreferencesUtils.putString(this, "QMB", "password", "");
                Staticdata.isLogin = false;
                Staticdata.static_userBean.setData(null);//用户信息清空
                RongIM.getInstance().disconnect();
                Intent intent_logout = new Intent(this, LoginActivity.class);
                startActivity(intent_logout);
                finish();
                break;
//            case R.id.textview_shareAPP:
//                if(Utils.isWxInstall(this)){
//                 shareClass.shareapp();
//                }else {
//                    ToastUtils.showToast(this,"未安装微信");
//                }
//                break;
        }
    }
    public void logout() {

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "退出登录");
            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl + Urls.logout + Staticdata.static_userBean.getData().getUser_token(), this, 0);
        //登出注销微信授权
        mShareAPI = UMShareAPI.get(this);
        mShareAPI.deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
}
