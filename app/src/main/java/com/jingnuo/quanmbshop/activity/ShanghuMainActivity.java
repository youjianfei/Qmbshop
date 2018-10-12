package com.jingnuo.quanmbshop.activity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.Permissionmanage;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyTodoBean;
import com.jingnuo.quanmbshop.fargment.Fragment_shanghutask;
import com.jingnuo.quanmbshop.utils.AutoUpdate;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.master.permissionhelper.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;

import static com.jingnuo.quanmbshop.data.Staticdata.isLogin;

public class ShanghuMainActivity extends BaseActivityother {
    public static ShanghuMainActivity shanghuMainActivity;
    //控件
    LinearLayout LinearLayout_taskmain;
    RelativeLayout LinearLayout_messagemain;

    ImageView imageview_task;
    ImageView image_messgae;
    TextView  text_task;
    TextView  text_message;
    CircleImageView image_dot;


    //对象
    Fragment_shanghutask fragment_shanghutask;


    FragmentManager fragmetnmanager;
    FragmentTransaction transaction;


    PermissionHelper permissionHelper;

    //高德定位
    int locationtime = 0;
    @Override
    public int setLayoutResID() {
        return R.layout.activity_shanghu_main;
    }

    @Override
    protected void setData() {
        LinearLayout_messagemain.setSelected(false);
        LinearLayout_taskmain.setSelected(true);
        image_messgae.setSelected(false);
        imageview_task.setSelected(true);
        text_task.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.yellow_jianbian_end));
        text_message.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.black_text2));
    }
    public void setdot() {
        image_dot.setVisibility(View.VISIBLE);
    }
    @Override
    protected void initData() {
        if (shanghuMainActivity == null) {
            shanghuMainActivity = this;
        }
        permissionHelper = new PermissionHelper(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        Permissionmanage permissionmanage = new Permissionmanage(permissionHelper, new InterfacePermission() {
            @Override
            public void onResult(boolean result) {
                LogUtils.LOG("ceshi", result + "", "");
                if (result) {//定位权限
                    setmapdata();// 高德地图配置参数
                    updata();
                    return;
                } else {
                    ToastUtils.showToast(ShanghuMainActivity.this, "请允许开启定位功能");
                }

            }
        });
        permissionmanage.requestpermission();

        fragment_shanghutask = new Fragment_shanghutask();
        fragmetnmanager = getFragmentManager();
        transaction = fragmetnmanager.beginTransaction();
        transaction.add(R.id.framelayout_main, fragment_shanghutask).commit();
    }
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LogUtils.LOG("ceshiqq", "定位成功,纬度：" + aMapLocation.getLatitude() + "精度：" + aMapLocation.getLongitude(), "mainactivity");

                    Staticdata.xValue = aMapLocation.getLatitude() + "";//获取纬度
                    Staticdata.yValue = aMapLocation.getLongitude() + "";//获取经度
                    Staticdata.city_location = aMapLocation.getCity();//城市信息
                    ++locationtime;
                    LogUtils.LOG("ceshiqq", "定位次数：" + locationtime, "mainactivity");
                    if (locationtime == 1) {
                        Intent intent = new Intent("com.jingnuo.quanmb.ADDRESS");
                        intent.putExtra("address", aMapLocation.getCity());
                        sendBroadcast(intent);
                    }
                    if (Staticdata.isLogin) {
                        new Volley_Utils(new Interface_volley_respose() {
                            @Override
                            public void onSuccesses(String respose) {

                            }

                            @Override
                            public void onError(int error) {

                            }
                        }).Http(Urls.Baseurl + Urls.updataXYDU + Staticdata.static_userBean.getData().getUser_token() + "&x_value=" +
                                Staticdata.xValue + "&y_value=" + Staticdata.yValue, ShanghuMainActivity.this, 0);
                    }
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                }
            }
        }
    };

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private void setmapdata() {
        //初始化定位
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);

            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setOnceLocation(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(100000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();

        }

    }
    AutoUpdate autoUpdate;

    void updata() {

        Permissionmanage permissionmanage = new Permissionmanage(permissionHelper, new InterfacePermission() {
            @Override
            public void onResult(boolean result) {
                if (result) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//安卓7.0权限 代替了FileProvider方式   https://blog.csdn.net/xiaoyu940601/article/details/54406725
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                    }
                    //检测是否更新
                    autoUpdate = new AutoUpdate(ShanghuMainActivity.this);
                    autoUpdate.requestVersionData();

                } else {
                    Toast.makeText(ShanghuMainActivity.this, "请开启存储权限,以便安装最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        });
        permissionmanage.requestpermission();

    }
    @Override
    protected void initListener() {
        LinearLayout_taskmain.setOnClickListener(this);
        LinearLayout_messagemain.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        LinearLayout_taskmain=findViewById(R.id.LinearLayout_taskmain);
        LinearLayout_messagemain=findViewById(R.id.LinearLayout_messagemain);
        imageview_task=findViewById(R.id.imageview_task);
        image_messgae=findViewById(R.id.image_messgae);
        image_dot=findViewById(R.id.image_dot);
        text_task=findViewById(R.id.text_task);
        text_message=findViewById(R.id.text_message);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.LinearLayout_taskmain://点击任务
                LinearLayout_messagemain.setSelected(false);
                LinearLayout_taskmain.setSelected(true);
                image_messgae.setSelected(false);
                imageview_task.setSelected(true);
                text_task.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.yellow_jianbian_end));
                text_message.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.black_text2));

                transaction = fragmetnmanager.beginTransaction();
                if (fragment_shanghutask == null) {
                    fragment_shanghutask = new Fragment_shanghutask();
                    transaction.replace(R.id.framelayout_main, fragment_shanghutask).commit();
                } else {
                    transaction.replace(R.id.framelayout_main, fragment_shanghutask).commit();
                }
                break;
            case R.id.LinearLayout_messagemain://点击消息

                if (isLogin) {
                    image_dot.setVisibility(View.INVISIBLE);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);

                   Intent  intent = new Intent(ShanghuMainActivity.this, ConversationListActivity.class);
                    intent.putExtra("newmessageTYpe", Staticdata.newmessageTYpe);
                    startActivity(intent);
                   overridePendingTransition(0, 0);//跳转无动画
                    Staticdata.newmessageTYpe = "notype";//跳转完之后归0
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//调用定位结束方法
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionHelper != null) {
            permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
