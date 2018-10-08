package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingnuo.quanmbshop.entityclass.SheQuListBean;
import com.jingnuo.quanmbshop.R;
import java.util.List;

public class Adapter_ShequList extends BaseAdapter {
    List<SheQuListBean.DataBean> mData;
    Activity mContext;
    LayoutInflater mInfalter;

    public Adapter_ShequList( List mDatas, Activity mContext) {
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
            convertView = mInfalter.inflate(R.layout.item_shequ, null, false);
            holder.mTextview_name = convertView.findViewById(R.id.text_shequname);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();

        }
        holder.mTextview_name.setText(mData.get(position).getCommunity_name()+"");
        return convertView;
    }

    class Viewholder {
        TextView mTextview_name;
    }

}
