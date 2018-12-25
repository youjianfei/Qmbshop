package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jingnuo.quanmbshop.Adapter.Adapter_LuntanShouye;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class LuntanActivity extends BaseActivityother {
    PullToRefreshListView listview_shouye;


    List<String> mData;


    Adapter_LuntanShouye adapter_luntanShouye;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_luntan;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        mData.add("全名帮1");
        mData.add("全名帮2");
        mData.add("全名帮4");
        adapter_luntanShouye=new Adapter_LuntanShouye(mData,this);
        listview_shouye.setAdapter(adapter_luntanShouye);
    }

    @Override
    protected void initListener() {
        listview_shouye.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.LOG("ceshi","点击论坛条目"+position,"论坛列表");
                Intent intent=new Intent(LuntanActivity.this,LuntanDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        listview_shouye=findViewById(R.id.listview_shouye);
    }
}
