package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.ShareGoodWeb;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.DataCleanManager;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;

import static com.jingnuo.quanmbshop.utils.Utils.deleteAllFiles;

public class SettingActivity extends BaseActivityother {
    //控件

    TextView mTextview_cleancache;
    TextView mTextview_textview_cleancacheSize;
    TextView mTextview_share;
    TextView textview_aboutus;


    //对象

    ShareGoodWeb shareClass;
    @Override
    public int setLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setData() {
        try {
            mTextview_textview_cleancacheSize.setText( DataCleanManager. getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        shareClass = new ShareGoodWeb(this);
    }

    @Override
    protected void initListener() {
        mTextview_cleancache.setOnClickListener(this);
        mTextview_share.setOnClickListener(this);
        textview_aboutus.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        mTextview_cleancache=findViewById(R.id.textview_cleancache);
        mTextview_textview_cleancacheSize=findViewById(R.id.textview_cleancacheSize);
        mTextview_share=findViewById(R.id.textview_shareAPP);
        textview_aboutus=findViewById(R.id.textview_aboutus);
    }
    File root;//分享的图片要放的文件夹
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            case R.id.textview_aboutus :
                Intent intent=new Intent(SettingActivity.this,AboutUsActivity.class);
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
                    ToastUtils.showToast(this,"清除缓存成功");
                    mTextview_textview_cleancacheSize.setText( DataCleanManager. getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.textview_shareAPP:
                if(Utils.isWxInstall(this)){
                 shareClass.shareapp();
                }else {
                    ToastUtils.showToast(this,"未安装微信");
                }
                break;
        }
    }
}
