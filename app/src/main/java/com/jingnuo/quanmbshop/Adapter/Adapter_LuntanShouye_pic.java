package com.jingnuo.quanmbshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.customview.MyGridView;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 飞 on 2018/12/25.
 */

public class Adapter_LuntanShouye_pic extends BaseAdapter {
    Context  mContext;
    LayoutInflater mLayoutinflater;
    List<String> mData;
    int  Width;
    int  High;

    public Adapter_LuntanShouye_pic(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mData=mDatas;
       this. mContext=mContext;
        mLayoutinflater=LayoutInflater.from(mContext);
        if(mData.size()>1){
            Width=Staticdata.ScreenWidth- SizeUtils.dip2px(mContext,40);
            Width=(int)Width/3;
            High= Width;
        }else {
            Width=Staticdata.ScreenWidth- SizeUtils.dip2px(mContext,30);
            High= (int) (Width*0.55);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderRR   viewHolder=null;
        if(viewHolder==null){
            viewHolder=new ViewHolderRR();
            convertView=mLayoutinflater.inflate(R.layout.item_luntanpic,null,false);
            viewHolder.mImage = convertView.findViewById(R.id.image_pic);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolderRR) convertView.getTag();
        }
        Glide.with(mContext).load(mData.get(position)).into(viewHolder.mImage);
//            viewHolder.mImage.setImageResource(R.mipmap.imageselector_photo);
            ViewGroup.LayoutParams para;
            para = viewHolder.mImage.getLayoutParams();
            para.width = Width;
            para.height = High;
            viewHolder.mImage.setLayoutParams(para);

        return convertView;
    }
    class ViewHolderRR {
        ImageView mImage;
    }
}
