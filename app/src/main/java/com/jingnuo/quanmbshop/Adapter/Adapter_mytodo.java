package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_bargin;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.activity.HelperOrderActivity;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MyTodoBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_addPrice;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter_mytodo extends  BaseAdapter {
    List<MyTodoBean.DataBean.ListBean>mdata;
    Context mContext;
    LayoutInflater mInflater;


    public Adapter_mytodo(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mdata=mDatas;
        this.mContext=mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_mytodo,null,false);
            holder.mTextview_type2=convertView.findViewById(R.id.text_type2);
            holder.mTextview_type=convertView.findViewById(R.id.text_type);
            holder.mTextview_title=convertView.findViewById(R.id.textview_titl);
            holder.mTextview_content=convertView.findViewById(R.id.text_content);
            holder.mTextview_money=convertView.findViewById(R.id.textview_moneyy);
            holder.mTextview_state=convertView.findViewById(R.id.textview_state2);
            holder.mTextview_issuename=convertView.findViewById(R.id.textview_issuename);
//            holder.mTextview_bargain=convertView.findViewById(R.id.text_bargain);
//            holder.mTextview_complete=convertView.findViewById(R.id.text_taskscomplete);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.mTextview_type.setText(mdata.get(position).getSpecialty_name());
        holder.mTextview_type2.setText(mdata.get(position).getSpecialty_name());
        if(mdata.get(position).getSpecialty_name().length()>3){
            holder.mTextview_type.setVisibility(View.INVISIBLE);
            holder.mTextview_type2.setVisibility(View.VISIBLE);
        }else {
            holder.mTextview_type.setVisibility(View.VISIBLE);
            holder.mTextview_type2.setVisibility(View.INVISIBLE);
        }
        holder.mTextview_title.setText(mdata.get(position).getTask_description());
        holder.mTextview_issuename.setText(mdata.get(position).getCreateDate());

        holder.mTextview_content.setText("预约时间："+mdata.get(position).getTask_time());
        if(mdata.get(position).getOrder_amount()==0&&mdata.get(position).getApp_type().equals("1")){
            holder.mTextview_money.setText("佣金：未报价");
        }else {
            holder.mTextview_money.setText("佣金："+mdata.get(position).getOrder_amount()+"元");
        }

        holder.mTextview_state.setText(mdata.get(position).getOrder_status());
//        if(mdata.get(position).getOrder_status().equals("已完成")){
//            holder.mTextview_complete.setText("任务完成");
//            holder.mTextview_complete.setEnabled(false);
//        }else if(!mdata.get(position).getOrder_status().equals("待完成")){
//            holder.mTextview_complete.setVisibility(View.GONE);
//        }

//        holder.mTextview_complete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mdata.get(position).getApp_type().equals("1")){
//                    new Popwindow_addPrice((Activity) mContext, new Interence_bargin() {
//                        @Override
//                        public void onResult(String result) {
//                            if(Double.parseDouble(result)<5){
//                                ToastUtils.showToast(mContext,"金额不得低于5元");
//                                return;
//                            }
//                            LogUtils.LOG("ceshi",result,"我的订单适配器");
//                            new Volley_Utils(new Interface_volley_respose() {
//                                @Override
//                                public void onSuccesses(String respose) {
//                                    LogUtils.LOG("ceshi", respose, "申请完成");
//                                    int status = 0;
//                                    String msg = "";
//                                    try {
//                                        JSONObject object = new JSONObject(respose);
//                                        status = (Integer) object.get("code");//登录状态
//                                        msg = (String) object.get("message");//登录返回信息
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (status == 1) {
//                                        ToastUtils.showToast(mContext, msg);
//                                    } else {
//                                        ToastUtils.showToast(mContext, msg);
//                                    }
//                                }
//
//                                @Override
//                                public void onError(int error) {
//                                }
//                            }).Http(Urls.Baseurl_cui + Urls.applyPipeicompletetask + Staticdata.static_userBean.getData().getUser_token() +
//                                    "&order_no=" + mdata.get(position).getOrder_no() +
//                                    "&order_amount=" + result, mContext, 0);
//
//                        }
//                    }).showpop();
//                }
//                else {
//                    LogUtils.LOG("ceshi","申请完成任务接口+"+ Urls.Baseurl_cui+Urls.applycompletetask+ Staticdata.static_userBean.getData().getUser_token()+
//                            "&order_no="+mdata.get(position).getOrder_no()+
//                            "&task_id="+mdata.get(position).getTask_id(),"申请完成任务");
//                    new Volley_Utils(new Interface_volley_respose() {
//                        @Override
//                        public void onSuccesses(String respose) {
//                            LogUtils.LOG("ceshi",respose,"申请完成");
//                            int status = 0;
//                            String msg = "";
//                            try {
//                                JSONObject object = new JSONObject(respose);
//                                status = (Integer) object.get("code");//登录状态
//                                msg = (String) object.get("message");//登录返回信息
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if(status==1){
//                                ToastUtils.showToast(mContext,msg);
//                            }else {
//                                ToastUtils.showToast(mContext,msg);
//                            }
//                        }
//                        @Override
//                        public void onError(int error) {
//
//                        }
//                    }).Http(Urls.Baseurl_cui+Urls.applycompletetask+ Staticdata.static_userBean.getData().getUser_token()+
//                            "&order_no="+mdata.get(position).getOrder_no()+
//                            "&task_id="+mdata.get(position).getTask_order_id(),mContext,0);
//                }
//
//
//
//            }
//        });

        return convertView;
    }
    class ViewHolder {
        TextView mTextview_title;
        TextView mTextview_type;
        TextView mTextview_type2;
        TextView mTextview_content;
        TextView mTextview_money;
        TextView mTextview_state;
        TextView mTextview_issuename;
//        TextView mTextview_bargain;
//        TextView mTextview_complete;
    }
}
