package com.YiDian.RainBow.agreement;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YonghuActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.webView)
    WebView webView;
    String str = "http://81.71.121.177/protocol.html";
    @Override
    protected int getResId() {
        return R.layout.activity_yonghu;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(YonghuActivity.this,toolbar);
        StatusBarUtil.setDarkMode(YonghuActivity.this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 通过此WebView 获取到 WebSettings ，通过WebSettings设置WebView
         */
        WebSettings webSettings = webView.getSettings();

        /**
         * 设置支持JavaScript激活，可用等
         */
        webSettings.setJavaScriptEnabled(true);

        /**
         * 设置自身浏览器，注意：可用把WebView理解为浏览器，设置new WebViewClient()后，手机就不会跳转其他的浏览器
         */
        webView.setWebViewClient(new WebViewClient());

        /**
         * addJavascriptInterface是添加(给js调用-->Java方法)
         * JSHook里面的方法 就是给JavaScript调用的;
         * androidCallbackAction是JavaScript/HTML/H5那边定义定义的标识，所以必须和JavaScript/HTML/H5那边定义标识一致
         */

        webView.loadUrl(str);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
