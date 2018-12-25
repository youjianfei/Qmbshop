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
 * 论坛详情下面全部回复大列表适配器
 */

public class Adapter_LuntanDetailsHuifu extends BaseAdapter {
    Context  mContext;
    LayoutInflater mLayoutinflater;
    List<String> mData;

    public Adapter_LuntanDetailsHuifu(List mDatas, Context mContext) {
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
            convertView=mLayoutinflater.inflate(R.layout.item_luntandetails_huifu,null,false);
            viewHolder.user_pic=convertView.findViewById(R.id.user_pic);
            viewHolder.textview_name=convertView.findViewById(R.id.textview_name);
            viewHolder.textview_time=convertView.findViewById(R.id.textview_time);
            viewHolder.textview_content=convertView.findViewById(R.id.textview_content);
            viewHolder.textview_zhuanfaCount=convertView.findViewById(R.id.textview_zhuanfaCount);
            viewHolder.textview_huifuCount=convertView.findViewById(R.id.textview_huifuCount);
            viewHolder.textview_dianzanCount=convertView.findViewById(R.id.textview_dianzanCount);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolderRR) convertView.getTag();
        }
        viewHolder.textview_name.setText(mData.get(position));

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
    }
}
