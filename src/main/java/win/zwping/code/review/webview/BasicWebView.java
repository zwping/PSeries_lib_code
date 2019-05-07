package win.zwping.code.review.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.utils.LogUtil;

import static android.view.KeyEvent.KEYCODE_BACK;
import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：简易的封装google原生webView
 * <p>    note：
 * <p>    note：4.4之前使用webKit内核、后使用chromium内核
 * <p>    note：了解该类重写的方法，后使用其它第三方webView也大同小异。eg：https://x5.tencent.com/tbs/
 * <p>    note：
 * <p> @author：zwp on 2017/11/14 0014 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <p>
 **/
public class BasicWebView extends WebView {

    //<editor-fold desc="内部参数">

    private WebSettings webSettings;

    /*** 嵌套滑动 ***/
    private boolean nestedScrollView;

    private OnPageStartedListener onPageStartedListener;
    private OnPageFinishedListener onPageFinishedListener;
    private OnReceivedSslErrorListener onReceivedSslErrorListener;
    private OnReceivedErrorListener onReceivedErrorListener;
    private OnUrlLoadingListener onUrlLoadingListener;

    private OnReceivedTitleListener onReceivedTitleListener;
    private OnProgressChangedListener onProgressChangedListener;
    //</editor-fold>
    //<editor-fold desc="构造函数">


    public BasicWebView(Context context, @NonNull Boolean nestedScrollView) {
        super(context);
        initView(null);
        this.nestedScrollView = nestedScrollView;
    }

    public BasicWebView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }
    //</editor-fold>
    //<editor-fold desc="功能变现">

    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BasicWebView);
            try {
                nestedScrollView = array.getBoolean(R.styleable.BasicWebView_p_nestedScrollView, false);
                if (array.getBoolean(R.styleable.BasicWebView_p_setDefaultConfig, false))
                    setDefaultWebSetting();
            } finally {
                array.recycle();
            }
        }
        setListener();
    }

    /*** 启用监听 ***/
    private void setListener() {
        setWebViewClient(new WebViewClient() {
            //开始加载网页
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (null != onPageStartedListener)
                    onPageStartedListener.onPageStarted(view, url, favicon);
            }

            //结束加载网页
            @Override
            public void onPageFinished(WebView view, String url) {
                if (null != onPageFinishedListener)
                    onPageFinishedListener.onPageFinished(view, url);
            }

            //处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (null != onReceivedSslErrorListener)
                    onReceivedSslErrorListener.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (null != onReceivedErrorListener)
                    onReceivedErrorListener.onReceivedError(view, request, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (null != onUrlLoadingListener)
                    return onUrlLoadingListener.shouldOverrideUrlLoading(this, view, url);
                else return super.shouldOverrideUrlLoading(view, url);
            }

        });

        setWebChromeClient(new WebChromeClient() {
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
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec;
        if (nestedScrollView)
            mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        else mExpandSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }

    @Override
    public void loadUrl(String url) {
        loadUrl(isEmpty(url) ? "" : url.startsWith("http") ? url : "http://" + url, true);
    }

    public void loadUrl(String url, Boolean nonJudgeHttp) {
        LogUtil.i("basisWebView：" + url);
        super.loadUrl(url);
    }

    //</editor-fold>
    //<editor-fold desc="API">

    /**
     * 加载默认webSetting
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void setDefaultWebSetting() {
        webSettings = this.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作x
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webView中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //防止root窃取/databases/webview.db中的密码
        webSettings.setSavePassword(false);

        //5.1以上默认禁止了https和http混用 开启混用模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
        //需要注意，为防止WebView远程代码执行经典Bug，应当在对应的Js调用Java的方法上添加@JavascriptInterface https://www.jianshu.com/p/93cea79a2443
        webSettings.setJavaScriptEnabled(true);

        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
    }

    /**
     * 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
     * <br />解释在{@link #setDefaultWebSetting()}中有
     */
    public void resume() {
        if (null != webSettings) webSettings.setJavaScriptEnabled(true);
    }

    /**
     * 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
     * <br />解释在{@link #setDefaultWebSetting()}中有
     */
    public void stop() {
        if (null != webSettings) webSettings.setJavaScriptEnabled(false);
    }

    /*** 兼容嵌套滑动 ***/
    public BasicWebView setNestedScrollView(Boolean nestedScrollView) {
        this.nestedScrollView = nestedScrollView;
        return this;
    }

    public BasicWebView setOnPageStartedListener(OnPageStartedListener listener) {
        this.onPageStartedListener = listener;
        return this;
    }

    public BasicWebView setOnPageFinishedListener(OnPageFinishedListener listener) {
        this.onPageFinishedListener = listener;
        return this;
    }

    public BasicWebView setOnReceivedSslErrorListener(OnReceivedSslErrorListener listener) {
        this.onReceivedSslErrorListener = listener;
        return this;
    }

    public BasicWebView setOnReceivedErrorListener(OnReceivedErrorListener listener) {
        this.onReceivedErrorListener = listener;
        return this;
    }

    public BasicWebView setOnReceivedTitleListener(OnReceivedTitleListener listener) {
        this.onReceivedTitleListener = listener;
        return this;
    }

    public BasicWebView setOnProgressChangedListener(OnProgressChangedListener listener) {
        this.onProgressChangedListener = listener;
        return this;
    }

    public BasicWebView setOnUrlLoadingListener(OnUrlLoadingListener listener) {
        this.onUrlLoadingListener = listener;
        return this;
    }


    /**
     * 消费activity back事件
     *
     * @param keyCode
     * @return 是否消费
     */
    public boolean onKeyDownGoBack(int keyCode) {
        /*
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                   return webView.onKeyDownGoBack(keyCode) || super.onKeyDown(keyCode, event);
            }
         */
        if ((keyCode == KEYCODE_BACK) && this.canGoBack()) {
            this.goBack();
            return true;
        } else {
            return false;
        }
    }

    /**
     * //销毁WebView
     * //在关闭了Activity时，如果WebView的音乐或视频，还在播放。就必须销毁WebView
     * //但是注意：webView调用desTory时,webView仍绑定在Activity上
     * //这是由于自定义webView构建时传入了该Activity的context对象
     * //因此需要先从父容器中移除webView,然后再销毁webView:
     */
    public void removeWebView() {
        //加载null data
        this.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        //this.clearHistory();

        ((ViewGroup) this.getParent()).removeView(this);
        this.destroy();
    }

    /**
     * @param clearHistory  //清除当前webView访问的历史记录 //只会清除webView访问历史记录里的所有记录除了当前访问记录
     * @param clearFormData //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
     * @param clearCache    //清除网页访问留下的缓存          //由于内核缓存是全局的因此这个方法不仅仅针对webView而是针对整个应用程序.
     */
    public void clear(boolean clearHistory, boolean clearFormData, boolean clearCache) {
        if (clearCache) {
            this.clearCache(true);
            return;
        }
        if (clearHistory) this.clearHistory();
        if (clearFormData) this.clearFormData();
    }
    //</editor-fold>
}

