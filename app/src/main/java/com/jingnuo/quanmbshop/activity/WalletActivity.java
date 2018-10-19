package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.utils.ToastUtils;

public class WalletActivity extends BaseActivityother {
    LinearLayout Lineatlayout_wallte;
    LinearLayout Lineatlayout_mingxi;
    LinearLayout linerlayout_tixian;
    TextView  mTextview;

    String money="";

    @Override
    public int setLayoutResID() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        money=intent.getStringExtra("money");
        mTextview.setText("¥ "+money);
    }

    @Override
    protected void initListener() {
        Lineatlayout_mingxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent_mingxi=new Intent(WalletActivity.this,MonryMingxiActivity.class);
            startActivity(intent_mingxi);
            }
        });
        Lineatlayout_wallte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(WalletActivity.this,"暂不可用");
            }
        });
        linerlayout_tixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_cash = new Intent(WalletActivity.this, CashoutActivity.class);
                    intent_cash.putExtra("money",money+"");
                    intent_cash.putExtra("TransferType","3");
                startActivity(intent_cash);
            }
        });

    }

    @Override
    protected void initView() {
        Lineatlayout_wallte=findViewById(R.id.linear_bankcard);
        Lineatlayout_mingxi=findViewById(R.id.linerlayout_mingxi);
        linerlayout_tixian=findViewById(R.id.linerlayout_tixian);
        mTextview=findViewById(R.id.text_money);
    }

}
