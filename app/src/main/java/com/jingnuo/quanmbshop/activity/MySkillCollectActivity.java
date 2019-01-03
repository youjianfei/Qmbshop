package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_myCollect;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.SkillCollectBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import java.util.ArrayList;
import java.util.List;

public class MySkillCollectActivity extends BaseActivityother {

    //控件
    ListView  mListview;

    List<SkillCollectBean.DataBean.ListBean>mdata;
    SkillCollectBean skillCollectBean;
    Adapter_myCollect adapter_myCollect;
    ImageView mImage_view_empty;
    //数据
    int  page=1;

    @Override
    public int setLayoutResID() {
        return R.layout.activity_my_skill_collect;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        mdata=new ArrayList<>();
        adapter_myCollect=new Adapter_myCollect(mdata,this);
        mListview.setAdapter(adapter_myCollect);
        page=1;
        request();
    }

    @Override
    protected void initListener() {
//        mListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                LogUtils.LOG("ceshi", "下拉刷新生效", "fragmentsquare");
//                page=1;
//                request();
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page++;
//                request();
//            }
//        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.LOG("ceshi","收藏点击了+"+position,"点击收藏列表");
                Intent mIntent_shopskillDetail = new Intent(MySkillCollectActivity.this, SkillDetailActivity.class);

                mIntent_shopskillDetail.putExtra("id",mdata.get(position-1).getRelease_specialty_id()+"");
                LogUtils.LOG("ceshi",mdata.get(position-1).getBusiness_no(),"ShophallActivity");
                if(mdata.get(position-1).getBusiness_no().equals("")){
                    mIntent_shopskillDetail.putExtra("role",1+"");
                    LogUtils.LOG("ceshi",mdata.get(position-1).getBusiness_no(),"ShophallActivity1");
                }else {
                    mIntent_shopskillDetail.putExtra("role",2+"");
                    LogUtils.LOG("ceshi",mdata.get(position-1).getBusiness_no(),"ShophallActivity2");

                }
                startActivity(mIntent_shopskillDetail);
            }
        });
    }

    @Override
    protected void initView() {
        mListview=findViewById(R.id.mlistview_skillcollect);
        mImage_view_empty=findViewById(R.id.image_empty);

    }
    void request(){
        LogUtils.LOG("ceshi","收藏列表网址接口+"+Urls.Baseurl+Urls.ColltctSkillList+ Staticdata.static_userBean.getData().getAppuser().getUser_token()
                +"&client_no="+Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+"&curPageNo="+page,"收藏列表");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {

                LogUtils.LOG("ceshi",respose,"收藏列表");
                skillCollectBean=new Gson().fromJson(respose,SkillCollectBean.class);
                if(skillCollectBean.getData()!=null&&page==1){
                    mdata.clear();
                    mdata.addAll(skillCollectBean.getData().getList());
                    adapter_myCollect.notifyDataSetChanged();
                    mImage_view_empty.setVisibility(mdata.size()==0? View.VISIBLE:View.GONE);
                }else if(skillCollectBean.getData()!=null){
                    mdata.addAll(skillCollectBean.getData().getList());
                    adapter_myCollect.notifyDataSetChanged();
                }
            }
            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl+Urls.ColltctSkillList+ Staticdata.static_userBean.getData().getAppuser().getUser_token()
                +"&client_no="+Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+"&curPageNo="+page,this,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        request();
    }
}
