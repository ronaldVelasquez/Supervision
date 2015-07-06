package com.inei.supervision.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.inei.supervision.R;
import com.inei.supervision.utils.ConstantsUtil;

import org.apache.http.util.EncodingUtils;

public class SupervisionActivity extends Activity {
    private WebView webViewSupervision;
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervision);

        Intent intent = getIntent();
        String user = intent.getStringExtra(USER);
        String password = intent.getStringExtra(PASSWORD);

        webViewSupervision = (WebView) findViewById(R.id.webview_supervision);
        String postData = "identity=" + user + "&password=" + password;
        webViewSupervision.postUrl("http://192.95.3.149:90/supervision/index.php/auth/login", EncodingUtils.getBytes(postData, "BASE64"));
        webViewSupervision.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
        webViewSupervision.setWebChromeClient(new WebChromeClient());
        webViewSupervision.getSettings().setJavaScriptEnabled(true);
        webViewSupervision.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webViewSupervision.restoreState(savedInstanceState);
    }
}
