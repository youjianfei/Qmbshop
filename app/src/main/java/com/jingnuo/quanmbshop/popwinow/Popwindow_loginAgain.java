package com.jingnuo.quanmbshop.popwinow;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_complteTask;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.R;
/**
 * Created by 飞 on 2018/4/25.
 */

public class Popwindow_loginAgain {
    View view;
    PopupWindow mPopupWindow;
    private Activity activity;

    //控件
    TextView mTextview_cancle;
    TextView mTextview_submit;

    //对象
    Interence_complteTask interence_complteTask;

    public Popwindow_loginAgain(Activity activity, Interence_complteTask interence_complteTask) {
        this.activity = activity;
        this.interence_complteTask=interence_complteTask;
    }

    public Popwindow_loginAgain showPopwindow(){
        view= LayoutInflater.from(activity).inflate(R.layout.popwindow_loginagain,null,false);
        mPopupWindow=new PopupWindow(view, (int) (Staticdata.ScreenWidth*0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(false);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popissue_animation);
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        Utils.setAlpha((float) 0.3,activity);
        initview();
        initlistenner();
        return null;
    }

    private void initlistenner() {
        mTextview_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interence_complteTask.onResult(true);
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setAlpha((float) 1,activity);
            }
        });
    }

    private void initview() {
    mTextview_submit=view.findViewById(R.id.textview_submit);
    }

}
