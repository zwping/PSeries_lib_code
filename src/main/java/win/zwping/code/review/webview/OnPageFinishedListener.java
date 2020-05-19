package win.zwping.code.review.webview;

import android.webkit.WebView;

/**
 * <p>describe：狂舔kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:22:18 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface OnPageFinishedListener {
    /**
     * 网页加载结束
     *
     * @param view
     * @param url
     */
    void onPageFinished(WebView view, String url);
}
