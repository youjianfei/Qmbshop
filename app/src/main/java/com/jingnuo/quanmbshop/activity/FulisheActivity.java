package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_FulisheList;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.GuanggaoBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import java.util.ArrayList;
import java.util.List;

public class FulisheActivity extends BaseActivityother {
    //控件
    ListView mlistview_fulishe;
    ImageView mImageview;


    //对象
    Adapter_FulisheList adapter_fulisheList;


    //数据
    List<GuanggaoBean.DataBean> mdata;
    @Override
    public int setLayoutResID() {
        return R.layout.activity_fulishe;
    }

    @Override
    protected void setData() {
        request_GGLB();
    }

    @Override
    protected void initData() {
        mdata=new ArrayList<>();
        adapter_fulisheList=new Adapter_FulisheList(mdata,this);
        mlistview_fulishe.setAdapter(adapter_fulisheList);
    }

    @Override
    protected void initListener() {
        mlistview_fulishe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.LOG("ceshi",position+"","福利社");
                if(mdata.get(position).getActivity_url()!=null&&!mdata.get(position).getActivity_url().equals("")){
                    Intent intent=new Intent(FulisheActivity.this,GuanggaoWeb.class);
                    intent.putExtra("web",mdata.get(position).getActivity_url());
                    FulisheActivity.this.startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initView() {
        mlistview_fulishe=findViewById(R.id.listview_fulishe);
        mImageview=findViewById(R.id.image_empty);
    }

    private void request_GGLB(){//请求网络轮播图
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", respose, "fulishe");
                mdata.clear();
                mdata.addAll(new Gson().fromJson(respose,GuanggaoBean.class).getData());
                adapter_fulisheList.notifyDataSetChanged();
                mImageview.setVisibility(mdata.size()==0? View.VISIBLE:View.GONE);
            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui+Urls.shouyePic+"4",FulisheActivity.this,0);
        LogUtils.LOG("ceshiddd", "轮播图片：" + Urls.Baseurl_cui+Urls.shouyePic+"4", "fragment_square");
    }
}
