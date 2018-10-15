package com.jingnuo.quanmbshop.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_loadImage_respose;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.class_.GlideLoader;
import com.jingnuo.quanmbshop.class_.Permissionmanage;
import com.jingnuo.quanmbshop.class_.RegularYanzheng;
import com.jingnuo.quanmbshop.class_.UpLoadImage;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.popwinow.Popwindow_shenfenzhengTip;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ReducePIC;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.master.permissionhelper.PermissionHelper;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationActivity extends BaseActivityother {

    //控件
    EditText mEditview_name;
    EditText mEditview_idcardnumber;
    ImageView mImageview_zheng;
    ImageView mImageview_fan;
    ImageView mImageview_shouchi;
    ImageView mImage_choose;
    Button mBitton_submit;
    TextView textview_xieyi;

    //对象
    PermissionHelper permissionHelper;
    KProgressHUD mKProgressHUD;

    UpLoadImage upLoadImage;

    //数据
    String name = "";
    String idcacardnumber = "";
    String idcacardPICID_zheng = "";
    String idcacardPICID_fan = "";
    String idcacardPICID_shouchi = "";

    Map  map_submit;


    int PICposition = 0;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void setData() {

        upLoadImage = new UpLoadImage(this, new Interface_loadImage_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "发布技能上传图片返回respose");
                int status = 0;
                String msg = "";
                String imageID = "";
                try {
                    JSONObject object = new JSONObject(respose);
                    status = (Integer) object.get("code");//登录状态
                    msg = (String) object.get("msg");//登录返回信息

                    if (status == 1) {
                        mKProgressHUD.dismiss();
                        imageID = (String) object.get("imgID");
                        LogUtils.LOG("ceshi", "单张图片ID" + imageID, "发布技能上传图片返回imageID");
                        switch (PICposition) {
                            case 1:
                                idcacardPICID_zheng = imageID;
                                break;
                            case 2:
                                idcacardPICID_fan = imageID;
                                break;
                            case 3:
                                idcacardPICID_shouchi = imageID;
                                break;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void initData() {
        mKProgressHUD = new KProgressHUD(AuthenticationActivity.this);

        permissionHelper = new PermissionHelper(AuthenticationActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        map_submit=new HashMap();

    }

    @Override
    protected void initListener() {
        mImageview_zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICposition = 1;
                new Popwindow_shenfenzhengTip(1,AuthenticationActivity.this, new Interence_complteTask() {
                    @Override
                    public void onResult(boolean result) {
                        if(result){
                            chooseIDcard();
                        }
                    }
                }).showPopwindow();
            }
        });
        mImageview_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICposition = 2;
                new Popwindow_shenfenzhengTip(2,AuthenticationActivity.this, new Interence_complteTask() {
                    @Override
                    public void onResult(boolean result) {
                        if(result){
                            chooseIDcard();
                        }
                    }
                }).showPopwindow();
            }
        });
        mImageview_shouchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICposition = 3;
                new Popwindow_shenfenzhengTip(3,AuthenticationActivity.this, new Interence_complteTask() {
                    @Override
                    public void onResult(boolean result) {
                        if(result){
                            chooseIDcard();
                        }
                    }
                }).showPopwindow();
            }
        });
        mImage_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImage_choose.isSelected()) {
                    mImage_choose.setSelected(false);
                } else {
                    mImage_choose.setSelected(true);
                }
            }
        });
        textview_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AuthenticationActivity.this,XieYiShanghuActivity.class);
                startActivity(intent);
            }
        });
        mBitton_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setmap()){
                    new Volley_Utils(new Interface_volley_respose() {
                        @Override
                        public void onSuccesses(String respose) {
                            int status = 0;
                            String msg = "";
                            String imageID = "";
                            try {
                                JSONObject object = new JSONObject(respose);
                                status = (Integer) object.get("code");//登录状态
                                msg = (String) object.get("msg");//登录返回信息

                                if (status == 1) {
                                    Intent intent_submit=new Intent(AuthenticationActivity.this,SubmitSuccessActivity.class);
                                    intent_submit.putExtra("state","1");
                                    startActivity(intent_submit);
                                    ToastUtils.showToast(AuthenticationActivity.this,"审核提交成功");//
                                    finish();
                                }else {
                                    ToastUtils.showToast(AuthenticationActivity.this,"审核提交失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(int error) {

                        }
                    }).postHttp(Urls.Baseurl_hu+Urls.authenticationHelper,AuthenticationActivity.this,1,map_submit);
                }

            }
        });


    }

    @Override
    protected void initView() {
        mEditview_name = findViewById(R.id.edit_realname);
        mEditview_idcardnumber = findViewById(R.id.edit_idcardnumber);
        mImageview_zheng = findViewById(R.id.image_idcardzheng);
        mImageview_fan = findViewById(R.id.image_idcardfan);
        mImageview_shouchi = findViewById(R.id.image_idcardshou);
        mBitton_submit = findViewById(R.id.button_submit);
        mImage_choose = findViewById(R.id.image_choose);
        textview_xieyi = findViewById(R.id.textview_xieyi);
    }
    boolean setmap(){
        name=mEditview_name.getText()+"";
        if(name.equals("")){
            ToastUtils.showToast(this,"请填写真实姓名");
            return false;
        }
        idcacardnumber=mEditview_idcardnumber.getText()+"";
        if(idcacardnumber.equals("")|| !RegularYanzheng.is18ByteIdCardComplex(idcacardnumber)){
            ToastUtils.showToast(this,"请填写正确证件号码");
            return false;
        }
        if(idcacardPICID_zheng.equals("")){
            ToastUtils.showToast(this,"请重新上传身份证正面照片");
            return false;
        }
        if(idcacardPICID_fan.equals("")){
            ToastUtils.showToast(this,"请重新上传身份证反面照片");
            return false;
        }
        if(idcacardPICID_shouchi.equals("")){
            ToastUtils.showToast(this,"请重新上传手持身份证照片");
            return false;
        }
        if (!mImage_choose.isSelected()) {
            ToastUtils.showToast(this, "请阅读全民帮用户协议并同意");
            return false;
        }

        map_submit.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
        map_submit.put("helper_name",name);
        map_submit.put("idCard",idcacardnumber);
        map_submit.put("front_imgID",idcacardPICID_zheng);
        map_submit.put("back_imgID",idcacardPICID_fan);
        map_submit.put("hand_imgID",idcacardPICID_shouchi);
        LogUtils.LOG("ceshi",map_submit.toString(),"身份认证");
        return true;

    }

    void chooseIDcard() {

        Permissionmanage permissionmanage = new Permissionmanage(permissionHelper, new InterfacePermission() {
            @Override
            public void onResult(boolean result) {
                LogUtils.LOG("ceshi", result + "", "");
                if (result) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//安卓7.0权限 代替了FileProvider方式   https://blog.csdn.net/xiaoyu940601/article/details/54406725
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                    }
                    ImageConfig imageConfig
                            = new ImageConfig.Builder(new GlideLoader())
                            // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                            .steepToolBarColor(getResources().getColor(R.color.yellow_jianbian_end))
                            // 标题的背景颜色 （默认黑色）
                            .titleBgColor(getResources().getColor(R.color.yellow_jianbian_end))
                            // 提交按钮字体的颜色  （默认白色）
                            .titleSubmitTextColor(getResources().getColor(R.color.white))
                            // 标题颜色 （默认白色）
                            .titleTextColor(getResources().getColor(R.color.white))
                            // 开启单选   （默认为多选）
                            .singleSelect()
                            .showCamera()
                            // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                            .filePath("/ImageSelector/Pictures")
                            .build();
                    ImageSelector.open(AuthenticationActivity.this, imageConfig);   // 开启图片选择器
                } else {
                    ToastUtils.showToast(AuthenticationActivity.this, "请允许开启照相功能，并读取本地文件");
                }
            }
        });
        permissionmanage.requestpermission();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
//            ArrayList<Bitmap> dataPictrue = dataPictrue = new ArrayList<>();
            List<String> mList_picpath = new ArrayList<>();

            Bitmap bitmap;
            Bitmap mBitmap = null;
            for (String path : pathList) {
                bitmap = BitmapFactory.decodeFile(path);
                mBitmap = Bitmap.createScaledBitmap(bitmap, 525, 350, true);

                String src_path = path;//原图片的路径
                String targetPath = Environment.getExternalStorageDirectory() + "/picyasuo/"+System.currentTimeMillis()+".png";//压缩后图片的路径
                final String compressImage = ReducePIC.compressImage(src_path, targetPath, 60);//进行图片压缩，返回压缩后图片的路径
//                dataPictrue.add(mBitmap);
                mList_picpath.add(compressImage);
            }
            mKProgressHUD.show();
            switch (PICposition) {
                case 1:
                    mImageview_zheng.setImageBitmap(mBitmap);
                    upLoadImage.uploadImg(mList_picpath, 1,"Y");//上传图片
                    break;
                case 2:
                    mImageview_fan.setImageBitmap(mBitmap);
                    upLoadImage.uploadImg(mList_picpath, 1,"Y");//上传图片
                    break;
                case 3:
                    mImageview_shouchi.setImageBitmap(mBitmap);
                    upLoadImage.uploadImg(mList_picpath, 1,"Y");//上传图片
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        upLoadImage=null;
    }
}
