package com.jingnuo.quanmbshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jingnuo.quanmbshop.R;

public class BaozhengjinTuikuanActivity extends BaseActivityother {
    //控件
    TextView textview_tuikuan;
    Button  button_yes;



    //数据
    String money="";


    @Override
    public int setLayoutResID() {
        return R.layout.activity_baozhengjin_tuikuan;
    }

    @Override
    protected void setData() {
        textview_tuikuan.setText(money);
    }

    @Override
    protected void initData() {
        money=getIntent().getStringExtra("money");
    }

    @Override
    protected void initListener() {
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        textview_tuikuan=findViewById(R.id.textview_tuikuan);
        button_yes=findViewById(R.id.button_yes);
    }
}
