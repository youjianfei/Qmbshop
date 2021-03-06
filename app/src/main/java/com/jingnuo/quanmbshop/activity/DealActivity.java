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
import com.jingnuo.quanmbshop.Adapter.Adapter_DealmessageList;
import com.jingnuo.quanmbshop.Adapter.Adapter_SystemmessageList;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.DealMessageBean;
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

public class DealActivity extends BaseActivityother {


    //控件
    SmartRefreshLayout SmartRefreshLayout_refreshLayout;
    ListView mListview;
    ImageView mImage_view_empty;
    //数据
    Map map_message;

    DealMessageBean dealMessageBean;
    List<DealMessageBean.DataBean> mData;

    int page=1;
    int  type=2;//  //1帮手  2  商户

    //对象
    Adapter_DealmessageList adapter_dealmessageList;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_deal;
    }

    @Override
    protected void setData() {
//        if(Staticdata.static_userBean.getData().getHelper_status()==1){
//            type=1;//帮手身份
//        }
//        if(Staticdata.static_userBean.getData().getBusiness_status()==1){
//            type=2;//商户身份
//        }


    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        adapter_dealmessageList=new Adapter_DealmessageList(mData,this);
        mListview.setAdapter(adapter_dealmessageList);

        map_message=new HashMap();
        map_message.put("pageNo",page+"");
        map_message.put("type","3");
        if(Staticdata.static_userBean.getData()==null){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        map_message.put("receive_client_no", Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
        map_message.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
        LogUtils.LOG("ceshi","系统消息内容map"+map_message,"DealMessageActivity");
        requestDealmessage(map_message);
    }

    @Override
    protected void initListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.LOG("ceshi","点击了"+position,"交易信息");
                String binding_id=mData.get(position).getBinding_id();

                if(binding_id.contains("QMB")){
                    Intent intent=new Intent(DealActivity.this,HelperOrderActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("order_no",binding_id);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(DealActivity.this,MytaskDetailActivity.class);
                    intent.putExtra("id",binding_id);
                    startActivity(intent);
                }
            }
        });
        SmartRefreshLayout_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                map_message.put("pageNo",page+"");
                requestDealmessage(map_message);
            }
        });
        SmartRefreshLayout_refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                map_message.put("pageNo",page+"");
                requestDealmessage(map_message);
            }
        });

        //下拉  上拉 加载刷新
//        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page = 1;
//                map_message.put("pageNo",page+"");
//                requestDealmessage(map_message);
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page++;
//                map_message.put("pageNo",page+"");
//                requestDealmessage(map_message);
//            }
//        });

    }

    @Override
    protected void initView() {
        mListview=findViewById(R.id.list_dealmessage);
        mImage_view_empty=findViewById(R.id.image_empty);
        SmartRefreshLayout_refreshLayout=findViewById(R.id.SmartRefreshLayout_refreshLayout);
    }


    void requestDealmessage(Map map){
        LogUtils.LOG("ceshi","交易消息内容URL"+Urls.Baseurl_hu+Urls.pushMessage,"DealMessageActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
//                if (mListview.isRefreshing()) {
//                    mListview.onRefreshComplete();
//                }
                SmartRefreshLayout_refreshLayout.finishLoadMore();
                SmartRefreshLayout_refreshLayout.finishRefresh();
                LogUtils.LOG("ceshi","交易消息内容"+respose,"DealMessageActivity");
                dealMessageBean=new Gson().fromJson(respose,DealMessageBean.class);
                if(page==1){
                    mData.clear();
                    if(dealMessageBean.getData()!=null){
                        mData.addAll(dealMessageBean.getData());
                        adapter_dealmessageList.notifyDataSetChanged();
                        mImage_view_empty.setVisibility(mData.size()==0? View.VISIBLE:View.GONE);
                    }
                }else {
                    if(dealMessageBean.getData()!=null){
                        mData.addAll(dealMessageBean.getData());
                        adapter_dealmessageList.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(int error) {
                SmartRefreshLayout_refreshLayout.finishLoadMore();
                SmartRefreshLayout_refreshLayout.finishRefresh();
            }
        }).postHttp(Urls.Baseurl_hu+Urls.pushMessage,DealActivity.this,1,map);



    }
}
