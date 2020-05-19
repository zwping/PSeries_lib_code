package win.zwping.code.review.webview;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * <p>describe：支持kt / lambda
 * <p>    note：
 * <p> @author：zwp on 2019-01-04 10:20:37 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface OnPageStartedListener {

    /**
     * 开始加载网页
     *
     * @param view
     * @param url
     * @param favicon
     */
    void onPageStarted(WebView view, String url, Bitmap favicon);

}
