package com.jingnuo.quanmbshop.activity;

import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jingnuo.quanmbshop.R;
import com.jingnuo.quanmbshop.data.Urls;
import com.jingnuo.quanmbshop.utils.LogUtils;

public class GuanggaoWeb extends BaseActivityother {
    //控件
    private WebView webView;
    TextView  titlename;
    ProgressBar mPrigressBer;

    String  URL="";

    @Override
    public int setLayoutResID() {
        return R.layout.activity_guanggao_web;
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void initData() {
        URL=getIntent().getStringExtra("web");
        WebSettings settings = webView.getSettings();
        // 设置与Js交互的权限
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缩放级别
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 支持缩放
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);//开启DOM storage API功能
        settings.setAllowFileAccess(true);
        // 将网页内容以单列显示
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        final String url = Urls.Baseurl_index;
//        final String url = "file:///android_asset/text.html";

        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);//错误提示
//
//                Intent intent=new Intent(ZixunKefuWebActivity.this,KongActivity.class);
//                startActivity(intent);
//                finish();
//            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    mPrigressBer.setVisibility(View.GONE);//加载完网页进度条消失
//                    LogUtils.LOG("ceshi",webView.getUrl(),"网..址");
//                    if(webView.getUrl().contains("https://eoskoreanode.com/app/index/index.html")||
//                            webView.getUrl().contains("https://eoskoreanode.com/app/asset/index.html")||
//                            webView.getUrl().contains("https://eoskoreanode.com/app/user/index.html")){
//                        webView.clearHistory();
//                    }
                }
                else{
                    mPrigressBer.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mPrigressBer.setProgress(newProgress);//设置进度值
                }


            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titlename.setText(title+"");

            }

            //扩展浏览器上传文件
            //3.0++版本
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                openFileChooserImpl(uploadMsg);
//            }

            //3.0--版本
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooserImpl(uploadMsg);
//            }

//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                openFileChooserImpl(uploadMsg);
//            }


//            @Override
//            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//
//                LogUtils.LOG("ceshi","图片选择","tupian");
//                onenFileChooseImpleForAndroid(filePathCallback);
//                return true;
//
//            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Uri uri = Uri.parse(message);
//                if ( uri.getScheme().equals("ete")) {
//
//                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
//                    // 所以拦截url,下面JS开始调用Android需要的方法
//                    Log.i("ceshi",uri.getAuthority()+".....2");
//                    webView.stopLoading();
//                    if(uri.getAuthority().equals("scan")){
//                        erweima();
//                    }else {
//                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                        cm.setText(uri.getAuthority());
//                        ToastUtils.showToast(MainActivity.this,"复制到剪贴板成功");
//                    }
//                    result.cancel();
//                    return true;
//                }
                return true;
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        webView=findViewById(R.id.webview);
        mPrigressBer=findViewById(R.id.pb);
        titlename=findViewById(R.id.titlename);
    }
}
