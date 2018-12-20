package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.Interface.Interence_shuaxinzhiding;
import com.jingnuo.quanmbshop.Interface.InterfaceAdapterSuccess;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.MySkillBean;
import com.jingnuo.quanmbshop.entityclass.ShangchengliebiaoBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_Tip;
import com.jingnuo.quanmbshop.popwinow.Popwindow_zhinengshuaxin;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Adapter_zhuangbeishangcheng extends BaseAdapter {
    List<ShangchengliebiaoBean.DataBean> mData;
    Activity mContext;
    LayoutInflater mInfalter;

    public Adapter_zhuangbeishangcheng(List mDatas, Activity mContext) {
        super(mDatas, mContext);
        this.mContext = mContext;
        this.mData = mDatas;
        mInfalter = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder holder = null;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = mInfalter.inflate(R.layout.item_zhoubian, null, false);
            holder.image_pic=convertView.findViewById(R.id.image_pic);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        Glide.with(mContext).load(mData.get(position).getImg_url()).into(holder.image_pic);
        ViewGroup.LayoutParams para;
        para = holder.image_pic.getLayoutParams();
        para.width = -1;
        para.height = Staticdata.ScreenWidth- SizeUtils.dip2px(mContext,30);
        para.height =  para.height/2;
        para.height = (int) (para.height*1.2);
        holder.image_pic.setLayoutParams(para);



        return convertView;
    }

    class Viewholder {
        ImageView image_pic;
    }

}
