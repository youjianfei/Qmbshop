package com.jingnuo.quanmbshop.fargment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Adapter.Adapter_SquareList;
import com.jingnuo.quanmbshop.Adapter.Adapter_mytodo;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.activity.HelperOrderActivity;
import com.jingnuo.quanmbshop.activity.MyTodoActivity;
import com.jingnuo.quanmbshop.activity.ShopCenterNewActivity;
import com.jingnuo.quanmbshop.activity.ShopInActivity;
import com.jingnuo.quanmbshop.activity.SubmitSuccessActivity;
import com.jingnuo.quanmbshop.activity.TaskDetailsActivity;
import com.jingnuo.quanmbshop.customview.SimpleRatingBar;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyTodoBean;
import com.jingnuo.quanmbshop.entityclass.ShanghuneworderBean;
import com.jingnuo.quanmbshop.entityclass.ShopcenterBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_ShanghuIsjiedan;
import com.jingnuo.quanmbshop.popwinow.Popwindow_helperfirst;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_shanghutask extends Fragment implements View.OnClickListener {
    View rootview;
    //控件
    RelativeLayout re_title;
    ImageView image_personcenter;
    TabLayout mTablayout;
    ListView mListview_shanghuorder;

    View listheadView;//头视图
    TabLayout mTablayout_header;
    TextView textview_money;
    TextView text_jiedan;
    TextView textview_ordercount;
    SimpleRatingBar simpleRatingBar;
    ImageView image_huiyuan;

    //popwindow
    Popwindow_ShanghuIsjiedan popwindow_shanghuIsjiedan;




    //数据
    int page = 1;
    int type = 0;    // 0 新订单
    String state = "00000";
    ShopcenterBean shopcenterBean;//商户

    Adapter_SquareList adapter;
    List<ShanghuneworderBean.DataBean.ListBean> mList;
    ShanghuneworderBean  shanghuneworderBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_shanghutask, container, false);
        initview();
        initdata();
        setdata();
        initlistenner();


        return rootview;
    }

    private void setdata() {

    }
    void  requestTuisongstate(String state){
        new  Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                int status = 0;
                String msg = "";
                LogUtils.LOG("ceshi", respose, "推送开关");
                try {
                    JSONObject object = new JSONObject(respose);
                    status = (Integer) object.get("code");//
                    msg = (String) object.get("message");//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == 1) {
                    ToastUtils.showToast(getActivity(), msg);
                    requestshopinfo();
                } else {
                    ToastUtils.showToast(getActivity(), msg);
                }

            }

            @Override
            public void onError(int error) {

            }
        }).Http(Urls.Baseurl_cui+Urls.push_on_off+Staticdata.static_userBean.getData().getAppuser().getUser_token()+"&push_on_off="+state,getActivity(),0);
    }
    private void initdata() {
        requestshopinfo();
        if(!Staticdata.xValue.equals("")){
            request("00000", page);
        }
        mList=new ArrayList<>();
        adapter=new Adapter_SquareList(mList,getActivity());
        mListview_shanghuorder.setAdapter(adapter);
    }

    private void initview() {

        re_title = rootview.findViewById(R.id.re_title);
        text_jiedan = rootview.findViewById(R.id.text_jiedan);
        mListview_shanghuorder = rootview.findViewById(R.id.list_order);
        image_personcenter = rootview.findViewById(R.id.image_personcenter);
        mTablayout = rootview.findViewById(R.id.tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("新订单").setTag("00000"));
        mTablayout.addTab(mTablayout.newTab().setText("进行中").setTag("05,06"));
        mTablayout.addTab(mTablayout.newTab().setText("已完成").setTag("00,"));

        listheadView = LayoutInflater.from(getActivity()).inflate(R.layout.list_headview_shanghuorder, null, false);
        mListview_shanghuorder.addHeaderView(listheadView);

        textview_ordercount = listheadView.findViewById(R.id.textview_ordercount);
        image_huiyuan = listheadView.findViewById(R.id.image_huiyuan);
        simpleRatingBar = listheadView.findViewById(R.id.SimpleRatingBar);
        textview_money = listheadView.findViewById(R.id.textview_money);
        mTablayout_header = listheadView.findViewById(R.id.tablayout_head);
        mTablayout_header.addTab(mTablayout_header.newTab().setText("新订单").setTag("00000"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("进行中").setTag("05,06"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("已完成").setTag("00,01,02"));
    }

    private void initlistenner() {
        text_jiedan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                popwindow_shanghuIsjiedan.showPopwindow();
            }
        });
//        mListview_shanghuorder.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        mListview_shanghuorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type==0){
                    Intent intent=new Intent(getActivity(),TaskDetailsActivity.class);
                    intent.putExtra("id",mList.get(position-2).getTask_id()+"");
//                LogUtils.LOG("ceshi","列表数"+mdata.size()+"点击位置"+position,"sadfasfd");
//                intent.putExtra("order_no",mdata.get(position-1).getOrder_no()+"");
                    startActivity(intent);
                }else {
                    Intent  intent=new Intent(getActivity(),HelperOrderActivity.class);
                    intent.putExtra("type",type);
                    LogUtils.LOG("ceshi","列表数"+mList.size()+"点击位置"+position,"sadfasfd");
                    intent.putExtra("order_no",mList.get(position-2).getOrder_no()+"");
                    startActivity(intent);
                }





            }
        });
        //listview的滑动距离监听
        mListview_shanghuorder.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (isScroll()) {
                    float scrollY = getScrollY();
                    LogUtils.LOG("ceshi", "scrollY" + scrollY, "透明度");

                    if (scrollY > 0.90) {
//                        int alpha = (int) (255 * scrollY);
                        mTablayout.setVisibility(View.VISIBLE);

//                        LogUtils.LOG("ceshi",alpha+"scrollY"+scrollY,"透明度");
//                        mRelativelayout_sort.setAlpha(alpha);
//                        mRelativelayout_sort.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
//                        relative_shaixuan.setVisibility(View.INVISIBLE);
                    } else {
//                        relative_shaixuan.setVisibility(View.VISIBLE);
                        mTablayout.setVisibility(View.INVISIBLE);

//                        mRelativelayout_sort.setBackgroundColor(Color.argb(255, 255, 255, 255));
                    }
                }
//                else {
//                    if (firstVisibleItem > 1) {
//                        mTablayout.setVisibility(View.VISIBLE);
//                    } else {
//                        mTablayout.setVisibility(View.INVISIBLE);
//                    }
//                }

            }
        });
        mTablayout_header.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.LOG("ceshi", tab.getTag() + "", "mytodo");
                state = (String) tab.getTag();
                page = 1;
                request(state, page);
                mTablayout.setScrollPosition(0,tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.LOG("ceshi", tab.getTag() + "", "mytodo");
                state = (String) tab.getTag();
                page = 1;
                request(state, page);
                mTablayout_header.setScrollPosition(0,tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        image_personcenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_personcenter:
                LogUtils.LOG("ceshi", Urls.Baseurl + Urls.shopIn_state + Staticdata.static_userBean.getData().getAppuser().getUser_token(), "检测商户审核状态接口");
                if (Staticdata.static_userBean.getData().getAppuser().getIs_business().equals("Y")) {
                    LogUtils.LOG("ceshi", "检测商户审核状态", "检测商户审核状态");
                    Intent intent_shopcenter = new Intent(getActivity(), ShopCenterNewActivity.class);
                    intent_shopcenter.putExtra("type", 2);//2  商户
                    getActivity().startActivity(intent_shopcenter);
                }
                break;
        }
    }

    void setstar(float stars){
        simpleRatingBar.setNumberOfStars(5);
        simpleRatingBar.setFillColor(getResources().getColor(R.color.yellow_jianbian_start));
        simpleRatingBar.setStarBackgroundColor(getResources().getColor(R.color.gray_background));
        simpleRatingBar.setStepSize((float) 0.1);
        simpleRatingBar.setRating(stars);
        simpleRatingBar.setDrawBorderEnabled(false);
        simpleRatingBar.setStarsSeparation(1);

    }
    void requestshopinfo() {
        String url_info = Urls.Baseurl + Urls.shopcenter + Staticdata.static_userBean.getData()
                        .getAppuser().getUser_token() + "&business_no=" + Staticdata.static_userBean.getData().getAppuser()
                        .getBusiness_no();
        LogUtils.LOG("ceshi", " 商户网址：" + url_info, "ShopCenterActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "商户中心：" + respose, "ShopCenterActivity");
                    shopcenterBean = new Gson().fromJson(respose, ShopcenterBean.class);
                    textview_money.setText(shopcenterBean.getData().getList().getCommission()+"");
                   textview_ordercount.setText(shopcenterBean.getData().getList().getOrderSum()+"");
                   if(shopcenterBean.getData().getList().getMemberImgUrl()==null){
                       image_huiyuan.setBackgroundResource(R.mipmap.huiyuanno);
                   }else {
                       image_huiyuan.setBackgroundResource(R.mipmap.huiyuanyes);
                   }
                    setstar((float) shopcenterBean.getData().getList().getEvaluation_star());
                if(shopcenterBean.getData().getList().getPush_on_off().equals("Y")){//推送开关状态
                    text_jiedan.setText("自动接单");
                }else {
                    text_jiedan.setText("关闭接单");
                }
                popwindow_shanghuIsjiedan=new Popwindow_ShanghuIsjiedan(getActivity(), re_title, new InterfacePermission() {
                    @Override
                    public void onResult(boolean result) {
                        if(result){
                            requestTuisongstate("Y");
                        }else {
                            requestTuisongstate("N");
                        }

                    }
                },text_jiedan.getText().equals("自动接单")?"关闭接单":"自动接单");
            }

            @Override
            public void onError(int error) {

            }
        }).Http(url_info, getActivity(), 0);
    }


    void request(String state, final int page) {//请求列表
        String URL = "";
        LogUtils.LOG("ceshi", "state"+state, "Fragment_shanghutask");
        if (state.equals("00000")){//新订单
            URL = Urls.Baseurl + Urls.neworderlist + Staticdata.static_userBean.getData().getAppuser().getUser_token()+ "&business_no=" +
                    Staticdata.static_userBean.getData().getAppuser().getBusiness_no()+ "&x_value=" + Staticdata.xValue + "&y_value=" + Staticdata.yValue+
                    "&pageNum=" + page;
            type=0;
        }else {
            URL = Urls.Baseurl + Urls.shoporder + Staticdata.static_userBean.getData().getAppuser().getUser_token() + "&order_status=" + state +
                    "&curPageNo=" + page;
            type=1;
        }
        LogUtils.LOG("ceshiurl", URL, "Fragment_shanghutask");

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "新订单"+respose, "Fragment_shanghutask");

                shanghuneworderBean = new Gson().fromJson(respose, ShanghuneworderBean.class);
                if (page == 1) {
                    mList.clear();
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData().getList());
                    }
//                    adapter.settype(type);
                    adapter.notifyDataSetChanged();
                } else {
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData().getList());
                    }
//                    adapter.settype(type);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int error) {

            }
        }).Http(URL, getActivity(), 0);

    }

    /**
     * 判断是否是第一行
     *
     * @return
     */
    private boolean isScroll() {
        if (mListview_shanghuorder.getFirstVisiblePosition() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到高度比例
     *
     * @return
     */
    private float getScrollY() {
        View c = mListview_shanghuorder.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListview_shanghuorder.getFirstVisiblePosition();
        if (firstVisiblePosition == 0) {
            //如果可见的是第一行或第二行，那么开始计算距离比例
            float top = c.getTop();
            //当第一行已经开始消失的时候，top是为负数的，所以取正
            top = Math.abs(top);
            //48为菜单栏的高度，单位为dp
            //得到的高度为ViewPager的高度减去菜单栏高度，即为最大可滑动距离
            float height = c.getHeight() - SizeUtils.dip2px(getActivity(), 40);

            float y = top / height;

            return y;
        } else {
            return 0;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestshopinfo();//
    }
}
