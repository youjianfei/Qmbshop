package com.jingnuo.quanmbshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingnuo.quanmbshop.Interface.Interence_jubao;
import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.activity.LuntanDetailActivity;
import com.jingnuo.quanmbshop.customview.MyGridView;
import com.jingnuo.quanmbshop.customview.MyListView;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.BBSXiangqingBean;
import com.jingnuo.quanmbshop.popwinow.Popwindow_Luntanpinglun_huifu;
import com.jingnuo.quanmbshop.popwinow.Popwindow_Tip;
import com.jingnuo.quanmbshop.popwinow.Popwindow_jubao1;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 飞 on 2018/12/25.
 * 论坛详情下面全部回复大列表适配器
 */

public class Adapter_LuntanDetailsHuifu extends BaseAdapter {
    Context  mContext;
    LayoutInflater mLayoutinflater;
    List<BBSXiangqingBean.DataBean.CommentListBean> mData;
    InterfacePermission interfacePermission;

    public Adapter_LuntanDetailsHuifu(List mDatas, Context mContext, InterfacePermission interfacePermission) {
        super(mDatas, mContext);
        this.mData=mDatas;
       this. mContext=mContext;
       this.interfacePermission =interfacePermission;
        mLayoutinflater=LayoutInflater.from(mContext);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderRR   viewHolder=null;
        if(viewHolder==null){
            viewHolder=new ViewHolderRR();
            convertView=mLayoutinflater.inflate(R.layout.item_luntandetails_huifu,null,false);
            viewHolder.user_pic=convertView.findViewById(R.id.user_pic);
            viewHolder.textview_name=convertView.findViewById(R.id.textview_name);
            viewHolder.textview_time=convertView.findViewById(R.id.textview_time);
            viewHolder.textview_content=convertView.findViewById(R.id.textview_content);
            viewHolder.textview_dianzanCount=convertView.findViewById(R.id.textview_dianzanCount);
            viewHolder.listview_pinglun=convertView.findViewById(R.id.listview_pinglun);
            viewHolder.imageview_dianzan=convertView.findViewById(R.id.imageview_dianzan);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolderRR) convertView.getTag();
        }
        Glide.with(mContext).load(mData.get(position).getHead_url()).into(viewHolder.user_pic);
        viewHolder.textview_name.setText(mData.get(position).getBusiness_name());
        viewHolder.textview_time.setText(mData.get(position).getCreatedate());
        viewHolder.textview_content.setText(mData.get(position).getContent());
        viewHolder.textview_dianzanCount.setText(mData.get(position).getLikes()+"");

        if(mData.get(position).getAppBbsList()!=null){
            viewHolder.listview_pinglun.setVisibility(View.VISIBLE);
            Adapter_pinglun adapter_pinglun=new Adapter_pinglun(mData.get(position).getAppBbsList(),mContext);
            viewHolder.listview_pinglun.setAdapter(adapter_pinglun);
        }else {
            viewHolder.listview_pinglun.setVisibility(View.GONE);
        }
        {//回复评论
            viewHolder.textview_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              new Popwindow_Luntanpinglun_huifu(mData.get(position).getBusiness_name(),(Activity) mContext, new Interence_jubao() {
                  @Override
                  public void onResult(final String result) {
                      Map map=new HashMap();
                      map.put("rootId", mData.get(position).getRootid()+ "");
                      map.put("ID", mData.get(position).getID() + "");
                      map.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
                      map.put("content", result);
                      new Volley_Utils(new Interface_volley_respose() {
                          @Override
                          public void onSuccesses(String respose) {
                              LogUtils.LOG("ceshi", respose, "详情评论");
                              int status = 0;
                              String msg = "";
                              try {
                                  JSONObject object = new JSONObject(respose);
                                  status = (Integer) object.get("code");//登录状态
                                  msg = (String) object.get("message");//登录返回信息
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }
                              if (status == 1) {
                                  ToastUtils.showToast(mContext, msg);
                                  BBSXiangqingBean.DataBean.CommentListBean.AppBbsListBean appBbsListBean=
                                          new BBSXiangqingBean.DataBean.CommentListBean.AppBbsListBean();
                                  appBbsListBean.setBusiness_name(Staticdata.static_userBean.getData().getAppuser().getBusiness_name());
                                  appBbsListBean.setContent(result);
                                mData.get(position).getAppBbsList().add(appBbsListBean);
                                  interfacePermission.onResult(true);
                              } else {
                                  ToastUtils.showToast(mContext, msg);
                              }


                          }

                          @Override
                          public void onError(int error) {

                          }
                      }).postHttp(Urls.Baseurl_cui + Urls.bbs_xiangqingpinglun, mContext, 1, map);

                  }
              }).showPopwindow();
                }
            });

        }
        {//点赞
            final ViewHolderRR finalViewHolder = viewHolder;
            viewHolder.   imageview_dianzan.setSelected(mData.get(position).getIsLike().contains("N")?false:true);
            viewHolder.imageview_dianzan.setOnClickListener(new View.OnClickListener() {
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
                                    finalViewHolder.imageview_dianzan.setSelected(true);
                                    finalViewHolder.textview_dianzanCount.setText(mData.get(position).getLikes()+"");

                                }else {
                                    mData.get(position).setIsLike("N");
                                    mData.get(position).setLikes(mData.get(position).getLikes()-1);
                                    finalViewHolder.imageview_dianzan.setSelected(false);
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

        return convertView;
    }
    class ViewHolderRR {
        CircleImageView user_pic;
        TextView textview_name;
        TextView textview_time;
        TextView textview_content;
        TextView textview_dianzanCount;
        MyListView listview_pinglun;
        ImageView imageview_dianzan;
    }


    class Adapter_pinglun extends BaseAdapter{
        List<BBSXiangqingBean.DataBean.CommentListBean.AppBbsListBean>mDatas;
        LayoutInflater layoutInflater;

        public Adapter_pinglun(List mDatas, Context mContext) {
            super(mDatas, mContext);
            this.mDatas=mDatas;
            layoutInflater=LayoutInflater.from(mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=layoutInflater.inflate(R.layout.item_luntandetails_huifu_pinglun,null,false);
            TextView textview_name=convertView.findViewById(R.id.textview_name);
            TextView textview_content=convertView.findViewById(R.id.textview_content);

            textview_name.setText(mDatas.get(position).getBusiness_name()+":");
            textview_content.setText(mDatas.get(position).getContent());

            return convertView;
        }
    }
}
