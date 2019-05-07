package win.zwping.code.review.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-01-05 13:34:40 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnUrlLoadingListener {

    /*** 当前webView加载的url ***/
    Boolean shouldOverrideUrlLoading(WebViewClient client, WebView view, String url);

    // return client.shouldOverrideUrlLoading(view, url);
}
