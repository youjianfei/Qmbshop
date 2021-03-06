package com.jingnuo.quanmbshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.SizeUtils;

public class HelperguizeActivity extends BaseActivityother {
    TextView text_title;
    LinearLayout relative;

    String title="";

    int hight=0;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_helperguize;
    }

    @Override
    protected void setData() {
//        ImageView image = new ImageView(HelperguizeActivity.this);
        if(title.equals("商家规则")){
            ImageView image = new ImageView(HelperguizeActivity.this);
            ImageView image2 = new ImageView(HelperguizeActivity.this);
            ImageView image3 = new ImageView(HelperguizeActivity.this);
            ImageView image4 = new ImageView(HelperguizeActivity.this);
            ImageView image5 = new ImageView(HelperguizeActivity.this);
            ImageView image6 = new ImageView(HelperguizeActivity.this);
            ImageView image7= new ImageView(HelperguizeActivity.this);
            image.setBackgroundResource(R.mipmap.shangjiaguize1);
            image2.setBackgroundResource(R.mipmap.shangjiaguize2);
            image3.setBackgroundResource(R.mipmap.shangjiaguize3);
            image4.setBackgroundResource(R.mipmap.shangjiaguize4);
            image5.setBackgroundResource(R.mipmap.shangjiaguize5);
            image6.setBackgroundResource(R.mipmap.shangjiaguize6);
            image7.setBackgroundResource(R.mipmap.shangjiaguize7);
            hight=(int) (Staticdata.ScreenWidth * 0.85);
            LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(Staticdata.ScreenWidth,hight );
            image.setLayoutParams(mLayoutparams);
            image2.setLayoutParams(mLayoutparams);
            image3.setLayoutParams(mLayoutparams);
            image4.setLayoutParams(mLayoutparams);
            image5.setLayoutParams(mLayoutparams);
            image6.setLayoutParams(mLayoutparams);
            image7.setLayoutParams(mLayoutparams);
            relative.addView(image);
            relative.addView(image2);
            relative.addView(image3);
            relative.addView(image4);
            relative.addView(image5);
            relative.addView(image6);
            relative.addView(image7);
//            image4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
        }else   if(title.equals("商家教学")){

            ImageView image = new ImageView(HelperguizeActivity.this);
            ImageView image2 = new ImageView(HelperguizeActivity.this);
            ImageView image3 = new ImageView(HelperguizeActivity.this);
            ImageView image4 = new ImageView(HelperguizeActivity.this);
            ImageView image5 = new ImageView(HelperguizeActivity.this);
            image.setBackgroundResource(R.mipmap.shangjiajiaoxue1);
            image2.setBackgroundResource(R.mipmap.shangjiajiaoxue2);
            image3.setBackgroundResource(R.mipmap.shangjiajiaoxue3);
            image4.setBackgroundResource(R.mipmap.shangjiajiaoxue4);
            image5.setBackgroundResource(R.mipmap.shangjiajiaoxue5);
            hight=(int) (Staticdata.ScreenWidth * 1.02);
            LinearLayout.LayoutParams mLayoutparams = new LinearLayout.LayoutParams(Staticdata.ScreenWidth,hight );
            image.setLayoutParams(mLayoutparams);
            image2.setLayoutParams(mLayoutparams);
            image3.setLayoutParams(mLayoutparams);
            image4.setLayoutParams(mLayoutparams);
            image5.setLayoutParams(mLayoutparams);
            relative.addView(image);
            relative.addView(image2);
            relative.addView(image3);
            relative.addView(image4);
            relative.addView(image5);
            image5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }


    }

    @Override
    protected void initData() {
        Staticdata.ScreenHight = SizeUtils.getScreenHeightPx(this);
        Staticdata.ScreenWidth = SizeUtils.getScreenWidthPx(this);
        title=getIntent().getStringExtra("title");
        text_title.setText(title);
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initView() {
        text_title=findViewById(R.id.text_title);
        relative=findViewById(R.id.relative);

    }
}
