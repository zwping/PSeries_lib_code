package win.zwping.code.cview.pi;

import android.view.View;

import androidx.annotation.LayoutRes;
import win.zwping.code.cview.SwitchPageStateLayout;

/**
 * <p>describe：this SwitchPageState of the public interface
 * <p>    note：
 * <p> @author：zwp on 2019-03-15 11:33:25 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface ISwitchPageLayout {

    SwitchPageStateLayout setLoadingResId(@LayoutRes int loadingResId);

    SwitchPageStateLayout setEmptyResId(@LayoutRes int loadingResId);

    SwitchPageStateLayout setNetErrorResId(@LayoutRes int loadingResId);

    SwitchPageStateLayout setErrorResId(@LayoutRes int loadingResId);

    SwitchPageStateLayout setCusResId(@LayoutRes int loadingResId);

    SwitchPageStateLayout showContent();

    SwitchPageStateLayout showLoading();

    SwitchPageStateLayout showEmpty();

    SwitchPageStateLayout showNetErrorWork();

    SwitchPageStateLayout showError();

    SwitchPageStateLayout showErrorOfSmart();

    SwitchPageStateLayout showCusView();

    View getContentView();

    View getLoadingView();

    View getEmptyView();

    View getNetErrorView();

    View getErrorView();

    View getCusView();

    SwitchPageStateLayout setOnRetryClickListener(View.OnClickListener onRetryClickListener);

    SwitchPageStateLayout setOnRetryClickListener(View view);

    SwitchPageStateLayout setOnRetryClickListener(View view, View.OnClickListener onRetryClickListener);
}
