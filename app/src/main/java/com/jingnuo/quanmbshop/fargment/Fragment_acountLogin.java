package com.jingnuo.quanmbshop.fargment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.activity.FindPasswordActivity;
import com.jingnuo.quanmbshop.activity.MainActivity;
import com.jingnuo.quanmbshop.popwinow.ProgressDlog;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.UserBean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.PasswordJiami;
import com.jingnuo.quanmbshop.utils.SharedPreferencesUtils;
import com.jingnuo.quanmbshop.utils.ToastUtils;
import com.jingnuo.quanmbshop.utils.Utils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;
import com.jingnuo.quanmbshop.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static com.jingnuo.quanmbshop.data.Staticdata.UUID;
import static com.jingnuo.quanmbshop.data.Staticdata.Userphonenumber;
import static com.jingnuo.quanmbshop.data.Staticdata.isLogin;

/**
 * Created by Administrator on 2018/3/20.
 */

public class Fragment_acountLogin extends Fragment {
    View rootview;
    //控件
    Button mButton_login;
    TextView mtextview_forgotpassword;
    EditText medit_account, medit_password;

    //数据
    String account = "";
    String password = "";
    String publicEncryptedResult = "";

    //对象
    PublicKey publicKey;
    Map map_login;
    UserBean userBean;

    ProgressDlog progressDlog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_acount_login, container, false);
        initview();
        initdata();
        initlinster();


        return rootview;

    }

    private void initdata() {
        progressDlog=new ProgressDlog(getActivity());
        account=SharedPreferencesUtils.getString(getActivity(),"QMB","phonenumber");
        medit_account.setText(account);


    }

    private void initlinster() {
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = medit_account.getText().toString();
                password = medit_password.getText().toString();
                if (account.equals("") || password.equals("")) {
                    ToastUtils.showToast(getActivity(), "请填写账号密码");
                } else {
                    SharedPreferencesUtils.putString(getActivity(),"QMB","phonenumber",account);//存电话号


                    publicEncryptedResult= PasswordJiami.passwordjiami(password);//对密码加密
                    LogUtils.LOG("ceshi", publicEncryptedResult + "1111111111", "fragment_account");

                    map_login = new HashMap();
                    Userphonenumber=account;//将电话号设为全局变量
                    map_login.put("username", account);
                    map_login.put("Jpush_id", Staticdata.JpushID);
                    map_login.put("password", publicEncryptedResult);
                    map_login.put("uuid", UUID);
                    //登陆成功后 设置全局变量islogin为 true
                    requestlogin(map_login);//登陆请求
                }


            }
        });
        mtextview_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_findpassword = new Intent(getActivity(), FindPasswordActivity.class);
                getActivity().startActivity(intent_findpassword);
            }
        });
    }

    private void requestlogin(Map map) {
//        Request_retrofit.retrofit_post(map);
        progressDlog.showPD("正在登录中");
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                progressDlog.cancelPD();
                int  status=0;
                String msg="";
                try {
                    JSONObject object=new JSONObject(respose);
                    status = (Integer) object.get("code");//登录状态
                    msg = (String) object.get("message");//登录返回信息
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status==1){//登录成功
                    SharedPreferencesUtils.putString(getActivity(),"QMB","password",password);//登录成功之后存未加密de密码
                    userBean=new Gson().fromJson(respose,UserBean.class);
                   Staticdata. static_userBean=userBean;
                    Userphonenumber=userBean.getData().getAppuser().getMobile_no();//将电话号设为全局变量
                    LogUtils.LOG("ceshi", respose + "1111111111", "fragment_account");
                    isLogin = true;
                    Utils.connect(userBean.getData().getAppuser().getRongCloud_token());
                    Intent intent_login = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent_login);
                    getActivity().finish();
                }else {
                    ToastUtils.showToast(getActivity(),msg);
                }

            }

            @Override
            public void onError(int error) {
                progressDlog.cancelPD();
                ToastUtils.showToast(getActivity(),"网络连接失败");
            }
        }).postHttp(Urls.Baseurl+Urls.login, getActivity(), 1, map);
    }


    private void initview() {
        medit_account = rootview.findViewById(R.id.edit_account);
        medit_password = rootview.findViewById(R.id.edit_password);
        mButton_login = rootview.findViewById(R.id.button_login);
        mtextview_forgotpassword = rootview.findViewById(R.id.textview_forgotpassword);
    }




}
