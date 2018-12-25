package com.jingnuo.quanmbshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.customview.MyGridView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 飞 on 2018/12/25.
 * 论坛首页大列表适配器
 */

public class Adapter_LuntanShouye extends BaseAdapter {
    Context  mContext;
    LayoutInflater mLayoutinflater;
    List<String> mData;

    public Adapter_LuntanShouye(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mData=mDatas;
       this. mContext=mContext;
        mLayoutinflater=LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderRR   viewHolder=null;
        if(viewHolder==null){
            viewHolder=new ViewHolderRR();
            convertView=mLayoutinflater.inflate(R.layout.item_luntanshouye,null,false);
            viewHolder.user_pic=convertView.findViewById(R.id.user_pic);
            viewHolder.textview_name=convertView.findViewById(R.id.textview_name);
            viewHolder.textview_time=convertView.findViewById(R.id.textview_time);
            viewHolder.textview_content=convertView.findViewById(R.id.textview_content);
            viewHolder.textview_zhuanfaCount=convertView.findViewById(R.id.textview_zhuanfaCount);
            viewHolder.textview_huifuCount=convertView.findViewById(R.id.textview_huifuCount);
            viewHolder.textview_dianzanCount=convertView.findViewById(R.id.textview_dianzanCount);
            viewHolder.mygridview_luntanPIC=convertView.findViewById(R.id.mygridview_luntanPIC);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolderRR) convertView.getTag();
        }
        viewHolder.textview_name.setText(mData.get(position));

        if(mData.size()>0){//有图片
            viewHolder.  mygridview_luntanPIC.setVisibility(View.VISIBLE);
        }else {//没有图片
            viewHolder.  mygridview_luntanPIC.setVisibility(View.GONE);
        }
        if(mData.size()>1){//只有一张
            viewHolder.  mygridview_luntanPIC.setNumColumns(3);
            Adapter_LuntanShouye_pic adapter_luntanShouye_pic=new Adapter_LuntanShouye_pic(mDatas,mContext);
            viewHolder.mygridview_luntanPIC.setAdapter(adapter_luntanShouye_pic);
        }else {//一张以上
            viewHolder.  mygridview_luntanPIC.setNumColumns(1);
            Adapter_LuntanShouye_pic adapter_luntanShouye_pic=new Adapter_LuntanShouye_pic(mDatas,mContext);
            viewHolder.mygridview_luntanPIC.setAdapter(adapter_luntanShouye_pic);
        }

        return convertView;
    }
    class ViewHolderRR {
        CircleImageView user_pic;
        TextView textview_name;
        TextView textview_time;
        TextView textview_content;
        TextView textview_zhuanfaCount;
        TextView textview_huifuCount;
        TextView textview_dianzanCount;
        MyGridView mygridview_luntanPIC;
    }
}
