package com.jingnuo.quanmbshop.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jingnuo.quanmbshop.entityclass.HuiyuanTaocanBean;
import com.jingnuo.quanmbshop.R;
import java.util.List;

public class Adapter_recharge_huiyuan extends BaseAdapter {
    int select;
    List<HuiyuanTaocanBean.DataBean> mdata;
    Context mContext;
    LayoutInflater mInflater;
    public Adapter_recharge_huiyuan(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mdata=mDatas;
        this.mContext=mContext;
        mInflater=LayoutInflater.from(mContext);
    }
    public void setSeclection(int select) {
        this.select = select;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mInflater.inflate(R.layout.item_huiyuan_recharge,null,false);
        LinearLayout mlinearlayout=convertView.findViewById(R.id.layout_back);
        TextView mTextview_price=convertView.findViewById(R.id.text_hui_price);
        TextView mTextview_huiyuan=convertView.findViewById(R.id.text_huiyuan);
        TextView text_yuanjia=convertView.findViewById(R.id.text_yuanjia);
        TextView text_dazhe=convertView.findViewById(R.id.textview_dazhe);
        if(select==position){
            mlinearlayout.setBackgroundResource(R.drawable.red_background_5);
            mTextview_price.setTextColor(mContext.getResources().getColor(R.color.white));
            mTextview_huiyuan.setTextColor(mContext.getResources().getColor(R.color.white));
            text_yuanjia.setTextColor(mContext.getResources().getColor(R.color.white));
            text_dazhe.setTextColor(mContext.getResources().getColor(R.color.red));
            text_dazhe.setBackgroundResource(R.mipmap.huiyuandazhe_white);

        }
        if(mdata.get(position).getDiscount().equals("")){
            text_dazhe.setVisibility(View.INVISIBLE);
            text_yuanjia.setVisibility(View.GONE);
        }else {
            text_dazhe.setVisibility(View.VISIBLE);
            text_yuanjia.setVisibility(View.VISIBLE);
        }
        mTextview_price.setText(mdata.get(position).getPrice()+"");
        text_yuanjia.setText("原价"+mdata.get(position).getOrg_price());
        text_yuanjia.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        mTextview_huiyuan.setText(mdata.get(position).getPackage_name());
        text_dazhe.setText(mdata.get(position).getDiscount());
        return convertView;
    }
}
