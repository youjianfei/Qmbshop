package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_LuntanShouye;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.BBSBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class LuntanActivity extends BaseActivityother {
    ListView listview_shouye;//列表控件
    SmartRefreshLayout SmartRefreshLayout_refreshLayout;
    ImageView iamgeview_fabu;

    List<BBSBean.DataBean> mData;//列表数据


    Adapter_LuntanShouye adapter_luntanShouye;//列表适配器对象
    BBSBean  bbsBean;//bbs实体类

    String  shuangxin="";

    int  PAGE=1;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_luntan;
    }

    @Override
    protected void setData() {
        request(PAGE);
    }

    @Override
    protected void initData() {
//        shuangxin=getIntent().getStringExtra("shuaxin");
        mData=new ArrayList<>();
        adapter_luntanShouye=new Adapter_LuntanShouye(mData,this);
        listview_shouye.setAdapter(adapter_luntanShouye);
    }

    @Override
    protected void initListener() {
        iamgeview_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LuntanActivity.this,Luntan_AddActivity.class);
                startActivityForResult(intent,1229);
            }
        });
        //上下拉刷新

        SmartRefreshLayout_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                iamgeview_fabu.setVisibility(View.VISIBLE);
                PAGE=1;
                request(PAGE);
            }
        });
        SmartRefreshLayout_refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                PAGE++;
                request(PAGE);
            }
        });
        //list  item  点击事件
        listview_shouye.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.LOG("ceshi","点击论坛条目"+position,"论坛列表");
                Intent intent=new Intent(LuntanActivity.this,LuntanDetailActivity.class);
                intent.putExtra("luntanID",mData.get(position).getID()+"");
                startActivity(intent);
            }
        });
        //监听 listview滑动距离
        final float[] mFirstY = {0};//按下时获取位置
        final float[] mCurrentY = {0};//得到滑动的位置
        listview_shouye.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mFirstY[0] = event.getY();//按下时获取位置
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY[0] = event.getY();//得到滑动的位置
                        if(mCurrentY[0] - mFirstY[0] > 5){//滑动的位置减去按下的位置大于最小滑动距离  则表示向下滑动
                            //down
                            iamgeview_fabu.setVisibility(View.VISIBLE);
                        }else if(mFirstY[0] - mCurrentY[0] > 5){//反之向上滑动
                            //up
                            iamgeview_fabu.setVisibility(View.GONE);
                        }
                        break;

                }

                return false;
            }
        });
    }

    @Override
    protected void initView() {
        SmartRefreshLayout_refreshLayout=findViewById(R.id.SmartRefreshLayout_refreshLayout);
        listview_shouye=findViewById(R.id.listview_shouye);
        iamgeview_fabu=findViewById(R.id.iamgeview_fabu);
    }

    //请求bbs列表内容
   void  request(final int  page){
       new Volley_Utils(new Interface_volley_respose() {
           @Override
           public void onSuccesses(String respose) {
               LogUtils.LOG("ceshi",respose,"论坛首页");
               SmartRefreshLayout_refreshLayout.finishLoadMore();
               SmartRefreshLayout_refreshLayout.finishRefresh();
               bbsBean=new Gson().fromJson(respose,BBSBean.class);
               if(page==1){
                   mData.clear();
                   if(bbsBean.getData()!=null){
                       mData.addAll(bbsBean.getData());
                       adapter_luntanShouye.notifyDataSetChanged();
                       listview_shouye.setSelection(0);
                   }
               }else {
                   if(bbsBean.getData()!=null){
                       mData.addAll(bbsBean.getData());
                       adapter_luntanShouye.notifyDataSetChanged();
                   }
               }
           }

           @Override
           public void onError(int error) {
               SmartRefreshLayout_refreshLayout.finishLoadMore();
               SmartRefreshLayout_refreshLayout.finishRefresh();
           }
       }).Http(Urls.Baseurl_cui+Urls.bbs_shouye+ Staticdata.static_userBean.getData().getAppuser().getUser_token()+
       "&pageNum="+page,this,0);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1229 && resultCode == 1229) {
            shuangxin  = data.getStringExtra("shuaxin");
            if(shuangxin.equals("Y")){
                PAGE=1;
                request(PAGE);

            }
        }
    }

}
