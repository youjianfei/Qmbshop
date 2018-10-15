package com.jingnuo.quanmbshop.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SetNicknameActivity extends BaseActivityother {

    //控件
    TextView mTextview_submit;
    EditText mEdit_nickname;
    //数据
    String nickname="";

    Map map_nickname;


    @Override
    public int setLayoutResID() {
        return R.layout.activity_set_nickname;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        map_nickname=new HashMap();
    }

    @Override
    protected void initListener() {
        mTextview_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname=mEdit_nickname.getText()+"";
                nickname=nickname.trim();
                int lenth=Utils.length(nickname);
                if(lenth<0||lenth>16){
                    ToastUtils.showToast(SetNicknameActivity.this,"用户名限1~16个字符,一个汉字为两个字符");
                }else {
                        map_nickname.put("NickName",nickname);
                        map_nickname.put("user_token", Staticdata.static_userBean.getData().getAppuser().getUser_token());
                    LogUtils.LOG("ceshi","修改昵称的map"+map_nickname.toString(),"SetNicknameActivity");
                    request(map_nickname);
                }
                LogUtils.LOG("ceshi","修改昵称的位数"+Utils.length(nickname),"SetNicknameActivity");
//                request(map_nickname);
            }
        });

    }

    @Override
    protected void initView() {
        mTextview_submit=findViewById(R.id.text_queding);
        mEdit_nickname=findViewById(R.id.edit_oldpassword);

    }
    void request(Map map){
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
                if(status==1){
//                    Staticdata.static_userBean.getData().getAppuser().setNick_name(nickname);
                    ToastUtils.showToast(SetNicknameActivity.this,msg);
                    finish();
                }else {
                    ToastUtils.showToast(SetNicknameActivity.this,msg);
                }

            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl+Urls.setnickname,SetNicknameActivity.this,1,map);



    }
}
