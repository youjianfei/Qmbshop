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
    }
}
