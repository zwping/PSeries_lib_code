package win.zwping.code.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

import win.zwping.code.basic.helper.BasicXLazyFm;
import win.zwping.code.basic.pi.IFm;
import win.zwping.code.utils.ToastUtil;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:56:32 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public abstract class BasicFm extends BasicXLazyFm implements IFm.IBasic {

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public View setContentView(@NonNull LayoutInflater inflater) {
        return inflater.inflate(bindLayout(), null);
    }

    @Override
    public void onCreateViewLazy(@Nullable Bundle savedInstanceState) {
        mContentView.post(() -> {
            initView(savedInstanceState);
            doBusiness();
        });
    }

//    @Override
//    protected void onCreateViewLazy(final Bundle savedInstanceState) {
//        super.onCreateViewLazy(savedInstanceState);
//        if (bindLayout() != 0)
//            setContentView(mContextView = inflater.inflate(bindLayout(), null));
//        if (null != mContextView)
//            mContextView.post(() -> {
//                initView(savedInstanceState);
//                doBusiness();
//            });
//    }


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
