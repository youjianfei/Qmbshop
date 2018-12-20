package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_zhuangbeishangcheng;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.ShangchengliebiaoBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import java.util.ArrayList;
import java.util.List;

public class ZhuangbeiShangchengActivity extends BaseActivityother {

    GridView gridview;

    int  type=4;

    List<ShangchengliebiaoBean.DataBean>  mData;

    Adapter_zhuangbeishangcheng adapter_zhuangbeishangcheng;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_zhuangbei_shangcheng;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        adapter_zhuangbeishangcheng=new Adapter_zhuangbeishangcheng(mData,this);
        gridview.setAdapter(adapter_zhuangbeishangcheng);
        request(4,1);
    }

    @Override
    protected void initListener() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent  intent=new Intent(ZhuangbeiShangchengActivity.this,ZhuangbeidetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initView() {
        gridview=findViewById(R.id.gridview);

    }

    void request(int  type,int page){
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi",respose,"装备列表");
                mData.clear();
                mData.addAll(new Gson().fromJson(respose,ShangchengliebiaoBean.class).getData());
                adapter_zhuangbeishangcheng.notifyDataSetChanged();

            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui+Urls.zhuangbeiliebiao+
                Staticdata.static_userBean.getData().getAppuser().getUser_token()
                +"&type="+type+"&pageNum="+page,this,0);

//        LogUtils.LOG("ceshi",Urls.Baseurl_cui+Urls.zhuangbeiliebiao+
//                Staticdata.static_userBean.getData().getAppuser().getUser_token()
//                +"&type="+type+"&pageNum="+page,"装备列表url");

    }
}
