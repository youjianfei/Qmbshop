package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.entityclass.ShanghuneworderBean;
import com.jingnuo.quanmbshop.entityclass.Square_defaultBean;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.R;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class Adapter_SquareList extends  BaseAdapter {
    private Activity mContext;
    private List<ShanghuneworderBean.DataBean.ListBean> mData;
    private LayoutInflater mInflater;
    int type=0;
    public Adapter_SquareList(List<ShanghuneworderBean.DataBean.ListBean> mDatas, Activity mContext) {
        super(mDatas, mContext);
        this.mData=mDatas;
        this.mContext=mContext;
        mInflater=LayoutInflater.from(mContext);

    }
//    public void settype(int type){
//        this. type=type;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_square_list,null,false);
            holder.mText_task_des=convertView.findViewById(R.id.text_square_task);
            holder.mText_task_type=convertView.findViewById(R.id.text_square_type);
            holder.textview_state=convertView.findViewById(R.id.textview_state);
            holder.mText_task_creattime=convertView.findViewById(R.id.text_square_time);
            holder.mText_task_username=convertView.findViewById(R.id.text_taskpeoplename);
            holder.mText_task_address=convertView.findViewById(R.id.text_square_address);
            holder.mText_task_price=convertView.findViewById(R.id.text_square_price);
//            holder.mTextview_distance=convertView.findViewById(R.id.text_taskdistance);
            holder.mImage_view=convertView.findViewById(R.id.image_square_person);
            holder.image_line=convertView.findViewById(R.id.image_line);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
//         holder.image_line.setVisibility(position==3? View.GONE:View.VISIBLE);

        holder.mText_task_des.setText(mData.get(position).getTask_description()+"");

        long now = Long.parseLong(Utils.getTime(Utils.getTimeString()));//系统当前时间
       {
            long ago = Long.parseLong(Utils.getTime(mData.get(position).getCreateDate()));//任务发布时间
            String time = Utils.getDistanceTime2(ago, now);//算出的差值
            holder.mText_task_creattime.setText(time);
            if(mData.get(position).getOrder_amount()==0){
                holder.mText_task_price.setText("");
            }else {
                holder.mText_task_price.setText(mData.get(position).getOrder_amount()+"元");
            }
//            holder.mTextview_distance.setVisibility(View.INVISIBLE);
            switch (mData.get(position).getOrder_status_code()){
                case "07":
                    holder.textview_state.setText("开始服务");
                    break;
                case "09":
                    holder.textview_state.setText("开始服务");

                    break;
                case "05":
                    holder.textview_state.setText("服务中");
                    break;
                case "06":
                    holder.textview_state.setText("待确认");
                    break;
                case "00":
                    holder.textview_state.setText("已完成");
                    break;
                case "01":
                    holder.textview_state.setText("已关闭");
                    break;
                case "02":
                    holder.textview_state.setText("已关闭");
                    break;
            }



        }

//        holder.mText_task_creattime.setText(mData.get(position).getCreateDate()+"");


        holder.mText_task_username.setText(mData.get(position).getNick_name()+"");
        holder.mText_task_address.setText(mData.get(position).getRelease_address()+"");

        holder.mText_task_type.setText(mData.get(position).getSpecialty_name()+"");
        Glide.with(mContext).load(mData.get(position).getHeadUrl()).into(holder.mImage_view);

        return convertView;
    }
    class  ViewHolder {
        TextView mText_task_des;//任务描述
        TextView mText_task_type ;//任务类型
        TextView mText_task_creattime ;//任务创建时间
        TextView mText_task_username ;//任务的发帖人
        TextView mText_task_address ;//发布任务的地址
        TextView mText_task_price ;//任务的佣金
        TextView mTextview_distance;
        TextView textview_state;//任务完成状态
        ImageView mImage_view;//头像
        ImageView image_line;//分隔条


    }
}
