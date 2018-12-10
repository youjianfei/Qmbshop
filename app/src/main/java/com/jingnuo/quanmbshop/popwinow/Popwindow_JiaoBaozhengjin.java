package com.jingnuo.quanmbshop.popwinow;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_jubao;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.activity.BaozhengjinActivity;
import com.jingnuo.quanmbshop.utils.Utils;

public class Popwindow_JiaoBaozhengjin {
    View view;
    Activity activity;
    PopupWindow mPopupWindow;

    //控件
    TextView textview_money;
    TextView text_submit;

    String money="";

//    Interence_jubao interence_jubao;

    public Popwindow_JiaoBaozhengjin(String  money,Activity activity) {
        this.activity = activity;
        this.money=money;
//        this.interence_jubao = interence_jubao;
    }

    public void  showPopwindow(){
        view= LayoutInflater.from(activity).inflate(R.layout.popwindow_jaobaozhengjian,null,false);
        mPopupWindow=new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popskilltype_animation);
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        initview();
        initlistenner();
        Utils.setAlpha((float) 0.3,activity);
    }
    private void initview() {
        textview_money=view.findViewById(R.id.textview_money);
        text_submit=view.findViewById(R.id.text_submit);
        textview_money.setText(money);
    }

    private void initlistenner() {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setAlpha((float) 1,activity);

            }
        });
        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent=new Intent(activity, BaozhengjinActivity.class);
                activity.startActivity(intent);

            }
        });

    }

}
