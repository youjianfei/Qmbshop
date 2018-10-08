package com.jingnuo.quanmbshop.popwinow;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_bargin;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.MoneyTextWatcher;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.R;
public class Popwindow_addPrice {
    View view;
    PopupWindow mPopupWindow;
    Activity activity;


    //控件
    ImageView mImageview_close;
    EditText mEdit_money;
    TextView mtextview_queren;

    //数据
    String money="";

    //对象
    Interence_bargin  interence_bargin;


    public Popwindow_addPrice(Activity activity, Interence_bargin interence_bargin) {
        this.activity = activity;
        this.interence_bargin=interence_bargin;
    }
    public  void  showpop(){
        view  = LayoutInflater.from(activity).inflate(R.layout.popwindow_addprice,null,false);
        mPopupWindow=new PopupWindow(view, (int) (Staticdata.ScreenWidth*0.8), ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopupWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失
        mPopupWindow.setFocusable(true);//设置焦点在window上
        mPopupWindow.setAnimationStyle(R.style.popissue_animation);
        mPopupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        Utils.setAlpha((float) 0.3,activity);

        initview();
        initlistenner();
    }

    private void initlistenner() {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.setAlpha((float) 1,activity);
            }
        });
        mEdit_money.addTextChangedListener(new MoneyTextWatcher(mEdit_money).setDigits(2));
        mImageview_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mtextview_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money=mEdit_money.getText()+"";
                if(money.equals("")){
                    ToastUtils.showToast(activity,"请输入金额");
                }else {
                    interence_bargin.onResult(money);
                    mPopupWindow.dismiss();
                }
            }
        });

    }

    private void initview() {
        mImageview_close=view.findViewById(R.id.iamge_close);
        mEdit_money=view.findViewById(R.id.edit_bargin);
        mtextview_queren=view.findViewById(R.id.text_queren);
    }


}
