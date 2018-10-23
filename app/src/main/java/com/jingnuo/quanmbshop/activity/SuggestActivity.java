package com.jingnuo.quanmbshop.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SuggestActivity extends BaseActivityother {

    EditText mEdit_view;
    Button mButton;

    String mysuggest="";
    Map map_suggest;

    String release_specialty_id="";

        String URL;
    @Override
    public int setLayoutResID() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void setData() {
    map_suggest=new HashMap();
    map_suggest.put("business_no", Staticdata.static_userBean.getData().getAppuser().getBusiness_no());
    map_suggest.put("user_token",Staticdata.static_userBean.getData().getAppuser().getUser_token());
        if(release_specialty_id!=null){
            URL=Urls.Baseurl+Urls.mySuggest;
            map_suggest.put("release_specialty_id",release_specialty_id);
        }else {
            URL=Urls.Baseurl+Urls. mySFeedBack;
        }

    }

    @Override
    protected void initData() {
        release_specialty_id=getIntent().getStringExtra("release_specialty_id");
    }

    @Override
    protected void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysuggest=mEdit_view.getText()+"";
                if(mysuggest.equals("")){
                    ToastUtils.showToast(SuggestActivity.this,"投诉和建议不能为空");
                }else {
                    map_suggest.put("content",mysuggest);
                    LogUtils.LOG("ceshi",Urls.Baseurl+Urls.mySuggest,"投诉和建议");
                    LogUtils.LOG("ceshi",map_suggest.toString(),"投诉和建议");
                    new Volley_Utils(new Interface_volley_respose() {
                        @Override
                        public void onSuccesses(String respose) {
                            int status = 0;
                            String msg = "";
                            try {
                                JSONObject object = new JSONObject(respose);
                                status = (Integer) object.get("code");//
                                msg = (String) object.get("message");//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(status==1){
                                ToastUtils.showToast(SuggestActivity.this,"我们已经收到你的建议");
                                finish();
                            }else {
                                ToastUtils.showToast(SuggestActivity.this,msg);
                            }


                        }

                        @Override
                        public void onError(int error) {

                        }
                    }).postHttp(URL,SuggestActivity.this,1,map_suggest);
                }


            }
        });
    }

    @Override
    protected void initView() {
        mEdit_view=findViewById(R.id.edit_suggest);
        mButton=findViewById(R.id.button_submit);
    }
}
