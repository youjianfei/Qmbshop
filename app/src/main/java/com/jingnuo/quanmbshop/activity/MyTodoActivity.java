package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_mytodo;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyTodoBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTodoActivity extends BaseActivityother {

    //控件
    TabLayout mTablayout;
   ListView mListView;


    Adapter_mytodo adapter;

    //数据
    int  page=1;
    int type=0;
    String state="";
    MyTodoBean myTodoBean;
    List<MyTodoBean.DataBean.ListBean>  mdata;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_my_todo;
    }

    @Override
    protected void setData() {


    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        mdata=new ArrayList<>();
        request("",page);
        adapter=new Adapter_mytodo(mdata,this);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
//        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page=1;
//                request(state,page);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page++;
//                request(state,page);
//            }
//        });
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.LOG("ceshi",tab.getTag()+"","mytodo");

                 state= (String) tab.getTag();
                page=1;
                request(state,page);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent  intent=new Intent(MyTodoActivity.this,HelperOrderActivity.class);
                intent.putExtra("type",type);
                LogUtils.LOG("ceshi","列表数"+mdata.size()+"点击位置"+position,"sadfasfd");
                intent.putExtra("order_no",mdata.get(position-1).getOrder_no()+"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        mTablayout=findViewById(R.id.tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("全部").setTag(""));
        mTablayout.addTab(mTablayout.newTab().setText("进行中").setTag("05,06"));
        mTablayout.addTab(mTablayout.newTab().setText("已完成").setTag("00,"));
        mTablayout.addTab(mTablayout.newTab().setText("已关闭").setTag("01,02"));

        mListView=findViewById(R.id.list_myorder);
    }
    void request(String state, final int  page){
        String URL="";
        if(type==1){
            URL=Urls.Baseurl+Urls.helporder+Staticdata.static_userBean.getData().getAppuser().getUser_token()+"&client_no="+
                    Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+"&order_status="+state+
                    "&curPageNo="+page;
        }else {
             URL=Urls.Baseurl+Urls.shoporder+Staticdata.static_userBean.getData().getAppuser().getUser_token()+"&client_no="+
                    Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+"&order_status="+state+
                    "&curPageNo="+page;
        }
        LogUtils.LOG("ceshi",URL,"MyTodoActivity的URl");

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi",respose,"MyTodoActivity");

                myTodoBean=new  Gson().fromJson(respose,MyTodoBean.class);
                if(page==1){
                    mdata.clear();
                    if(myTodoBean.getData()!=null){
                        mdata.addAll(new Gson().fromJson(respose,MyTodoBean.class).getData().getList());
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    if(myTodoBean.getData()!=null){
                        mdata.addAll(new Gson().fromJson(respose,MyTodoBean.class).getData().getList());
                    }
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int error) {

            }
        }).Http(URL,MyTodoActivity.this,0);

    }
}
