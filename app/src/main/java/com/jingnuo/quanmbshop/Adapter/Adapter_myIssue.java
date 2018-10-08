package com.jingnuo.quanmbshop.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.activity.MytaskDetailActivity;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyorderBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_Tip;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_myIssue extends BaseAdapter {
    List<MyorderBean.DataBean> mData;
    Context mContext;
    LayoutInflater mInlayout;

    public Adapter_myIssue(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mContext = mContext;
        this.mData = mDatas;
        mInlayout = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolde viewHolder = null;
        if (convertView == null) {
            viewHolder = new viewHolde();
            convertView = mInlayout.inflate(R.layout.item_myissue_list, null, false);
            viewHolder.mTextview_type = convertView.findViewById(R.id.text_type);
            viewHolder.mTextview_type2 = convertView.findViewById(R.id.text_type2);
            viewHolder.mTextview_title = convertView.findViewById(R.id.textview_titl);
            viewHolder.mTextview_issuetime = convertView.findViewById(R.id.textview_issuename);
            viewHolder.mTextview_money = convertView.findViewById(R.id.textview_moneyy);
            viewHolder.mTextview_taskstate = convertView.findViewById(R.id.text_taskstate);
            viewHolder.mTextview_resttime = convertView.findViewById(R.id.text_resttime);
            viewHolder.mTextview_cancel=convertView.findViewById(R.id.text_taskcancle);
            viewHolder.mImage_resttime=convertView.findViewById(R.id.iamge_resttimepic);
            viewHolder.resttime=convertView.findViewById(R.id.resttime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (viewHolde) convertView.getTag();
        }
        viewHolder.mTextview_type.setText(mData.get(position).getSpecialty_name());
        viewHolder.mTextview_type2.setText(mData.get(position).getSpecialty_name());
        if(mData.get(position).getSpecialty_name().length()>3){
            viewHolder.mTextview_type.setVisibility(View.INVISIBLE);
            viewHolder.mTextview_type2.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mTextview_type.setVisibility(View.VISIBLE);
            viewHolder.mTextview_type2.setVisibility(View.INVISIBLE);
        }
        viewHolder.mTextview_title.setText(mData.get(position).getTask_description());
        viewHolder.mTextview_issuetime.setText("发布时间：" + mData.get(position).getTask_StartDate());
        viewHolder.mTextview_taskstate.setText(mData.get(position).getStatus_name());
        if(mData.get(position).getTask_Status_code().equals("07")||mData.get(position).getTask_Status_code().equals("13")
                ||mData.get(position).getTask_Status_code().equals("09")||mData.get(position).getTask_Status_code().equals("06")){
            viewHolder.resttime.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.resttime.setVisibility(View.VISIBLE);
        }

        if(mData.get(position).getStatus_name().equals("待帮助")){
            viewHolder.mTextview_taskstate.setBackgroundResource(R.drawable.text_green2);
            if(mData.get(position).getIs_helper_bid().equals("Y")){//待帮助状态下  判断是否由帮手出价
                viewHolder.mTextview_money.setText("佣金：帮手出价");
            }else {
                viewHolder.mTextview_money.setText("佣金：" + mData.get(position).getCommission() + "元");
            }

        }else if(mData.get(position).getStatus_name().equals("已完成")||mData.get(position).getStatus_name().equals("已失效")
                ||mData.get(position).getStatus_name().equals("取消任务")){
            viewHolder.mTextview_taskstate.setBackgroundResource(R.drawable.text_gray2);
            if(mData.get(position).getCounteroffer_Amount()==0){
                viewHolder.mTextview_money.setText("佣金：" + mData.get(position).getCommission() + "元");
            }else {
                viewHolder.mTextview_money.setText("佣金：" + mData.get(position).getCounteroffer_Amount() + "元");
            }
        }else {
            viewHolder.mTextview_taskstate.setBackgroundResource(R.drawable.text_red);
            if(mData.get(position).getCounteroffer_Amount()==0){
                viewHolder.mTextview_money.setText("佣金：" + mData.get(position).getCommission() + "元");
            }else {
                viewHolder.mTextview_money.setText("佣金：" + mData.get(position).getCounteroffer_Amount() + "元");
            }
        }
        if(mData.get(position).getApp_type().equals("1")&&mData.get(position).getCommission()==0){
            viewHolder.mTextview_money.setText("佣金：等待报价");
        }
        if(mData.get(position).getTask_Status_code().equals("01")){
            viewHolder.mTextview_resttime.setVisibility(View.VISIBLE);
            viewHolder.mImage_resttime.setVisibility(View.VISIBLE);
            long now = Long.parseLong(Utils.getTime(Utils.getTimeString()));//系统当前时间
            long ago = Long.parseLong(Utils.getTime(mData.get(position).getTask_EndDate()));
            String time = Utils.getDistanceTime(ago, now);//算出的差值
            viewHolder.mTextview_resttime.setText("剩余时间："+time);
        }else {

            viewHolder.mTextview_resttime.setVisibility(View.INVISIBLE);
            viewHolder.mImage_resttime.setVisibility(View.INVISIBLE);
        }
        if(mData.get(position).getTask_Status_code().equals("01")||mData.get(position).getTask_Status_code().equals("02")||
        mData.get(position).getTask_Status_code().equals("08")){
            viewHolder.mTextview_cancel.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mTextview_cancel.setVisibility(View.INVISIBLE);
        }
        viewHolder.mTextview_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Popwindow_Tip("是否取消任务?", (Activity) mContext, new Interence_complteTask() {
                    @Override
                    public void onResult(boolean result) {
                        if (result){
                            Map map=new HashMap();
                            map.put("user_token", Staticdata.static_userBean.getData().getUser_token());
                            map.put("client_no",Staticdata.static_userBean.getData().getAppuser().getClient_no());
                            map.put("id",mData.get(position).getTask_id()+"");
                            new Volley_Utils(new Interface_volley_respose() {
                                @Override
                                public void onSuccesses(String respose) {
                                    int status = 0;
                                    String msg = "";
                                    try {
                                        JSONObject object = new JSONObject(respose);
                                        status = (Integer) object.get("code");//登录状态
                                        msg = (String) object.get("message");//登录返回信息

                                        if (status == 1) {
                                            ToastUtils.showToast(mContext, "取消任务成功");
                                        } else {
                                            ToastUtils.showToast(mContext, msg);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(int error) {

                                }
                            }).postHttp(Urls.Baseurl_cui + Urls.taskdetailscancle, mContext, 1, map);
                        }

                    }
                }).showPopwindow();


            }
        });

        return convertView;
    }

    class viewHolde {
        TextView mTextview_type;
        TextView mTextview_type2;
        TextView mTextview_title;
        TextView mTextview_issuetime;
        TextView mTextview_money;
        TextView mTextview_taskstate;
        TextView mTextview_resttime;
        TextView mTextview_cancel;
        ImageView mImage_resttime;
        RelativeLayout resttime;
    }
}
