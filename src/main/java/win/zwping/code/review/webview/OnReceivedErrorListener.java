package win.zwping.code.review.webview;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * <p>describe：支持kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:22:49 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnReceivedErrorListener {

    /**
     * 加载页出错时响应（eg：404/500...）
     *
     * @param view
     * @param request
     * @param error
     */
    void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
}
