package com.jingnuo.quanmbshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jingnuo.quanmbshop.R;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class Adapter_SearchAddress extends BaseAdapter{
//    List<PoiInfo> mdata;
    List<PoiItem> mdata;
    Context mContext;
    LayoutInflater mLayoutinflater;


    public Adapter_SearchAddress(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mdata=mDatas;
        this.mContext=mContext;
        mLayoutinflater=LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if(convertView==null){
            viewholder=new Viewholder();
            convertView=mLayoutinflater.inflate(R.layout.item_searchaddress,null,false);
            viewholder.mTextview_name=convertView.findViewById(R.id.text_nameaddress);
            viewholder.mTextview_address=convertView.findViewById(R.id.text_addressaddress);
            convertView.setTag(viewholder);
        }else {
            viewholder= (Viewholder) convertView.getTag();
        }
        viewholder.mTextview_name.setText(mdata.get(position).getTitle());
        viewholder.mTextview_address.setText(mdata.get(position).getSnippet());

        return convertView;
    }
    class  Viewholder{
        TextView mTextview_name;
        TextView mTextview_address;
    }
}
