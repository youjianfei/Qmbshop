package com.jingnuo.quanmbshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.Interface.SendYanZhengmaSuccess;
import com.jingnuo.quanmbshop.class_.SendYanZhengMa;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChangephoneNumberNextActivity extends BaseActivityother {

    //控件
    EditText mEdit_phonenumber;
    EditText mEdit_yanzhengma;
    Button mButton_yanchengma;
    Button mButotn_sumit;

    //数据
    String phonenumber = "";

    Map map_bindphonenumber;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_changephone_number_next;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        map_bindphonenumber = new HashMap();
        map_bindphonenumber.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());

    }

    @Override
    protected void initListener() {
        mButton_yanchengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumber = mEdit_phonenumber.getText() + "";
                if (phonenumber.equals("")) {
                    ToastUtils.showToast(ChangephoneNumberNextActivity.this, "请输入手机号");
                } else {
                    map_bindphonenumber.put("phoneNumbers", phonenumber);
                    new SendYanZhengMa(new SendYanZhengmaSuccess() {
                        @Override
                        public void onSuccesses(String yanzhengma) {
                            ToastUtils.showToast(ChangephoneNumberNextActivity.this, yanzhengma);
                        }
                    }, mButton_yanchengma).sendyanzhengma(ChangephoneNumberNextActivity.this, map_bindphonenumber);
                }

            }
        });

        mButotn_sumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yanzhengam = mEdit_yanzhengma.getText() + "";
                if (yanzhengam.equals("")) {
                    ToastUtils.showToast(ChangephoneNumberNextActivity.this, "请输入验证码");
                    return;
                }
                map_bindphonenumber.put("ValidateCode", yanzhengam);
                map_bindphonenumber.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
                map_bindphonenumber.put("type","1");
                new Volley_Utils(new Interface_volley_respose() {
                    @Override
                    public void onSuccesses(String respose) {
                        int status = 0;
                        String msg = "";
                        try {
                            JSONObject object = new JSONObject(respose);
                            status = (Integer) object.get("code");//登录状态
                            msg = (String) object.get("message");//登录返回信息
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status != 1) {
                            ToastUtils.showToast(ChangephoneNumberNextActivity.this, msg);
                            return;
                        }
                        ToastUtils.showToast(ChangephoneNumberNextActivity.this, msg);
                        Staticdata.Userphonenumber=phonenumber;
                        finish();

                    }

                    @Override
                    public void onError(int error) {

                    }
                }).postHttp(Urls.Baseurl + Urls.bindphonenumber, ChangephoneNumberNextActivity.this, 1, map_bindphonenumber);


            }
        });

    }

    @Override
    protected void initView() {
        mEdit_phonenumber = findViewById(R.id.edit_newphonenumber);
        mEdit_yanzhengma = findViewById(R.id.edit_yanzhengma);
        mButton_yanchengma = findViewById(R.id.button_yanzhengma);
        mButotn_sumit = findViewById(R.id.button_submit);
    }
}
