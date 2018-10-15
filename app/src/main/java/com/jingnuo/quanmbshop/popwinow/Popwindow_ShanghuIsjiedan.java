package com.jingnuo.quanmbshop.popwinow;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.InterfacePermission;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.utils.Utils;

public class Popwindow_ShanghuIsjiedan {
    View conView;
    private Activity activity;
    private RelativeLayout mRelativeLayout;//用于定位 popwindow弹出的位置
    private InterfacePermission interfacePermission;
    private String  title;

    TextView mTextview;

    boolean isTuisong=true;



    PopupWindow mPopupWindow;

    public Popwindow_ShanghuIsjiedan(Activity activity, RelativeLayout mRelativeLayout, InterfacePermission interfacePermission, String title) {
        this.activity = activity;
        this.mRelativeLayout = mRelativeLayout;
        this.interfacePermission = interfacePermission;
        this.title = title;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showPopwindow() {

        //初始化popwindow；
        conView = LayoutInflater.from(activity).inflate(R.layout.popwindow_shanghuisjiedan, null, false);
        mPopupWindow = new PopupWindow(conView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popmenu_animation);
        mPopupWindow.showAsDropDown(mRelativeLayout, 0, 0, Gravity.BOTTOM);
        Utils.setAlpha((float) 0.3,activity);
        initview();
        initdata();
        initlistenner();
    }

    private void initdata() {
        mTextview.setText(title);
    }
    private void initview() {
        mTextview=conView.findViewById(R.id.text_isjiedan);
    }

    private void initlistenner() {
        mTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTuisong){
                    interfacePermission.onResult(false);
                    mPopupWindow.dismiss();
                }else {
                    interfacePermission.onResult(true);
                    mPopupWindow.dismiss();
                }
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setAlpha((float) 1,activity);
            }
        });
    }

}
