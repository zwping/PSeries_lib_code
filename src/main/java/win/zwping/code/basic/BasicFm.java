package win.zwping.code.basic;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.gyf.barlibrary.ImmersionBar;
import win.zwping.code.basic.helper.BasicLazyFm;
import win.zwping.code.basic.pi.IFm;
import win.zwping.code.utils.ToastUtil;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:56:32 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public abstract class BasicFm extends BasicLazyFm implements IFm.IBasic {

    protected View mContextView;

    @Override
    protected void onCreateViewLazy(final Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(mContextView = inflater.inflate(bindLayout(), null));
        if (null != mContextView)
            mContextView.post(new Runnable() {
                @Override
                public void run() {
                    initView(savedInstanceState);
                    doBusiness();
                }
            });
    }


    private ImmersionBar imBar;

    @Override
    public ImmersionBar getImBar() {
        return imBar == null ? imBar = ImmersionBar.with(this).statusBarDarkFont(true) : imBar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != imBar) imBar.destroy();
    }

    @Override
    public void showToast(@Nullable final Object o) {
        ToastUtil.showShort(o + "");
    }
}
