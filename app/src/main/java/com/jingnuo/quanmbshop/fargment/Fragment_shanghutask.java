package com.jingnuo.quanmbshop.fargment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jingnuo.quanmbshop.Adapter.Adapter_SquareList;
import com.jingnuo.quanmbshop.Adapter.Adapter_mytodo;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.activity.HelperOrderActivity;
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
import com.jingnuo.quanmbshop.popwinow.Popwindow_helperfirst;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_shanghutask extends Fragment implements View.OnClickListener {
    View rootview;
    //控件
    ImageView image_personcenter;
    TabLayout mTablayout;
    PullToRefreshListView mListview_shanghuorder;

    View listheadView;//头视图
    TabLayout mTablayout_header;
    TextView textview_money;
    TextView textview_ordercount;
    SimpleRatingBar simpleRatingBar;




    //数据
    int page = 1;
    int type = 0;
    String state = "00000";
    ShopcenterBean shopcenterBean;//商户

    Adapter_SquareList adapter;
    List<ShanghuneworderBean.DataBean> mList;
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

    private void initdata() {
        requestshopinfo();
        request("00000", page);
        mList=new ArrayList<>();
        adapter=new Adapter_SquareList(mList,getActivity());
        mListview_shanghuorder.setAdapter(adapter);
    }

    private void initview() {
        mListview_shanghuorder = rootview.findViewById(R.id.list_order);
        image_personcenter = rootview.findViewById(R.id.image_personcenter);
        mTablayout = rootview.findViewById(R.id.tablayout);
        mTablayout.addTab(mTablayout.newTab().setText("新订单").setTag("00000"));
        mTablayout.addTab(mTablayout.newTab().setText("进行中").setTag("05,06"));
        mTablayout.addTab(mTablayout.newTab().setText("已完成").setTag("00,"));

        listheadView = LayoutInflater.from(getActivity()).inflate(R.layout.list_headview_shanghuorder, null, false);
        mListview_shanghuorder.getRefreshableView().addHeaderView(listheadView);

        textview_ordercount = listheadView.findViewById(R.id.textview_ordercount);
        simpleRatingBar = listheadView.findViewById(R.id.SimpleRatingBar);
        textview_money = listheadView.findViewById(R.id.textview_money);
        mTablayout_header = listheadView.findViewById(R.id.tablayout_head);
        mTablayout_header.addTab(mTablayout_header.newTab().setText("新订单").setTag("00000"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("进行中").setTag("05,06"));
        mTablayout_header.addTab(mTablayout_header.newTab().setText("已完成").setTag("00,"));
    }

    private void initlistenner() {
        mListview_shanghuorder.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                request(state,page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                request(state,page);
            }
        });
        mListview_shanghuorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),TaskDetailsActivity.class);
                intent.putExtra("id",mList.get(position-2).getTask_ID()+"");
//                LogUtils.LOG("ceshi","列表数"+mdata.size()+"点击位置"+position,"sadfasfd");
//                intent.putExtra("order_no",mdata.get(position-1).getOrder_no()+"");
                startActivity(intent);




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
                } else {
                    if (firstVisibleItem > 1) {
                        mTablayout.setVisibility(View.VISIBLE);
                    } else {
                        mTablayout.setVisibility(View.INVISIBLE);
                    }
                }

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
                LogUtils.LOG("ceshi", Urls.Baseurl + Urls.shopIn_state + Staticdata.static_userBean.getData().getUser_token(), "检测商户审核状态接口");
                if (Staticdata.static_userBean.getData().getAppuser().getRole().contains("2")) {
                    LogUtils.LOG("ceshi", "检测商户审核状态", "检测商户审核状态");

                    Intent intent_shopcenter = new Intent(getActivity(), ShopCenterNewActivity.class);
                    intent_shopcenter.putExtra("type", 2);//2  商户
                    getActivity().startActivity(intent_shopcenter);

                } else {
                    LogUtils.LOG("ceshi", "检测商户审核状态" + Staticdata.static_userBean.getData().getAppuser().getRole(), "检测商户审核状态");
                    new Volley_Utils(new Interface_volley_respose() {
                        @Override
                        public void onSuccesses(String respose) {

                            int status = 0;
                            String msg = "";
                            String state = "";
                            try {
                                JSONObject object = new JSONObject(respose);
                                status = (Integer) object.get("code");//
                                msg = (String) object.get("message");//
                                state = (String) object.get("status");//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (state.equals("00")) {//审核通过
                                Intent intent_shopcenter = new Intent(getActivity(), ShopCenterNewActivity.class);
                                intent_shopcenter.putExtra("type", 2);//2  商户
                                getActivity().startActivity(intent_shopcenter);
                            } else if (state.equals("01")) {//没提交
                                Intent intent_shopin = new Intent(getActivity(), ShopInActivity.class);
                                getActivity().startActivity(intent_shopin);

                            } else if (state.equals("02")) {//正在审核
//                            Intent intent_shopinext=new Intent(getActivity(), ShopInNextActivity.class);
//                            getActivity().startActivity(intent_shopinext);
                                Intent intent_submit = new Intent(getActivity(), SubmitSuccessActivity.class);
                                intent_submit.putExtra("state", "2");
                                startActivity(intent_submit);

                            } else if (state.equals("03")) {//没提交审核
                                Intent intent_shopin = new Intent(getActivity(), ShopInActivity.class);
                                getActivity().startActivity(intent_shopin);
                            }
                        }

                        @Override
                        public void onError(int error) {

                        }
                    }).Http(Urls.Baseurl + Urls.shopIn_state + Staticdata.static_userBean.getData().getUser_token(), getActivity(), 0);

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
                        .getUser_token() + "&client_no=" + Staticdata.static_userBean.getData().getAppuser()
                        .getClient_no();
        LogUtils.LOG("ceshi", "帮手 商户网址：" + url_info, "ShopCenterActivity");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "商户中心：" + respose, "ShopCenterActivity");
                    shopcenterBean = new Gson().fromJson(respose, ShopcenterBean.class);
                    textview_money.setText(shopcenterBean.getData().getList().getCommission()+"");
                   textview_ordercount.setText(shopcenterBean.getData().getList().getOrderSum()+"");
                    setstar((float) shopcenterBean.getData().getList().getEvaluation_star());
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
            URL = Urls.Baseurl + Urls.neworderlist + Staticdata.static_userBean.getData().getUser_token() + "&business_no=" +
                    "60000000046"+ "&x_value=" + "34.800276" + "&y_value=" + "113.631177"+
                    "&pageNum=" + page;
        }else {
            URL = Urls.Baseurl + Urls.shoporder + Staticdata.static_userBean.getData().getUser_token() + "&client_no=" +
                    Staticdata.static_userBean.getData().getAppuser().getClient_no() + "&order_status=" + state +
                    "&curPageNo=" + page;
        }
        LogUtils.LOG("ceshi", URL, "Fragment_shanghutask");

        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                LogUtils.LOG("ceshi", "新订单"+respose, "Fragment_shanghutask");
                if (mListview_shanghuorder.isRefreshing()) {
                    mListview_shanghuorder.onRefreshComplete();
                }
                shanghuneworderBean = new Gson().fromJson(respose, ShanghuneworderBean.class);
                if (page == 1) {
                    mList.clear();
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    if (shanghuneworderBean.getData() != null) {
                        mList.addAll(shanghuneworderBean.getData());
                    }
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
        if (mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition() == 1 || mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition() == 0) {
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
        View c = mListview_shanghuorder.getRefreshableView().getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = mListview_shanghuorder.getRefreshableView().getFirstVisiblePosition();
        if (firstVisiblePosition == 1 || firstVisiblePosition == 0) {
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
