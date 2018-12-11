package com.jingnuo.quanmbshop.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.jingnuo.quanmbshop.Adapter.Adapter_SquareList;
import com.jingnuo.quanmbshop.Interface.Interence_jubao;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.Permissionmanage;
import com.jingnuo.quanmbshop.customview.SimpleRatingBar;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.ShanghuneworderBean;
import com.jingnuo.quanmbshop.entityclass.ShopcenterBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_JiaoBaozhengjin;
import com.jingnuo.quanmbshop.popwinow.Popwindow_ShanghuIsjiedan;
import com.jingnuo.quanmbshop.utils.AutoUpdate;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.master.permissionhelper.PermissionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;

import static com.jingnuo.quanmbshop.data.Staticdata.isLogin;

public class ShanghuMainActivity extends BaseActivityother {
    public static ShanghuMainActivity shanghuMainActivity;
    //控件
    LinearLayout LinearLayout_taskmain;
    RelativeLayout LinearLayout_messagemain;

    ImageView iamge_empty;
    ImageView imageview_task;
    ImageView image_messgae;
    TextView  text_task;
    TextView  text_message;
    TextView  textview_huiyuan;
    CircleImageView image_dot;

    //控件
    RelativeLayout re_title;
    ImageView image_personcenter;
    TabLayout mTablayout;
    PullToRefreshListView mListview_shanghuorder;

    View listheadView;//头视图
    TabLayout mTablayout_header;
    TextView textview_money;
    TextView text_jiedan;
    TextView textview_ordercount;
    SimpleRatingBar simpleRatingBar;
    ImageView image_huiyuan;
    //popwindow
    Popwindow_ShanghuIsjiedan popwindow_shanghuIsjiedan;
    //数据
    int page = 1;
    int type = 0;    // 0 新订单
    String state = "00000";
    ShopcenterBean shopcenterBean;//商户

    Adapter_SquareList adapter;
    List<ShanghuneworderBean.DataBean.ListBean> mList;
    ShanghuneworderBean  shanghuneworderBean;


    //对象
//    Fragment_shanghutask fragment_shanghutask;
//
//
//    FragmentManager fragmetnmanager;
//    FragmentTransaction transaction;


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

//        fragment_shanghutask = new Fragment_shanghutask();
//        fragmetnmanager = getFragmentManager();
//        transaction = fragmetnmanager.beginTransaction();
//        transaction.add(R.id.framelayout_main, fragment_shanghutask).commit();
//

        requestshopinfo();
        if(!Staticdata.xValue.equals("")){
            request("00000", page);
        }
        mList=new ArrayList<>();
        adapter=new Adapter_SquareList(mList,ShanghuMainActivity.this);
        mListview_shanghuorder.setAdapter(adapter);

        Map map_lookBaozhengjin=new HashMap();
        map_lookBaozhengjin.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
        map_lookBaozhengjin.put("business_no", Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
        requestbaozhengjin(map_lookBaozhengjin);

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
                        request("00000", page);
                    }
                    if (Staticdata.isLogin) {
                        new Volley_Utils(new Interface_volley_respose() {
                            @Override
                            public void onSuccesses(String respose) {

                            }

                            @Override
                            public void onError(int error) {

                            }
                        }).Http(Urls.Baseurl + Urls.updataXYDU + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&x_value=" +
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
    //数据
    int isjiaona;
//    String money="";
    String needmoney="";
//    String baozhengjinmsg="";

    void requestbaozhengjin( Map map){//是否缴纳保证金详情
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("cehsi","保证金"+respose,"保证金");
                try {
                    JSONObject object=new JSONObject(respose);
                    isjiaona = (Integer) object.get("code");//是否缴纳
//                    money = (String) object.get("ensure_money");//缴纳显示金额
//                    baozhengjinmsg = (String) object.get("msg");//提示
                    needmoney = (String) object.get("ensure_money_business");//需要缴纳的金额

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(isjiaona==0){//没有缴纳保证金
                    new Popwindow_JiaoBaozhengjin(needmoney,ShanghuMainActivity.this).showPopwindow();
                }
            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_hu+Urls.lookBaozhengjin,this,1,map);

    }


    AutoUpdate autoUpdate;

    void updata() {//检测是否更新

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



        text_jiedan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                popwindow_shanghuIsjiedan.showPopwindow();
            }
        });
        mListview_shanghuorder.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                request(state,page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                request(state,page);
            }
        });
        mListview_shanghuorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type==0){
                    Intent intent=new Intent(ShanghuMainActivity.this,TaskDetailsActivity.class);
                    intent.putExtra("id",mList.get(position-2).getTask_id()+"");
//                LogUtils.LOG("ceshi","列表数"+mdata.size()+"点击位置"+position,"sadfasfd");
//                intent.putExtra("order_no",mdata.get(position-1).getOrder_no()+"");
                    startActivity(intent);
                }else {
                    Intent  intent=new Intent(ShanghuMainActivity.this,HelperOrderActivity.class);
                    intent.putExtra("type",type);
                    LogUtils.LOG("ceshi","列表数"+mList.size()+"点击位置"+position,"sadfasfd");
                    intent.putExtra("order_no",mList.get(position-2).getOrder_no()+"");
                    startActivity(intent);
                }





            }
        });
        //listview的滑动距离监听
        mListview_shanghuorder.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (isScroll()) {
                    float scrollY = getScrollY();
                    LogUtils.LOG("ceshi", "scrollY" + scrollY, "透明度");

                    if (scrollY > 0.90) {
//                        int alpha = (int) (255 * scrollY);
                        mTablayout.setVisibility(View.VISIBLE);

//                        LogUtils.LOG("ceshi",alpha+"scrollY"+scrollY,"透明度");
//                        mRelativelayout_sort.setAlpha(alpha);
//                        mRelativelayout_sort.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
//                        relative_shaixuan.setVisibility(View.INVISIBLE);
                    } else {
//                        relative_shaixuan.setVisibility(View.VISIBLE);
                        mTablayout.setVisibility(View.INVISIBLE);

//                        mRelativelayout_sort.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    }
                } else {
                    if (firstVisibleItem > 1) {
                        mTablayout.setVisibility(View.VISIBLE);
                    } else {
                        mTablayout.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });
        mTablayout_header.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.LOG("ceshi", tab.getTag() + "", "mytodo");
                state = (String) tab.getTag();
                page = 1;
                request(state, page);
                mTablayout.setScrollPosition(0,tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.LOG("ceshi", tab.getTag() + "", "mytodo");
                state = (String) tab.getTag();
                page = 1;
                request(state, page);
                mTablayout_header.setScrollPosition(0,tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        image_personcenter.setOnClickListener(this);



    }

    @Override
    protected void initView() {
        LinearLayout_taskmain=findViewById(R.id.LinearLayout_taskmain);
        LinearLayout_messagemain=findViewById(R.id.LinearLayout_messagemain);
        iamge_empty=findViewById(R.id.iamge_empty);
        imageview_task=findViewById(R.id.imageview_task);
        image_messgae=findViewById(R.id.image_messgae);
        image_dot=findViewById(R.id.image_dot);
        text_task=findViewById(R.id.text_task);
        text_message=findViewById(R.id.text_message);



        re_title = findViewById(R.id.re_title);
        text_jiedan = findViewById(R.id.text_jiedan);
        mListview_shanghuorder = findViewById(R.id.list_order);
        image_personcenter = findViewById(R.id.image_personcenter);
        mTablayout = findViewById(R.id.tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("新订单").setTag("00000"));
        mTablayout.addTab(mTablayout.newTab().setText("进行中").setTag("05,06"));
        mTablayout.addTab(mTablayout.newTab().setText("已完成").setTag("00,"));

        listheadView = LayoutInflater.from(ShanghuMainActivity.this).inflate(R.layout.list_headview_shanghuorder, null, false);
        mListview_shanghuorder.getRefreshableView().addHeaderView(listheadView);

        textview_ordercount = listheadView.findViewById(R.id.textview_ordercount);
        image_huiyuan = listheadView.findViewById(R.id.image_huiyuan);
        simpleRatingBar = listheadView.findViewById(R.id.SimpleRatingBar);
        textview_money = listheadView.findViewById(R.id.textview_money);
        mTablayout_header = listheadView.findViewById(R.id.tablayout_head);
        textview_huiyuan = listheadView.findViewById(R.id.textview_huiyuan);
        mTablayout_header.addTab(mTablayout_header.newTab().setText("新订单").setTag("00000"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("进行中").setTag("05,06"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("已完成").setTag("00,01,02"));




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

//                transaction = fragmetnmanager.beginTransaction();
//                if (fragment_shanghutask == null) {
//                    fragment_shanghutask = new Fragment_shanghutask();
//                    transaction.replace(R.id.framelayout_main, fragment_shanghutask).commit();
//                } else {
//                    transaction.replace(R.id.framelayout_main, fragment_shanghutask).commit();
//                }
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
            case R.id.image_personcenter:
                LogUtils.LOG("ceshi", Urls.Baseurl + Urls.shopIn_state + Staticdata.static_userBean.getData().getAppuser().getUser_token(), "检测商户审核状态接口");
                if (Staticdata.static_userBean.getData().getAppuser().getIs_business().equals("Y")) {
                    LogUtils.LOG("ceshi", "检测商户审核状态", "检测商户审核状态");
                    Intent intent_shopcenter = new Intent(ShanghuMainActivity.this, ShopCenterNewActivity.class);
                    intent_shopcenter.putExtra("type", 2);//2  商户
                    startActivity(intent_shopcenter);
                }
                break;
        }

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

   public void speak(){

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(ShanghuMainActivity.this, mTtsInitListener);

        if( null == mTts ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            LogUtils.LOG("ceshixunfei","创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化","mainactivity");
            return;
        }

        setParam();

        int code = mTts.startSpeaking("你有新订单", mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
			/*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/

        if (code != ErrorCode.SUCCESS) {
            LogUtils.LOG("ceshixunfei","语音合成失败,错误码: " + code,"mainactivity");

        }
       LogUtils.LOG("ceshi","播放语音，","shanghumainactivity");

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






    void  requestTuisongstate(String state){
        new  Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                int status = 0;
                String msg = "";
                LogUtils.LOG("ceshi", respose, "推送开关");
                try {
                    JSONObject object = new JSONObject(respose);
                    status = (Integer) object.get("code");//
                    msg = (String) object.get("message");//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    ToastUtils.showToast(ShanghuMainActivity.this, msg);
                    requestshopinfo();
                } else {
                    ToastUtils.showToast(ShanghuMainActivity.this, msg);
                }

            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui+Urls.push_on_off+Staticdata.static_userBean.getData().getAppuser().getUser_token()+"&push_on_off="+state,ShanghuMainActivity.this,0);
    }

    void setstar(float stars){
        simpleRatingBar.setNumberOfStars(5);
        simpleRatingBar.setFillColor(getResources().getColor(R.color.yellow_jianbian_start));
        simpleRatingBar.setStarBackgroundColor(getResources().getColor(R.color.gray_background));
        simpleRatingBar.setStepSize((float) 0.1);
        simpleRatingBar.setRating(stars);
        simpleRatingBar.setDrawBorderEnabled(false);
        simpleRatingBar.setStarsSeparation(1);

    }
    void requestshopinfo() {
        String url_info = Urls.Baseurl + Urls.shopcenter + Staticdata.static_userBean.getData()
                .getAppuser().getUser_token() + "&business_no=" + Staticdata.static_userBean.getData().getAppuser()
                .getBusiness_no();
        LogUtils.LOG("ceshi", " 商户网址：" + url_info, "ShopCenterActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "商户中心：" + respose, "ShopCenterActivity");
                shopcenterBean = new Gson().fromJson(respose, ShopcenterBean.class);
                textview_money.setText(shopcenterBean.getData().getList().getCommission()+"");
                textview_ordercount.setText(shopcenterBean.getData().getList().getOrderSum()+"");
//                if(shopcenterBean.getData().getList().getMemberImgUrl()==null){
//                    image_huiyuan.setBackgroundResource(R.mipmap.huiyuanno);
//                }else {
//                    image_huiyuan.setBackgroundResource(R.mipmap.huiyuanyes);
//                }
                Glide.with(ShanghuMainActivity.this).load(shopcenterBean.getData().getList().getIconImgUrl()).into(image_huiyuan);

                if(shopcenterBean.getData().getList().getMemberImgUrl()==null){
                    textview_huiyuan.setText("会员商家未开通");
                }else {
                    textview_huiyuan.setText("会员商家");
                    textview_huiyuan.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.red2));
                }
                setstar((float) shopcenterBean.getData().getList().getEvaluation_star());
                if(shopcenterBean.getData().getList().getPush_on_off().equals("Y")){//推送开关状态
                    text_jiedan.setText("自动接单");
                }else {
                    text_jiedan.setText("关闭接单");
                }
                popwindow_shanghuIsjiedan=new Popwindow_ShanghuIsjiedan(ShanghuMainActivity.this, re_title, new InterfacePermission() {
                    @Override
                    public void onResult(boolean result) {
                        if(result){
                            requestTuisongstate("Y");
                        }else {
                            requestTuisongstate("N");
                        }

                    }
                },text_jiedan.getText().equals("自动接单")?"关闭接单":"自动接单");


            }

            @Override
            public void onError(int error) {

            }
        }).Http(url_info, this, 0);
    }
    void request(String state, final int page) {//请求列表
        String URL = "";
        LogUtils.LOG("ceshi", "state"+state, "Fragment_shanghutask");
        if (state.equals("00000")){//新订单
            URL = Urls.Baseurl + Urls.neworderlist + Staticdata.static_userBean.getData().getAppuser().getUser_token()+ "&business_no=" +
                    Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+ "&x_value=" + Staticdata.xValue + "&y_value=" + Staticdata.yValue+
                    "&pageNum=" + page;
            type=0;
        }else {
            URL = Urls.Baseurl + Urls.shoporder + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_status=" + state +
                    "&curPageNo=" + page;
            type=1;
        }
        LogUtils.LOG("ceshiurl", URL, "Fragment_shanghutask");

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "新订单"+respose, "Fragment_shanghutask");
                if (mListview_shanghuorder.isRefreshing()) {
                    mListview_shanghuorder.onRefreshComplete();
                }
                shanghuneworderBean = new Gson().fromJson(respose, ShanghuneworderBean.class);
                if (page == 1) {
                    mList.clear();
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData().getList());
                    }
                    adapter.settype(type);
                    adapter.notifyDataSetChanged();
                } else {
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData().getList());
                    }
                    adapter.settype(type);
                    adapter.notifyDataSetChanged();
                }
                if(mList.size()==0){
                    iamge_empty.setVisibility(View.VISIBLE);
                }else {
                    iamge_empty.setVisibility(View.GONE);

                }

            }

            @Override
            public void onError(int error) {

            }
        }).Http(URL, this, 0);

    }

    /**
     * 判断是否是第一行
     *
     * @return
     */
    private boolean isScroll() {
        if (mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition() == 1 || mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到高度比例
     *
     * @return
     */
    private float getScrollY() {
        View c = mListview_shanghuorder.getRefreshableView().getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition();
        if (firstVisiblePosition == 1 || firstVisiblePosition == 0) {
            //如果可见的是第一行或第二行，那么开始计算距离比例
            float top = c.getTop();
            //当第一行已经开始消失的时候，top是为负数的，所以取正
            top = Math.abs(top);
            //48为菜单栏的高度，单位为dp
            //得到的高度为ViewPager的高度减去菜单栏高度，即为最大可滑动距离
            float height = c.getHeight() - SizeUtils.dip2px(this, 40);

            float y = top / height;

            return y;
        } else {
            return 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestshopinfo();//
    }
}
