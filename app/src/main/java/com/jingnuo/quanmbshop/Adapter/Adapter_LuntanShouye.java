package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.class_.ShareGoodWeb;
import com.jingnuo.quanmbshop.customview.MyGridView;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.BBSBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_lookpic;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 飞 on 2018/12/25.
 * 论坛首页大列表适配器
 */

public class Adapter_LuntanShouye extends BaseAdapter {
    Context  mContext;
    LayoutInflater mLayoutinflater;
    List<BBSBean.DataBean> mData;
    ShareGoodWeb shareClass;
    public Adapter_LuntanShouye(List mDatas, Context mContext) {
        super(mDatas, mContext);
        this.mData=mDatas;
       this. mContext=mContext;
        mLayoutinflater=LayoutInflater.from(mContext);
        shareClass = new ShareGoodWeb((Activity) mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            viewHolder.linearlayout_dianzan=convertView.findViewById(R.id.linearlayout_dianzan);
            viewHolder.linearlayout_fenxiang=convertView.findViewById(R.id.linearlayout_fenxiang);
            viewHolder.image_dianzan=convertView.findViewById(R.id.image_dianzan);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolderRR) convertView.getTag();
        }
        viewHolder.textview_name.setText(mData.get(position).getBusiness_name());//名字
        Glide.with(mContext).load(mData.get(position).getHead_url()).into(viewHolder.user_pic);//头像
        viewHolder.textview_content.setText(mData.get(position).getContent());//内容
        if(mData.get(position).getForwards()!=0){
            viewHolder.textview_zhuanfaCount.setText(mData.get(position).getForwards()+"");//分享数
        }
        if(mData.get(position).getCommentCount()!=0){
            viewHolder.textview_huifuCount.setText(mData.get(position).getCommentCount()+"");//评论数
        }
        if(mData.get(position).getLikes()!=0){
            viewHolder.textview_dianzanCount.setText(mData.get(position).getLikes()+"");//点赞数
        }
        {//时间
            long now = Long.parseLong(Utils.getTime(Utils.getTimeString()));//系统当前时间
            long ago = Long.parseLong(Utils.getTime(mData.get(position).getCreatedate()));//任务发布时间
            String time = Utils.getDistanceTime2(ago, now);//算出的差值
            viewHolder.textview_time.setText(time);
        }
        {//点赞
            viewHolder.image_dianzan.setSelected(mData.get(position).getIsLike().contains("N")?false:true);
            final ViewHolderRR finalViewHolder = viewHolder;
            viewHolder.linearlayout_dianzan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                new Volley_Utils(new Interface_volley_respose() {
                    @Override
                    public void onSuccesses(String respose) {
                        int status = 0;
                        String msg = "";
                        String imageID="";
                        try {
                            JSONObject object = new JSONObject(respose);
                            status = (Integer) object.get("code");//
                            msg = (String) object.get("message");//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(status==1){
                            if(mData.get(position).getIsLike().contains("N")){
                                mData.get(position).setIsLike("Y");
                                mData.get(position).setLikes(mData.get(position).getLikes()+1);
                                finalViewHolder.image_dianzan.setSelected(true);
                                finalViewHolder.textview_dianzanCount.setText(mData.get(position).getLikes()+"");

                            }else {
                                mData.get(position).setIsLike("N");
                                mData.get(position).setLikes(mData.get(position).getLikes()-1);
                                finalViewHolder.image_dianzan.setSelected(false);
                                finalViewHolder.textview_dianzanCount.setText(mData.get(position).getLikes()+"");

                            }
                        }
                        ToastUtils.showToast(mContext,msg);


                    }
                    @Override
                    public void onError(int error) {

                    }
                }).Http(Urls.Baseurl_cui+Urls.bbs_dianzan+ Staticdata.static_userBean.getData().getAppuser().getUser_token()+
                "&ID="+mData.get(position).getID(),mContext,0);
                }
            });
        }


        {//图片展示
            String image_url = mData.get(position).getImg_url();
            if (image_url == null || image_url.equals("")) {//没有图片
                viewHolder.mygridview_luntanPIC.setVisibility(View.GONE);
            } else {//有图片
                viewHolder.mygridview_luntanPIC.setVisibility(View.VISIBLE);
                final List<String> imageview_urllist = new ArrayList<>();
                imageview_urllist.clear();
                String[] images = image_url.split(",");
                int len = images.length;
                for (int i = 0; i < len; i++) {
                    imageview_urllist.add(images[i]);
                }
                if (imageview_urllist.size() > 1) {//一张以上
                    viewHolder.mygridview_luntanPIC.setNumColumns(3);
                    Adapter_LuntanShouye_pic adapter_luntanShouye_pic = new Adapter_LuntanShouye_pic(imageview_urllist, mContext);
                    viewHolder.mygridview_luntanPIC.setAdapter(adapter_luntanShouye_pic);
                } else {//只有一张
                    viewHolder.mygridview_luntanPIC.setNumColumns(1);
                    Adapter_LuntanShouye_pic adapter_luntanShouye_pic = new Adapter_LuntanShouye_pic(imageview_urllist, mContext);
                    viewHolder.mygridview_luntanPIC.setAdapter(adapter_luntanShouye_pic);
                }
                viewHolder.mygridview_luntanPIC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        new   Popwindow_lookpic((Activity) mContext).showPopwindow(position,imageview_urllist);
                    }
                });
            }

        }
        {//分享
            viewHolder.linearlayout_fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    shareClass.shareapp();
                }
            });
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
        LinearLayout linearlayout_dianzan;
        LinearLayout linearlayout_fenxiang;
        ImageView image_dianzan;
    }

}
