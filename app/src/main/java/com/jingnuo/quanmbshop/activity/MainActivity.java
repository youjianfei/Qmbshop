package com.jingnuo.quanmbshop.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.jaeger.library.StatusBarUtil;
import com.jingnuo.quanmbshop.App;
import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.class_.Permissionmanage;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.ShouyeRadios;
import com.jingnuo.quanmbshop.utils.AutoUpdate;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.master.permissionhelper.PermissionHelper;
import com.jingnuo.quanmbshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

import static com.jingnuo.quanmbshop.data.Staticdata.isLogin;
import static io.rong.imlib.model.Conversation.ConversationType.PRIVATE;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static MainActivity mainActivity;
    //控件
    DrawerLayout drawerlayout_menu;

    CircleImageView image_dot;
    ImageView mImageview_message;//消息
    ImageView mImageview_iamge_person;//用户中心
    ImageView mImageview_help;//帮忙
    ImageView mImageview_needhelp;//求助
    private TextSwitcher tv_notice;//轮播通知文字

    TextView mTextview_neerbytask;//附近服务
    TextView mTextview_lovetask;//爱心帮
    TextView mTextview_myshequ;//我的社区

    PermissionHelper permissionHelper;


    //高德定位
    int locationtime = 0;


    private String[] mAdvertisements;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);//状态栏颜色
        //注册监听函数
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

//        ActionBar actionBar = getActionBar();
//        actionBar.setCustomView(R.mipmap.aboutus);

        initview();
        initdata();
        initlinstenner();
        setview();
        setdata();

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
                                Staticdata.xValue + "&y_value=" + Staticdata.yValue, MainActivity.this, 0);

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

    @Override
    protected void onPause() {
        super.onPause();
        //启动定位
//        mLocationClient.startLocation();
    }


    public void initview() {
        drawerlayout_menu = findViewById(R.id.drawerlayout_menu);
        image_dot = findViewById(R.id.image_dot);
        mImageview_message = findViewById(R.id.iamge_message);
        mImageview_iamge_person = findViewById(R.id.iamge_person);
        mImageview_help = findViewById(R.id.image_help);
        mImageview_needhelp = findViewById(R.id.image_needhelp);
        mTextview_neerbytask = findViewById(R.id.text_neerbytask);
        mTextview_lovetask = findViewById(R.id.text_lovetask);
        mTextview_myshequ = findViewById(R.id.text_myshequ);
        tv_notice = findViewById(R.id.tv_notice);
    }

    public void initdata() {
        if (mainActivity == null) {
            mainActivity = this;
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
                    ToastUtils.showToast(MainActivity.this, "请允许开启定位功能");
                }

            }
        });
        permissionmanage.requestpermission();


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
                    autoUpdate = new AutoUpdate(MainActivity.this);
                    autoUpdate.requestVersionData();

                } else {
                    Toast.makeText(MainActivity.this, "请开启存储权限,以便安装最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        });
        permissionmanage.requestpermission();

    }

    public void initlinstenner() {
        mImageview_message.setOnClickListener(this);
        mImageview_help.setOnClickListener(this);
        mImageview_needhelp.setOnClickListener(this);
        mTextview_neerbytask.setOnClickListener(this);
        mTextview_lovetask.setOnClickListener(this);
        mTextview_myshequ.setOnClickListener(this);
        mImageview_iamge_person.setOnClickListener(this);

    }

    public void setview() {
        tv_notice.setFactory(new ViewSwitcher.ViewFactory() {
            // 这里用来创建内部的视图，这里创建TextView，用来显示文字
            @RequiresApi(api = Build.VERSION_CODES.M)
            public View makeView() {
                TextView tv = new TextView(MainActivity.this);
                // 设置文字的显示单位以及文字的大小
                tv.setTextSize(12);
                tv.setTextColor(getResources().getColor(R.color.gray_969696));
                return tv;
            }
        });
        tv_notice.setInAnimation(MainActivity.this,
                R.anim.slide_in_bottom);
        tv_notice.setOutAnimation(getApplicationContext(), R.anim.slide_out_up);

    }

    private final int HOME_AD_RESULT = 1;
    private int mSwitcherCount = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 广告
                case HOME_AD_RESULT:
                    tv_notice.setText(data.get(mSwitcherCount % data.size()).getContent());
                    mSwitcherCount++;
                    mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);
                    break;
            }

        }
    };

    //设置容云用户信息
//    private void setRongUserInfo(final String targetid) {
//        if (RongIM.getInstance()!=null){
//            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//                @Override
//                public UserInfo getUserInfo(String s) {
//                    if(targetid.equals(s)){
//                        return new UserInfo(targetid,"郑州灯饰借", Uri.parse("http://quanminbang-img.oss-cn-beijing.aliyuncs.com/image/avatar/ba031a18-c3c0-4e7d-8a8f-2b94c12489391534840739188.png"));
//                    }
//                    return null;
//                }
//            },true);
//        }
//
//
//    }
    public void setdata() {
//        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                for (int i = 0; i < conversations.size(); i++) {
////                    getListUserInfo(ConversationListActivity.this,conversations.get(i).getTargetId());
//                    setRongUserInfo(conversations.get(i).getTargetId());
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });


        if (isLogin) {
            drawerlayout_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);//打开滑动
        } else {
            drawerlayout_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止滑动
        }
        data = new ArrayList<>();
        requestRadios();
    }

    List<ShouyeRadios.DataBean> data;

    void requestRadios() {
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose + "喇叭", "首页");
                data.clear();
                data.addAll(new Gson().fromJson(respose, ShouyeRadios.class).getData());
                mHandler.sendEmptyMessage(HOME_AD_RESULT);
            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_hu + Urls.shouyeRadios, this, 0);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.image_help://广场列表
                intent = new Intent(MainActivity.this, ShanghuMainActivity.class);
                startActivity(intent);

                break;
            case R.id.text_myshequ://我的社区
                if (Staticdata.isLogin) {
                    if (Staticdata.static_userBean.getData().getAppuser().getCommunity_code().equals("")) {
                        ToastUtils.showToast(this, "请先绑定社区");
                        intent = new Intent(this, ShezhishequActivity.class);
                        startActivity(intent);
                        return;
                    }
                    intent = new Intent(this, MyShequActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.image_needhelp://发布服务
                if (isLogin) {
                    intent = new Intent(this, IssueTaskActivity.class);
                    this.startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.iamge_message://消息界面
//                if (isLogin) {
//                    image_dot.setVisibility(View.INVISIBLE);
//                    intent=new Intent(MainActivity.this,MessageActivity.class);
//                    intent.putExtra("newmessageTYpe",Staticdata.newmessageTYpe);
//                    startActivity(intent);
//                    Staticdata.newmessageTYpe="notype";//跳转完之后归0
//                } else {
//                    intent = new Intent(this, LoginActivity.class);
//                    startActivity(intent);
//                }
                if (isLogin) {
                    image_dot.setVisibility(View.INVISIBLE);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);

                    intent = new Intent(MainActivity.this, ConversationListActivity.class);
                    intent.putExtra("newmessageTYpe", Staticdata.newmessageTYpe);
                    startActivity(intent);
                    Staticdata.newmessageTYpe = "notype";//跳转完之后归0
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_lovetask://爱心帮
//                intent = new Intent(MainActivity.this, LoveTaskActivity.class);
//                startActivity(intent);

                speak();

//                RongIM.getInstance().startPrivateChat(MainActivity.this, "456", "标题");
//                RongIM.getInstance().startConversation(MainActivity.this,PRIVATE,"111","用户名");
//                RongIM.getInstance().startConversationList(MainActivity.this);

//                new Popwindow_helperfirst(MainActivity.this,1,5.16).showpop();


                break;

            case R.id.iamge_person://个人中心
                if (isLogin) {
                    drawerlayout_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);//打开滑动
                    drawerlayout_menu.openDrawer(Gravity.LEFT);
                } else {
                    drawerlayout_menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止滑动
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.text_neerbytask:
                intent = new Intent(this, SkillActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void setdot() {
        image_dot.setVisibility(View.VISIBLE);
    }

    /**
     * 再点一次退出
     */
    private long mLastTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastTime > 2000) {
            // 两次返回时间超出两秒
            Toast.makeText(this, "再点一次退出程序", Toast.LENGTH_SHORT).show();
            mLastTime = System.currentTimeMillis();
        } else {
            finish();
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
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//调用定位结束方法
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LogUtils.LOG("ceshi1", "onPostResume111", "~~~~main");
        if (!Staticdata.isLogin) {
        }
    }


    /**
     * 讯飞 模块
     *
     */

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer = "xiaoyan";

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue ;
    String texts = "";
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    void speak(){


        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(MainActivity.this, mTtsInitListener);

        if( null == mTts ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            LogUtils.LOG("ceshixunfei","创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化","mainactivity");
            return;
        }

        setParam();

        int code = mTts.startSpeaking("今天天 气不错", mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
			/*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/

        if (code != ErrorCode.SUCCESS) {
            LogUtils.LOG("ceshixunfei","语音合成失败,错误码: " + code,"mainactivity");

        }
    }
    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH,"50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME,"50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.pcm");
    }





    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                LogUtils.LOG("ceshixunfei","初始化失败,错误码："+code,"mainactivity");

            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            LogUtils.LOG("ceshixunfei","开始播放","mainactivity");

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {
            LogUtils.LOG("ceshixunfei","暂停播放","mainactivity");

        }

        @Override
        public void onSpeakResumed() {
            LogUtils.LOG("ceshixunfei","继续播放","mainactivity");

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }


        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                LogUtils.LOG("ceshixunfei","播放完成","mainactivity");
            } else if (error != null) {
                LogUtils.LOG("ceshixunfei",error.getPlainDescription(true),"mainactivity");

            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }


    };























}


