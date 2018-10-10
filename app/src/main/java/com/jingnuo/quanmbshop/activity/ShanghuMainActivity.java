package com.jingnuo.quanmbshop.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jingnuo.quanmbshop.Adapter.Adapter_mytodo;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyTodoBean;
import com.jingnuo.quanmbshop.fargment.Fragment_shanghutask;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import java.util.ArrayList;
import java.util.List;

public class ShanghuMainActivity extends BaseActivityother {
    //控件
    LinearLayout LinearLayout_taskmain;
    LinearLayout LinearLayout_messagemain;

    ImageView imageview_task;
    ImageView image_messgae;
    TextView  text_task;
    TextView  text_message;


    //对象
    Fragment_shanghutask fragment_shanghutask;


    FragmentManager fragmetnmanager;
    FragmentTransaction transaction;
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

    @Override
    protected void initData() {
        fragment_shanghutask = new Fragment_shanghutask();
        fragmetnmanager = getFragmentManager();
        transaction = fragmetnmanager.beginTransaction();
        transaction.add(R.id.framelayout_main, fragment_shanghutask).commit();
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
//                    transaction.replace(R.id.framelayout_main, fragmentTskZhaoRenShou).commit();
                }
                break;
            case R.id.LinearLayout_messagemain://点击消息
                LinearLayout_messagemain.setSelected(true);
                LinearLayout_taskmain.setSelected(false);
                image_messgae.setSelected(true);
                imageview_task.setSelected(false);
                text_task.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.black_text2));
                text_message.setTextColor(ShanghuMainActivity.this.getResources().getColor(R.color.yellow_jianbian_end));

                transaction = fragmetnmanager.beginTransaction();
//                if (fragment_shanghutask == null) {
//                    fragment_shanghutask = new Fragment_shanghutask();
//                    transaction.replace(R.id.framelayout_main, fragment_shanghutask).commit();
//                } else {
////                    transaction.replace(R.id.framelayout_main, fragmentTskZhaoRenShou).commit();
//                }

                break;
        }

    }

}
