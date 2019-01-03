package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jingnuo.quanmbshop.Adapter.Adapter_SystemmessageList;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.SystemmessageBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemMessageActivity extends BaseActivityother {

    //控件
    SmartRefreshLayout SmartRefreshLayout_refreshLayout;
    ListView  mListview;
    ImageView mImage_view_empty;

    //数据
    Map map_message;

    List<SystemmessageBean.DataBean>mData;

    int page=1;

    //对象
    Adapter_SystemmessageList adapter_systemmessageList;
    SystemmessageBean systemmessageBean;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        adapter_systemmessageList=new Adapter_SystemmessageList(mData,this);
        mListview.setAdapter(adapter_systemmessageList);

        map_message=new HashMap();
        map_message.put("pageNo",page+"");
        map_message.put("type","1");
        if(Staticdata.isLogin){
            map_message.put("receive_client_no",Staticdata.static_userBean.getData().getAppuser().getBusiness_no());//获取系统消息不用客户号
            map_message.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
        }
        LogUtils.LOG("ceshi","系统消息内容map"+map_message,"SystemMessageActivity");
        requestSystermyMessage(map_message);
    }

    @Override
    protected void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_messagedetail=new Intent(SystemMessageActivity.this,SystemmessageDetailActivity.class);
                intent_messagedetail.putExtra("title",mData.get(position).getTitle());
                intent_messagedetail.putExtra("content",mData.get(position).getContent());
                intent_messagedetail.putExtra("time",mData.get(position).getCreateDate());
                intent_messagedetail.putExtra("url",mData.get(position).getClick_url());
                startActivity(intent_messagedetail);
            }
        });
        //下拉  上拉 加载刷新
//        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });

        SmartRefreshLayout_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                map_message.put("pageNo",page+"");
                requestSystermyMessage(map_message);
            }
        });
        SmartRefreshLayout_refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                map_message.put("pageNo",page+"");
                requestSystermyMessage(map_message);
            }
        });
    }

    @Override
    protected void initView() {
        SmartRefreshLayout_refreshLayout=findViewById(R.id.SmartRefreshLayout_refreshLayout);
        mListview=findViewById(R.id.list_systerme);
        mImage_view_empty=findViewById(R.id.image_empty);
    }

    void requestSystermyMessage(Map map){
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                SmartRefreshLayout_refreshLayout.finishLoadMore();
                SmartRefreshLayout_refreshLayout.finishRefresh();
                systemmessageBean=new Gson().fromJson(respose,SystemmessageBean.class);
                if(page==1){
                    mData.clear();
                    mData.addAll(systemmessageBean.getData());
                    adapter_systemmessageList.notifyDataSetChanged();
                    mImage_view_empty.setVisibility(mData.size()==0? View.VISIBLE:View.GONE);
                }else {
                    mData.addAll(systemmessageBean.getData());
                    adapter_systemmessageList.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int error) {
                SmartRefreshLayout_refreshLayout.finishLoadMore();
                SmartRefreshLayout_refreshLayout.finishRefresh();
            }
        }).postHttp(Urls.Baseurl_hu+Urls.pushMessage,SystemMessageActivity.this,1,map);



    }
}
