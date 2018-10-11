package com.jingnuo.quanmbshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.jingnuo.quanmbshop.Interface.Interface_volley_respose;
import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Staticdata;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.entityclass.NewMessage_Bean;
import com.jingnuo.quanmbshop.utils.LogUtils;
import com.jingnuo.quanmbshop.utils.Volley_Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class ConversationListActivity extends FragmentActivity {


    String  newmessageTYpe="";

    //控件
//    RelativeLayout mRelativelayout_bargain;
    RelativeLayout mRelativelayout_systemmessage;
    RelativeLayout mRelativelayout_dealmessage;
//    RelativeLayout mRelativelayout_tuijianrenwu;


    //控件
    LinearLayout LinearLayout_taskmain;
    LinearLayout LinearLayout_messagemain;
    ImageView imageview_task;
    ImageView image_messgae;
    TextView  text_task;
    TextView  text_message;
//    ImageView iv_back;

//    TextView mTextview_systemmessage;
//    TextView mTextview_bargainmessage;
//    TextView mTextview_jiaoyimeaage;
//    TextView mTextview_tuijianrenwu;

    static ImageView mImageView_dot1;
    static ImageView mImageView_dot2;
    static ImageView mImageView_dot3;
    static ImageView mImageView_dot4;


    //数据
    Map map_getnewmessage;

    NewMessage_Bean newMessage_bean;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black), 0);//状态栏颜色
        initview();
        initdata();
        setdata();
        initListener();
        ConversationListFragment listFragment = (ConversationListFragment) ConversationListFragment.instantiate(this, ConversationListFragment.class.getName());
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .build();
        listFragment.setUri(uri);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //将融云的Fragment界面加入到我们的页面。
        transaction.add(R.id.conversationlist, listFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initListener() {
        LinearLayout_taskmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ConversationListActivity.this, ShanghuMainActivity.class);
                startActivity(intent);
            }
        });

//        iv_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        mRelativelayout_systemmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_system=new Intent(ConversationListActivity.this, SystemMessageActivity.class);
                mImageView_dot1.setVisibility(View.GONE);
                startActivity(intent_system);
            }
        });
        mRelativelayout_dealmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_deal=new Intent(ConversationListActivity.this, DealActivity.class);
                mImageView_dot3.setVisibility(View.GONE);
                startActivity(intent_deal);
            }
        });

    }

    private void setdata() {
        LinearLayout_messagemain.setSelected(true);
        LinearLayout_taskmain.setSelected(false);
        image_messgae.setSelected(true);
        imageview_task.setSelected(false);
        text_task.setTextColor(this.getResources().getColor(R.color.black_text2));
        text_message.setTextColor(this.getResources().getColor(R.color.yellow_jianbian_end));



        map_getnewmessage=new HashMap();
        map_getnewmessage.put("receive_client_no", Staticdata.static_userBean.getData().getAppuser().getClient_no());
        map_getnewmessage.put("user_token",Staticdata.static_userBean.getData().getUser_token());
        request();
    }

    private void initdata() {
        newmessageTYpe=getIntent().getStringExtra("newmessageTYpe");
        setDot(newmessageTYpe);

    }

    private void initview() {
        LinearLayout_taskmain=findViewById(R.id.LinearLayout_taskmain);
        LinearLayout_messagemain=findViewById(R.id.LinearLayout_messagemain);
        imageview_task=findViewById(R.id.imageview_task);
        image_messgae=findViewById(R.id.image_messgae);
        text_task=findViewById(R.id.text_task);
        text_message=findViewById(R.id.text_message);


        mRelativelayout_systemmessage=findViewById(R.id.relativelayout_systemnotice);
        mRelativelayout_dealmessage=findViewById(R.id.relativelayout_Jiaoyinotice);
        mImageView_dot1=findViewById(R.id.image_reddot1);
        mImageView_dot2=findViewById(R.id.image_reddot2);
        mImageView_dot3=findViewById(R.id.image_reddot3);
        mImageView_dot4=findViewById(R.id.image_reddot4);
        
    }
    public   void setDot(String num){
        LogUtils.LOG("ceshi",num+"","推送3");
        switch (num){
            case "type1":
                mImageView_dot1.setVisibility(View.VISIBLE);
                break;
            case "type2":
                mImageView_dot2.setVisibility(View.VISIBLE);
                break;
            case "type3":
                mImageView_dot3.setVisibility(View.VISIBLE);
                break;
            case "type4":
                mImageView_dot4.setVisibility(View.VISIBLE);
                break;
        }
    }
    void request(){
        new Volley_Utils(new Interface_volley_respose() {
            @Override
            public void onSuccesses(String respose) {
                newMessage_bean=new Gson().fromJson(respose,NewMessage_Bean.class);
            }

            @Override
            public void onError(int error) {

            }
        }).postHttp(Urls.Baseurl_hu+Urls.getNewmessage,ConversationListActivity.this,1,map_getnewmessage);

    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Staticdata.isLogin){
            map_getnewmessage.put("receive_client_no", Staticdata.static_userBean.getData().getAppuser().getClient_no());
            map_getnewmessage.put("user_token",Staticdata.static_userBean.getData().getUser_token());
            request();
        }
    }
}
