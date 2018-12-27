package com.jingnuo.quanmbshop.popwinow;

import android.app.Activity;
import android.test.mock.MockContext;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interence_jubao;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;

public class Popwindow_Luntanpinglun_huifu {
    View view;
    Activity activity;
    PopupWindow mPopupWindow;

    //控件
    TextView text_name;
    TextView text_submit;
    EditText  edit_content;

    String  name="";


    Interence_jubao interence_jubao;

    public Popwindow_Luntanpinglun_huifu(String  name,Activity activity, Interence_jubao interence_jubao) {
        this.activity = activity;
        this.interence_jubao = interence_jubao;
        this.name=name;
    }

    public void  showPopwindow(){
        view= LayoutInflater.from(activity).inflate(R.layout.popwindow_luntanhuifu,null,false);
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
        text_name=view.findViewById(R.id.text_name);
        text_submit=view.findViewById(R.id.text_submit);
        edit_content=view.findViewById(R.id.edit_content);

        text_name.setText("回复 "+name+":");
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
                String aaaa=edit_content.getText().toString().trim();
                if(!aaaa.equals("")){
                    interence_jubao.onResult(aaaa);
                    mPopupWindow.dismiss();
                }else {
                    ToastUtils.showToast(activity,"不能为空");
                }

            }
        });
    }

}
