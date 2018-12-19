package com.jingnuo.quanmbshop.popwinow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.SizeUtils;
import com.jingnuo.quanmbshop.utils.Utils;


/**
 * Created by Administrator on 2018/4/10.
 */

public class Popwindow_weigui {
    View  view;
    PopupWindow mPopupWindow;
    private Activity activity;

    //控件
    LinearLayout linearLayout_coupon;

    public Popwindow_weigui(Activity activity  ) {
        this.activity = activity;
    }

    public  void showpopwindow(){
        view  = LayoutInflater.from(activity).inflate(R.layout.popwindow_weiguichufa,null,false);
        mPopupWindow=new PopupWindow(view, Staticdata.ScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popissue_animation);
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        initview();
        initlistenner();
        Staticdata.isshow=false;//是否可以显示

    }

    private void initlistenner() {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Staticdata.isshow=true;//是否可以显示
                Utils.setAlpha((float)1,activity);
            }
        });

    }

    @SuppressLint("WrongViewCast")
    private void initview() {
        Utils.setAlpha((float) 0.3,activity);
        linearLayout_coupon=view.findViewById(R.id.iamage_coupon);
        ImageView image = new ImageView(activity);
        image.setBackgroundResource(R.mipmap.weiguichufa);
        int w=Staticdata.ScreenWidth- SizeUtils.dip2px(activity,40);
        int h= (int) (w*0.83);
        LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(w, h);
        image.setLayoutParams(mLayoutparams);
        linearLayout_coupon.addView(image);

        linearLayout_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }


}
