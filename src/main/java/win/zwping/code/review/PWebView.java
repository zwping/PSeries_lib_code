package win.zwping.code.review;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import win.zwping.code.R;
import win.zwping.code.cview.OverScrollLayout;
import win.zwping.code.cview.SwitchPageStateLayout;
import win.zwping.code.review.webview.BasicWebView;
import win.zwping.code.review.webview.OnPageFinishedListener;
import win.zwping.code.review.webview.OnPageStartedListener;
import win.zwping.code.review.webview.OnProgressChangedListener;
import win.zwping.code.review.webview.OnReceivedErrorListener;
import win.zwping.code.review.webview.OnReceivedSslErrorListener;
import win.zwping.code.review.webview.OnReceivedTitleListener;
import win.zwping.code.review.webview.OnUrlLoadingListener;

import static win.zwping.code.utils.CollectionUtil.split;
import static win.zwping.code.utils.EmptyUtil.isEmpty;
import static win.zwping.code.utils.EmptyUtil.isNotEmpty;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：https://juejin.im/post/58a037df86b599006b3fade4
 * <p> @author：zwp on 2019-01-04 16:28:14 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PWebView extends FrameLayout {

    private Builder builder;

    private OverScrollLayout overSv;
    private TextView fromTitleTv, previewTv;
    private FrameLayout containerFl;
    private SwitchPageStateLayout switchWebViewSps;
    private ProgressBar progressPb;

    // 默认配置 进度条 来源Title loading/error界面 loading界面 兼容嵌套滑动 嵌套协调器布局滑动
    private Boolean defaultConfig, showPb, showFromTitle, enableOverSv, enableLoadingView, enableErrorView, nestedScrollView, nestedCoordinatorLayoutEnable;

    public PWebView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public PWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.cus_web_view, this);

        overSv = findViewById(R.id.over_sv);
        fromTitleTv = findViewById(R.id.web_view_from_title_tv);
        containerFl = findViewById(R.id.web_view_container_fl);
        switchWebViewSps = findViewById(R.id.web_view_sps);
        progressPb = findViewById(R.id.progress_pb);
        previewTv = findViewById(R.id.web_view_preview_tv);

        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PWebView);
            try {
                defaultConfig = array.getBoolean(R.styleable.PWebView_p_setDefaultConfig, true);
                showPb = array.getBoolean(R.styleable.PWebView_p_setProgressBar, true);
                progressPb.setVisibility(showPb ? VISIBLE : GONE);  // 默认显示加载进度条

                overSv.setOverScroll(array.getBoolean(R.styleable.PWebView_p_enableOverSv, false)); // 只允许在xml中配置，因其影响网页渲染

                showFromTitle = array.getBoolean(R.styleable.PWebView_p_fromTitle, false);
                fromTitleTv.setVisibility(showFromTitle ? VISIBLE : GONE); // 默认不显示来源Title

                nestedScrollView = array.getBoolean(R.styleable.PWebView_p_nestedScrollView, false);
                nestedCoordinatorLayoutEnable = array.getBoolean(R.styleable.PWebView_p_nested_coordinator_layout, false);

                switchWebViewSps.setLoadingResId(array.getResourceId(R.styleable.PWebView_p_loadingView, R.layout.child_web_view_loading));
                switchWebViewSps.setErrorResId(array.getResourceId(R.styleable.PWebView_p_errorView, R.layout.child_web_view_error));
                enableLoadingView = array.getBoolean(R.styleable.PWebView_p_enableLoadingView, false);
                enableErrorView = array.getBoolean(R.styleable.PWebView_p_enableErrorView, true);
                if (enableLoadingView) switchWebViewSps.showLoading();
            } finally {
                array.recycle();
            }
        }
        post(() -> previewTv.setVisibility(GONE));
    }

    public SwitchPageStateLayout getSwitchPageStatesView() {
        return switchWebViewSps;
    }

    public PWebView setProgressBar(Boolean show) {
        progressPb.setVisibility(show ? VISIBLE : GONE);
        return this;
    }

    /*** 防止字体重新设置 ***/
    public PWebView setFixedFont() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dm.scaledDensity = dm.density;
        return this;
    }
    /////////////////// 对WebView进行操作，建议通过init进入Builder进行操作 /////////////////////

    public Builder builder() {
        if (null == builder) {
            builder = new Builder(new BasicWebView(getContext(), nestedScrollView, nestedCoordinatorLayoutEnable), this);
            containerFl.addView(builder.webView);
        }
        return builder;
    }

//    public BasisWebView getWebView() {
//        return null == builder ? null : builder.webView;
//    }

    ///////////////// 状态可直接调用 //////////////////

    public void resume() {
        if (null != builder) builder.webView.resume();
    }

    public void stop() {
        if (null != builder) builder.webView.stop();
    }

    public void destroy() {
        if (null != builder) builder.webView.removeWebView();
    }

    public boolean onKeyDownGoBack(int keyCode) {
        /*
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                   return webView.onKeyDownGoBack(keyCode) || super.onKeyDown(keyCode, event);
            }
         */
        if (null != builder) return builder.webView.onKeyDownGoBack(keyCode);
        else return false;
    }

    /*** 清除缓存 {@link BasicWebView#clear(boolean, boolean, boolean)} ***/
    public void clearCache(boolean clearHistory, boolean clearFormData, boolean clearCache) {
        if (null != builder) builder.webView.clear(clearHistory, clearFormData, clearCache);
    }

    /////////////////// 引导用户必须调用init() //////////////////////////

    public static class Builder {

        private PWebView pWebView;
        public BasicWebView webView;

        private OnPageStartedListener onPageStartedListener;
        private OnPageFinishedListener onPageFinishedListener;
        private OnReceivedSslErrorListener onReceivedSslErrorListener;
        private OnReceivedErrorListener onReceivedErrorListener;
        private OnUrlLoadingListener onUrlLoadingListener;

        private OnReceivedTitleListener onReceivedTitleListener;
        private OnProgressChangedListener onProgressChangedListener;

        Builder(BasicWebView webView, PWebView pWebView) {
            this.webView = webView;
            this.pWebView = pWebView;
            init();
        }

        private void init() {
            if (pWebView.defaultConfig) webView.setDefaultWebSetting();  // 默认加载默认配置
            addListener();
        }

        /////////////////////////////////

        public Builder loadUrl(String url) {
            webView.loadUrl(url);
            return this;
        }

        public Builder loadUrl(String url, Boolean nonJudgeHttp) {
            webView.loadUrl(url, nonJudgeHttp);
            return this;
        }

        @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
        public Builder addJavascriptInterface(Object object, String name) {
            webView.addJavascriptInterface(object, name);
            return this;
        }

        public Builder setShowFromTitle(Boolean show) {
            pWebView.fromTitleTv.setVisibility(show ? VISIBLE : GONE);
            return this;
        }

        public Builder setEnableErrorView(Boolean enable) {
            pWebView.enableErrorView = enable;
            return this;
        }

        public Builder setErrorViewResId(@LayoutRes int resId) {
            pWebView.switchWebViewSps.setErrorResId(resId);
            return this;
        }

        ///////////////////////////////////////

        public Builder setOnPageStartedListener(OnPageStartedListener listener) {
            this.onPageStartedListener = listener;
            return this;
        }

        public Builder setOnPageFinishedListener(OnPageFinishedListener listener) {
            this.onPageFinishedListener = listener;
            return this;
        }

        public Builder setOnReceivedSslErrorListener(OnReceivedSslErrorListener listener) {
            this.onReceivedSslErrorListener = listener;
            return this;
        }

        public Builder setOnReceivedErrorListener(OnReceivedErrorListener listener) {
            this.onReceivedErrorListener = listener;
            return this;
        }

        public Builder setOnReceivedTitleListener(OnReceivedTitleListener listener) {
            this.onReceivedTitleListener = listener;
            return this;
        }

        public Builder setOnProgressChangedListener(OnProgressChangedListener listener) {
            this.onProgressChangedListener = listener;
            return this;
        }

        public Builder setOnUrlLoadingListener(OnUrlLoadingListener listener) {
            this.onUrlLoadingListener = listener;
            return this;
        }

        ////////////////////////////////////
        private void addListener() {
            webView.setWebViewClient(new WebViewClient() {
                //开始加载网页
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    webView.setTag(null);
                    if (null != onPageStartedListener)
                        onPageStartedListener.onPageStarted(view, url, favicon);
                }

                //结束加载网页
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (pWebView.fromTitleTv.getVisibility() == VISIBLE && isNotEmpty(url)) {
                        pWebView.fromTitleTv.setText(String.format("网页由 %s 提供", split(split(url, "//")[1], "/")[0]));
                    }
                    if (null != onPageFinishedListener)
                        onPageFinishedListener.onPageFinished(view, url);
                    if (isEmpty(webView.getTag()))
                        pWebView.switchWebViewSps.showContent();
                }

                //处理https请求
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    if (null != onReceivedSslErrorListener)
                        onReceivedSslErrorListener.onReceivedSslError(view, handler, error);
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    webView.setTag("WebView");
                    if (null != onReceivedErrorListener)
                        onReceivedErrorListener.onReceivedError(view, request, error);
                    TextView tv = pWebView.switchWebViewSps.getErrorView().findViewById(R.id.error_tv);
                    if (isNotEmpty(tv) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        tv.setText(String.format("网页加载失败(%s)", error.getErrorCode()));
                    if (pWebView.enableErrorView) pWebView.switchWebViewSps.showError();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (null != onUrlLoadingListener)
                        return onUrlLoadingListener.shouldOverrideUrlLoading(this, view, url);
                    else return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                //获取网站标题
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    if (null != onReceivedTitleListener)
                        onReceivedTitleListener.onReceivedTitle(view, title);
                }

                //获取加载进度
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (null != onProgressChangedListener)
                        onProgressChangedListener.onProgressChanged(view, newProgress);
                    if (pWebView.showPb) {
                        if (100 == newProgress) pWebView.progressPb.setVisibility(GONE);
                        else {
                            if (pWebView.progressPb.getVisibility() == GONE)
                                pWebView.progressPb.setVisibility(VISIBLE);
                            pWebView.progressPb.setProgress(newProgress);
                        }
                    }
                }
            });
        }

    }
}
