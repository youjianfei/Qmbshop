package com.jingnuo.quanmbshop.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_Gridviewpic;
import com.jingnuo.quanmbshop.Adapter.Adapter_Gridviewpic_skillsdetails;
import com.jingnuo.quanmbshop.Interface.Interence_bargin;
import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.Interence_complteTask_time;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.customview.MyGridView;
import com.jingnuo.quanmbshop.popwinow.Popwindow_CompleteTime;
import com.jingnuo.quanmbshop.popwinow.Popwindow_addPrice;
import com.jingnuo.quanmbshop.popwinow.Popwindow_complatetask;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.HelpOrderBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.master.permissionhelper.PermissionHelper;
import com.jingnuo.quanmbshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelperOrderActivity extends BaseActivityother {

    CircleImageView mImageview_head;
    ImageView iv_back;
        TextView text_title;//订单状态
    TextView mTextview_state;//状态
    TextView mTextview_money;//佣金
    TextView mTextview_time;//发布时间
    TextView mTextview_yuyueshijian;//预约时间
    TextView mTextview_peoplename;//雇主
    TextView text_lianxiren;//联系人
    TextView mTextview_taskDetail;//任务描述
    TextView mTextview_address;//地点
    TextView textview_sendmessage;//发短信
    //    TextView mTextview_phonenumber;//客户电话
    LinearLayout mLinearlayout_tel;
    LinearLayout linearlayout_star;//开始服务

    TextView mButton_queren;


    MyGridView imageGridview;
    String image_url = "";
    List<String> imageview_urllist;
    Adapter_Gridviewpic_skillsdetails adapter_gridviewpic;


    //对象
//    Popwindow_complatetask popwindow_complatetask;
    HelpOrderBean helpOrderBean;
    //数据
    String order_no = "";
    int type = 0;  //1帮手  2  商户
    int whichactivity = 0;//判断点击返回是否跳转到帮帮广场   1 是

    String tel = "";//雇主电话
    PermissionHelper mPermission;//动态申请权限

    long chazhi = 0;//时间戳差值

    @Override
    public int setLayoutResID() {
        return R.layout.activity_helper_order;
    }

    @Override
    protected void setData() {
        imageview_urllist = new ArrayList<>();
        adapter_gridviewpic = new Adapter_Gridviewpic_skillsdetails(imageview_urllist, this);
        imageGridview.setAdapter(adapter_gridviewpic);

    }

    @Override
    protected void initData() {
        mPermission = new PermissionHelper(this, new String[]{Manifest.permission.CALL_PHONE}, 100);
        order_no = getIntent().getStringExtra("order_no");
        type = getIntent().getIntExtra("type", 0);
//        text_title.setText(type==1?"帮手订单":"商户订单");
        whichactivity = getIntent().getIntExtra("whichactivity", 0);
        request();

    }

    void setImage(String image) {
        if (image == null || image.equals("")) {

        } else {
            String[] images = image.split(",");
            int len = images.length;
            LogUtils.LOG("ceshi", "图片的个数" + images.length, "SkillDetailActivity分隔图片");
            imageview_urllist.clear();
            for (int i = 0; i < len; i++) {
                imageview_urllist.add(images[i]);
            }
            imageGridview.setVisibility(imageview_urllist.size() > 0 ? View.VISIBLE : View.GONE);
            adapter_gridviewpic.notifyDataSetChanged();

        }

    }

    void request() {
        LogUtils.LOG("ceshi", "帮手订单+" + Urls.Baseurl + Urls.shoporderdetail + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&orderId=" + order_no, "帮手订单网址");
        String URL = "";
//        if (type == 1) {
//            URL = Urls.Baseurl + Urls.helporderdetail + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_no=" + order_no;
//        } else
        {
            URL = Urls.Baseurl + Urls.shoporderdetail + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_no=" + order_no;
        }

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                mButton_queren.setEnabled(true);
                LogUtils.LOG("ceshi", "帮手订单+" + respose, "帮手订单网址");
                helpOrderBean = new Gson().fromJson(respose, HelpOrderBean.class);
                Glide.with(HelperOrderActivity.this).load(helpOrderBean.getData().getDetail().getHeadUrl()).into(mImageview_head);
                mTextview_state.setText(helpOrderBean.getData().getDetail().getOrder_status());
                mTextview_money.setText(helpOrderBean.getData().getDetail().getOrder_amount() + "元");
                mTextview_time.setText("发布时间：" + helpOrderBean.getData().getDetail().getTask_StartDate());
                mTextview_yuyueshijian.setText(helpOrderBean.getData().getDetail().getTask_time());

//                mTextview_resttime.setText(time);
                image_url = helpOrderBean.getData().getDetail().getTask_Img_Url();
                setImage(image_url);
                mTextview_peoplename.setText(helpOrderBean.getData().getDetail().getNick_name());
//                String sex=helpOrderBean.getData().getDetail().getClient_sex().equals("0")?"（先生）":"（女士）";
                text_lianxiren.setText(helpOrderBean.getData().getDetail().getMobile_no());


                mTextview_taskDetail.setText(helpOrderBean.getData().getDetail().getTask_description());
                mTextview_address.setText(helpOrderBean.getData().getDetail().getDetailed_address() + helpOrderBean.getData().getDetail().getHouseNumber());
//                mTextview_phonenumber.setText(helpOrderBean.getData().getDetail().getMobile_no());
                tel = helpOrderBean.getData().getDetail().getMobile_no();
                String state = helpOrderBean.getData().getDetail().getOrder_status_code();
                if (state.equals("07")||state.equals("09")) {
                    mButton_queren.setVisibility(View.GONE);
                    linearlayout_star.setVisibility(View.VISIBLE);
                    textview_sendmessage.setVisibility(View.VISIBLE);
                } else {
                    mButton_queren.setVisibility(View.VISIBLE);
                    linearlayout_star.setVisibility(View.GONE);
                    textview_sendmessage.setVisibility(View.GONE);
                }
                if (state.equals("06")) {
                    mButton_queren.setEnabled(false);
                    mButton_queren.setText("等待雇主确认");
                } else if (state.equals("00")) {
                    mButton_queren.setEnabled(false);
                    mButton_queren.setText("已完成");
                    text_title.setText("订单已完成");
                } else if (state.equals("02")||state.equals("01")) {
                    mButton_queren.setEnabled(false);
                    mButton_queren.setText("已关闭");
                    text_title.setText("订单已关闭");
                } else {
                    mButton_queren.setEnabled(true);
                }

            }

            @Override
            public void onError(int error) {
                request();
            }
        }).Http(URL, HelperOrderActivity.this, 0);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        mButton_queren.setOnClickListener(this);
        linearlayout_star.setOnClickListener(this);
        textview_sendmessage.setOnClickListener(this);
        mLinearlayout_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tel.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + tel);
                    intent.setData(data);

                    if (ActivityCompat.checkSelfPermission(HelperOrderActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

//                    ToastUtils.showToast(mContext,"拨打电话权限被你拒绝，请在手机设置中开启");
                        mPermission.request(new PermissionHelper.PermissionCallback() {
                            @Override
                            public void onPermissionGranted() {

                            }

                            @Override
                            public void onIndividualPermissionGranted(String[] grantedPermission) {

                            }

                            @Override
                            public void onPermissionDenied() {

                            }

                            @Override
                            public void onPermissionDeniedBySystem() {

                            }
                        });
                        return;
                    }
                    startActivity(intent);//调用具体方法
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_star://开始任务
                new Volley_Utils(new Interface_volley_respose() {
                    @Override
                    public void onSuccesses(String respose) {
                        LogUtils.LOG("ceshi", respose, "开始任务");

                        int status = 0;
                        String msg = "";
                        try {
                            JSONObject object = new JSONObject(respose);
                            status = (Integer) object.get("code");//登录状态
                            msg = (String) object.get("message");//登录返回信息
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status==1){
                            request();
                        }else {
                            ToastUtils.showToast(HelperOrderActivity.this,msg);
                        }
                    }

                    @Override
                    public void onError(int error) {

                    }
                }).Http(Urls.Baseurl_cui + Urls.startTask + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_no=" + order_no, HelperOrderActivity.this, 0);

                break;
            case R.id.textview_sendmessage://发送短信
                new Volley_Utils(new Interface_volley_respose() {
                    @Override
                    public void onSuccesses(String respose) {
                        LogUtils.LOG("ceshi", respose, "短信通知");

                        int status = 0;
                        String msg = "";
                        try {
                            JSONObject object = new JSONObject(respose);
                            status = (Integer) object.get("code");//登录状态
                            msg = (String) object.get("message");//登录返回信息
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ToastUtils.showToast(HelperOrderActivity.this,msg);
                    }

                    @Override
                    public void onError(int error) {

                    }
                }).Http(Urls.Baseurl_cui + Urls.sendmessage + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_no=" + order_no, HelperOrderActivity.this, 0);


                break;

            case R.id.iv_back:
                if (whichactivity == 1) {
//                    Intent intent=new Intent(HelperOrderActivity.this,SquareActuvity.class);
//                    startActivity(intent);
                } else {
                    finish();
                }
                break;
            case R.id.button_bargain://申请完成任务
                if (helpOrderBean.getData().getDetail().getApp_type().equals("1")) {//匹配订单申请完成
                    new Popwindow_addPrice(HelperOrderActivity.this, new Interence_bargin() {
                        @Override
                        public void onResult(String result) {
                            if (Double.parseDouble(result) < 5) {
                                ToastUtils.showToast(HelperOrderActivity.this, "金额不得低于5元");
                                return;
                            }
                            LogUtils.LOG("ceshi", result, "aa");
                            new Volley_Utils(new Interface_volley_respose() {
                                @Override
                                public void onSuccesses(String respose) {
                                    LogUtils.LOG("ceshi", respose, "申请完成");
                                    int status = 0;
                                    String msg = "";
                                    try {
                                        JSONObject object = new JSONObject(respose);
                                        status = (Integer) object.get("code");//登录状态
                                        msg = (String) object.get("message");//登录返回信息
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (status == 1) {
                                        ToastUtils.showToast(HelperOrderActivity.this, msg);
                                        request();
                                    } else {
                                        request();
                                        ToastUtils.showToast(HelperOrderActivity.this, msg);
                                    }
                                }

                                @Override
                                public void onError(int error) {
                                    request();
                                }
                            }).Http(Urls.Baseurl_cui + Urls.applyPipeicompletetask + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
                                    "&order_no=" + helpOrderBean.getData().getDetail().getOrder_no() +
                                    "&order_amount=" + result, HelperOrderActivity.this, 0);


                        }
                    }).showpop();

                }
//                else {//广场申请完成
//                    new Popwindow_complatetask(HelperOrderActivity.this, new Interence_complteTask() {
//                        @Override
//                        public void onResult(boolean result) {
//                            if (result) {
//                                LogUtils.LOG("ceshi", "申请完成任务接口+" + Urls.Baseurl_cui + Urls.applycompletetask + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
//                                        "&order_no=" + helpOrderBean.getData().getDetail().getOrder_no() +
//                                        "&task_id=" + helpOrderBean.getData().getDetail().getTask_id(), "申请完成任务");
//                                new Volley_Utils(new Interface_volley_respose() {
//                                    @Override
//                                    public void onSuccesses(String respose) {
//                                        LogUtils.LOG("ceshi", respose, "申请完成");
//                                        int status = 0;
//                                        String msg = "";
//                                        try {
//                                            JSONObject object = new JSONObject(respose);
//                                            status = (Integer) object.get("code");//登录状态
//                                            msg = (String) object.get("message");//登录返回信息
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                        if (status == 1) {
//                                            ToastUtils.showToast(HelperOrderActivity.this, msg);
//                                            request();
//                                        } else {
//                                            request();
//                                            ToastUtils.showToast(HelperOrderActivity.this, msg);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(int error) {
//                                        request();
//                                    }
//                                }).Http(Urls.Baseurl_cui + Urls.applycompletetask + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
//                                        "&order_no=" + helpOrderBean.getData().getDetail().getOrder_no() +
//                                        "&task_id=" + helpOrderBean.getData().getDetail().getTask_id(), HelperOrderActivity.this, 0);
//                            }
//
//                        }
//                    }).showPopwindow();
//                }
                break;


        }

    }

    @Override
    public void onBackPressed() {
        if (whichactivity == 1) {
//            Intent intent=new Intent(HelperOrderActivity.this,SquareActuvity.class);
//            intent.putExtra("refresh",1);
//            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    protected void initView() {
        mImageview_head = findViewById(R.id.image_task);
        iv_back = findViewById(R.id.iv_back);
        text_title = findViewById(R.id.text_title);
        mTextview_state = findViewById(R.id.text_taskstate);
        mTextview_money = findViewById(R.id.text_taskmoney_);
        mTextview_time = findViewById(R.id.text_tasktime);
        mTextview_yuyueshijian = findViewById(R.id.text_time);
        mTextview_peoplename = findViewById(R.id.text_name);
        text_lianxiren = findViewById(R.id.text_lianxiren);
        mTextview_taskDetail = findViewById(R.id.text_taskdetail);
        mTextview_address = findViewById(R.id.text_address);
        textview_sendmessage = findViewById(R.id.textview_sendmessage);
//        mTextview_phonenumber=findViewById(R.id.text_number);
        imageGridview = findViewById(R.id.GridView_PIC);
        mButton_queren = findViewById(R.id.button_bargain);
        mLinearlayout_tel = findViewById(R.id.linearlayout_tel);
        linearlayout_star = findViewById(R.id.linearlayout_star);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermission != null) {
            mPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
