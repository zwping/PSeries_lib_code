package win.zwping.code.review.webview;

import android.webkit.WebView;

/**
 * <p>describe：支持kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:23:02 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnReceivedTitleListener {
    /**
     * 获取网页标题
     *
     * @param view
     * @param title
     */
    void onReceivedTitle(WebView view, String title);
}
