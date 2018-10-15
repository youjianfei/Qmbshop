package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jingnuo.quanmbshop.Adapter.Adapter_BarginmessageList;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.BargainMessageListBean;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarginmessageListActivity extends BaseActivityother {

    //控件
    PullToRefreshListView mList_view;
    ImageView mImage_view_empty;
    //数据
    Map map_message;
    int page=1;
    BargainMessageListBean bargainMessageListBean;
    List<BargainMessageListBean.DataBean> mData;

    //对象
    Adapter_BarginmessageList adapter_barginmessageList;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_barginmessage_list;
    }

    @Override
    protected void setData() {


    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        adapter_barginmessageList=new Adapter_BarginmessageList(mData,this);
        mList_view.setAdapter(adapter_barginmessageList);

        map_message=new HashMap();
        map_message.put("pageNo",page+"");
        map_message.put("type","2");
        if(Staticdata.static_userBean.getData()==null){
            Intent intent=new Intent(BarginmessageListActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        map_message.put("receive_client_no", Staticdata.static_userBean.getData().getAppuser().getClient_no());
        map_message.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
        LogUtils.LOG("ceshi","系统消息内容map"+map_message,"SystemMessageActivity");
        requestBarginMessage(map_message);


    }

    @Override
    protected void initListener() {
        //下拉  上拉 加载刷新
        mList_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                map_message.put("pageNo",page+"");
                requestBarginMessage(map_message);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                map_message.put("pageNo",page+"");
                requestBarginMessage(map_message);
            }
        });

        mList_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_bargain=new Intent(BarginmessageListActivity.this,BargainActivity.class);
                intent_bargain.putExtra("binding_id",mData.get(position-1).getBinding_id()+"");
                intent_bargain.putExtra("receive_client_no",mData.get(position-1).getReceive_client_no()+"");
                intent_bargain.putExtra("send_client_no",mData.get(position-1).getSend_client_no()+"");
                intent_bargain.putExtra("content",mData.get(position-1).getContent()+"");
                startActivity(intent_bargain);
            }
        });
    }

    @Override
    protected void initView() {
        mList_view=findViewById(R.id.list_bargin);
        mImage_view_empty=findViewById(R.id.image_empty);

    }

    void requestBarginMessage(Map map){
        LogUtils.LOG("ceshi",Urls.Baseurl_hu+Urls.pushMessage,"还价消息");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                if (mList_view.isRefreshing()) {
                    mList_view.onRefreshComplete();
                }
                bargainMessageListBean=new Gson().fromJson(respose,BargainMessageListBean.class);
                if(page==1){
                    mData.clear();
                    mData.addAll(bargainMessageListBean.getData());
                    adapter_barginmessageList.notifyDataSetChanged();
                    mImage_view_empty.setVisibility(mData.size()==0? View.VISIBLE:View.GONE);
                }else {
                    mData.addAll(bargainMessageListBean.getData());
                    adapter_barginmessageList.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(int error) {
                if (mList_view.isRefreshing()) {
                    mList_view.onRefreshComplete();
                }
            }
        }).postHttp(Urls.Baseurl_hu+Urls.pushMessage,BarginmessageListActivity.this,1,map);
    }
}
