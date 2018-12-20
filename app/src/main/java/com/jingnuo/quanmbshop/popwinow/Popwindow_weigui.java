package com.jingnuo.quanmbshop.popwinow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.InterfacePermission;
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

    InterfacePermission interfacePermission;

    //控件
    TextView  textview_one;
    TextView  textview_two;
    TextView  textview_three;
    Button button;

    String  one="";
    String two="";
    String  three="";
    String  four="";


    public Popwindow_weigui(Activity activity, String text, String money, String level , String day , InterfacePermission interfacePermission) {
        this.activity = activity;
        this.one=text;
        this.two=money;
        this.three=level;
        this.four=day;
        this.interfacePermission=interfacePermission;
    }

    public  void showpopwindow(){
        view  = LayoutInflater.from(activity).inflate(R.layout.popwindow_weiguichufa,null,false);
        mPopupWindow=new PopupWindow(view, (int) (Staticdata.ScreenWidth*0.9), ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popissue_animation);
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        initview();
        initdata();
        initlistenner();
        Staticdata.isshow=false;//是否可以显示
    }

    private void initdata() {
        textview_one.setText(one+"根据平台商家管理规定，给予一下处罚：");
        textview_two.setText("扣除保证金"+two+"元；商家等级等级直降"+three+"级；冻结账户"+four+"天。");
    }

    private void initlistenner() {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Staticdata.isshow=true;//是否可以显示
                Utils.setAlpha((float)1,activity);
                interfacePermission.onResult(true);
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initview() {
        Utils.setAlpha((float) 0.3,activity);
        textview_one=view.findViewById(R.id.textview_one);
        textview_two=view.findViewById(R.id.textview_two);
        textview_three=view.findViewById(R.id.textview_three);
        button=view.findViewById(R.id.button);







//        linearLayout_coupon=view.findViewById(R.id.iamage_coupon);
//        ImageView image = new ImageView(activity);
//        image.setBackgroundResource(R.mipmap.weiguichufa);
//        int w=Staticdata.ScreenWidth- SizeUtils.dip2px(activity,40);
//        int h= (int) (w*0.83);
//        LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(w, h);
//        image.setLayoutParams(mLayoutparams);
//        linearLayout_coupon.addView(image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }


}
