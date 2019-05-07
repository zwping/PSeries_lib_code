package win.zwping.code.review.webview;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

/**
 * <p>describe：支持kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:22:35 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnReceivedSslErrorListener {
    /**
     * 处理https请求
     *
     * @param view
     * @param handler
     * @param error
     */
    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);
}
