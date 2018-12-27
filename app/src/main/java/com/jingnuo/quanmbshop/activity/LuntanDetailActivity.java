package com.jingnuo.quanmbshop.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_LuntanDetail_pic;
import com.jingnuo.quanmbshop.Adapter.Adapter_LuntanDetailsHuifu;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.customview.MyListView;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.BBSXiangqingBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_lookpic;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuntanDetailActivity extends BaseActivityother {
    TextView textview_content;
    TextView textview_zhuanfaCount;//分享数
    TextView textview_huifuCount;//评论数
    TextView textview_dianzanCount;//点赞数
    TextView textview_loadmore;//展开更多
    MyListView listview_detailsPIC;//详情图片
    MyListView mylistview_allHuifu;//详情下面回复列表
    LinearLayout linearlayout_dianzan;
    ImageView image_dianzan;

    TextView textview_huifu;//评论确定按钮
    EditText edit_pinglun;//评论确定按钮

    Adapter_LuntanDetail_pic adapter_luntanDetail_pic;
    Adapter_LuntanDetailsHuifu adapter_luntanDetailsHuifu;//评论 回复afapter

    BBSXiangqingBean bbsXiangqingBean;

    List<BBSXiangqingBean.DataBean.CommentListBean> mData_pinlun;//评论data
    List<String> mData_pic;//详情图片

    String ID = "";//论坛ID
    String pinglunContent = "";

    int PAGE = 1;
    InputMethodManager imm;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_luntan_detail;
    }

    @Override
    protected void setData() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        requestXiangxing(PAGE);
    }

    @Override
    protected void initData() {
        ID = getIntent().getStringExtra("luntanID");
        mData_pinlun = new ArrayList<>();//评论 回复

        mData_pic = new ArrayList<>();//详情图片

        adapter_luntanDetail_pic = new Adapter_LuntanDetail_pic(mData_pic, this);
        listview_detailsPIC.setAdapter(adapter_luntanDetail_pic);
        adapter_luntanDetail_pic.notifyDataSetChanged();
        adapter_luntanDetailsHuifu = new Adapter_LuntanDetailsHuifu(mData_pinlun, this, new InterfacePermission() {
            @Override
            public void onResult(boolean result) {
                adapter_luntanDetailsHuifu.notifyDataSetChanged();
            }
        });
        mylistview_allHuifu.setAdapter(adapter_luntanDetailsHuifu);
    }

    @Override
    protected void initListener() {
        //详情页查看图片
        listview_detailsPIC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new Popwindow_lookpic(LuntanDetailActivity.this).showPopwindow(position,mData_pic);
            }
        });

        //详情页点赞
        linearlayout_dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Volley_Utils(new Interface_volley_respose() {
                    @Override
                    public void onSuccesses(String respose) {
                        int status = 0;
                        String msg = "";
                        String imageID = "";
                        try {
                            JSONObject object = new JSONObject(respose);
                            status = (Integer) object.get("code");//
                            msg = (String) object.get("message");//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status == 1) {
                            if (bbsXiangqingBean.getData().getRoot().getIsLike().contains("N")) {
                                bbsXiangqingBean.getData().getRoot().setIsLike("Y");
                                bbsXiangqingBean.getData().getRoot().setLikes(bbsXiangqingBean.getData().getRoot().getLikes() + 1);
                                image_dianzan.setSelected(true);
                                textview_dianzanCount.setText(bbsXiangqingBean.getData().getRoot().getLikes() + "");

                            } else {
                                bbsXiangqingBean.getData().getRoot().setIsLike("N");
                                bbsXiangqingBean.getData().getRoot().setLikes(bbsXiangqingBean.getData().getRoot().getLikes() - 1);
                                image_dianzan.setSelected(false);
                                textview_dianzanCount.setText(bbsXiangqingBean.getData().getRoot().getLikes() + "");

                            }
                        }
                        ToastUtils.showToast(LuntanDetailActivity.this, msg);


                    }

                    @Override
                    public void onError(int error) {

                    }
                }).Http(Urls.Baseurl_cui + Urls.bbs_dianzan + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
                        "&ID=" + bbsXiangqingBean.getData().getRoot().getID(), LuntanDetailActivity.this, 0);
            }
        });
        textview_loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PAGE++;
                requestXiangxing(PAGE);
            }
        });
        textview_huifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pinglunContent = edit_pinglun.getText() + "";
                pinglunContent = pinglunContent.trim();
                if (pinglunContent.equals("")) {
                    ToastUtils.showToast(LuntanDetailActivity.this, "请输入有效内容");
                } else {
                    Map map_pinglun = new HashMap();
                    map_pinglun.put("rootId", bbsXiangqingBean.getData().getRoot().getID() + "");
                    map_pinglun.put("ID", bbsXiangqingBean.getData().getRoot().getID() + "");
                    map_pinglun.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
                    map_pinglun.put("content", pinglunContent);
                    requestPinglun(map_pinglun);
                }

            }
        });
    }

    @Override
    protected void initView() {
        textview_content = findViewById(R.id.textview_content);
        textview_zhuanfaCount = findViewById(R.id.textview_zhuanfaCount);
        textview_huifuCount = findViewById(R.id.textview_huifuCount);
        textview_dianzanCount = findViewById(R.id.textview_dianzanCount);
        textview_loadmore = findViewById(R.id.textview_loadmore);
        listview_detailsPIC = findViewById(R.id.listview_detailsPIC);
        mylistview_allHuifu = findViewById(R.id.mylistview_allHuifu);
        linearlayout_dianzan = findViewById(R.id.linearlayout_dianzan);
        image_dianzan = findViewById(R.id.image_dianzan);
        textview_huifu = findViewById(R.id.textview_huifu);
        edit_pinglun = findViewById(R.id.edit_pinglun);
        listview_detailsPIC.setFocusable(false);
        mylistview_allHuifu.setFocusable(false);
    }

    //论坛详情请求
    void requestXiangxing(final int page) {
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "论坛详情");
                bbsXiangqingBean = new Gson().fromJson(respose, BBSXiangqingBean.class);
                if (bbsXiangqingBean.getData().getRoot() != null) {

                    textview_content.setText(bbsXiangqingBean.getData().getRoot().getContent());
                    if (bbsXiangqingBean.getData().getRoot().getForwards() != 0) {//分享数
                        textview_zhuanfaCount.setText(bbsXiangqingBean.getData().getRoot().getForwards() + "");
                    }
                    if (bbsXiangqingBean.getData().getRoot().getLikes() != 0) {//点赞数
                        textview_dianzanCount.setText(bbsXiangqingBean.getData().getRoot().getLikes() + "");
                    }
                    image_dianzan.setSelected(bbsXiangqingBean.getData().getRoot().getIsLike().contains("N")?false:true);
                    if (bbsXiangqingBean.getData().getRoot().getCommentCount() != 0) {//评论数
                        textview_huifuCount.setText(bbsXiangqingBean.getData().getRoot().getCommentCount() + "");
                    }

                    if (bbsXiangqingBean.getData().getRoot().getImg_url().equals("")) {//图片
                        listview_detailsPIC.setVisibility(View.GONE);
                    } else {
                        listview_detailsPIC.setVisibility(View.VISIBLE);
                        mData_pic.clear();
                        String[] images = bbsXiangqingBean.getData().getRoot().getImg_url().split(",");
                        int len = images.length;
                        for (int i = 0; i < len; i++) {
                            mData_pic.add(images[i]);
                        }
                        adapter_luntanDetail_pic.notifyDataSetChanged();
                    }

                }
                //评论
                if (bbsXiangqingBean.getData().getCommentList().size() != 0) {
                    textview_loadmore.setText("展开更多回复");
                    textview_loadmore.setEnabled(true);
                    if (page == 1) {
                        mData_pinlun.clear();
                        mData_pinlun.addAll(bbsXiangqingBean.getData().getCommentList());
                        adapter_luntanDetailsHuifu.notifyDataSetChanged();
                    } else {
                        mData_pinlun.addAll(bbsXiangqingBean.getData().getCommentList());
                        adapter_luntanDetailsHuifu.notifyDataSetChanged();
                    }

                } else {
                    textview_loadmore.setEnabled(false);
                    textview_loadmore.setText("暂无更多回复");
                }


            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui + Urls.bbs_xiangqing + Staticdata.static_userBean.getData().getAppuser().getUser_token() +
                "&ID=" + ID + "&pageNum=" + page, this, 0);
    }

    //评论请求
    void requestPinglun(Map map) {
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "详情评论");
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
                    ToastUtils.showToast(LuntanDetailActivity.this, msg);
                    requestXiangxing(PAGE);
                    edit_pinglun.setText("");//

                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                } else {
                    ToastUtils.showToast(LuntanDetailActivity.this, msg);
                }


            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_cui + Urls.bbs_xiangqingpinglun, LuntanDetailActivity.this, 1, map);
    }
}
