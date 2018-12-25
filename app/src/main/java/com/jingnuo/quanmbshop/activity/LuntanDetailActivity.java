package com.jingnuo.quanmbshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jingnuo.quanmbshop.Adapter.Adapter_LuntanDetail_pic;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.customview.MyListView;

import java.util.ArrayList;
import java.util.List;

public class LuntanDetailActivity extends BaseActivityother {
    MyListView listview_detailsPIC;//详情图片
    MyListView mylistview_allHuifu;//详情下面回复列表


    Adapter_LuntanDetail_pic adapter_luntanDetail_pic;


    List<String > mData;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_luntan_detail;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        mData.add("1");
        mData.add("1");
        mData.add("1");
        adapter_luntanDetail_pic=new Adapter_LuntanDetail_pic(mData,this);
        listview_detailsPIC.setAdapter(adapter_luntanDetail_pic);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        listview_detailsPIC=findViewById(R.id.listview_detailsPIC);
        mylistview_allHuifu=findViewById(R.id.mylistview_allHuifu);
    }
}
