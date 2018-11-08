package com.haodong.veerdemo.activities;

import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.haodong.veerdemo.R;

/**
 * @author linghailong
 * @date on 2018/11/7
 * @email 105354999@qq.com
 * @describe :
 */
public class PageActivity extends BaseActivity {
    WebView mWebview;
    WebSettings mWebSettings;
    TextView mTitle;
    String mUrl;
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_page;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @Override
    protected void initWidget() {
        mWebview = findViewById(R.id.webView1);
        mWebSettings = mWebview.getSettings();
        mTitle = findViewById(R.id.title);
        mUrl=getIntent().getStringExtra("thumb");
        mWebview.loadUrl(mUrl);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTitle.setText(title);
            }
        });
    }
}
