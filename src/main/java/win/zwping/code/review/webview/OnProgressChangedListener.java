package win.zwping.code.review.webview;

import android.webkit.WebView;

/**
 * <p>describe：支持kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:23:16 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnProgressChangedListener {

    /**
     * 加载进度
     *
     * @param view
     * @param newProgress
     */
    void onProgressChanged(WebView view, int newProgress);
}
