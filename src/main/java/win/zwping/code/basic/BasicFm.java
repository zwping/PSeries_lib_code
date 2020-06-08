package win.zwping.code.basic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;

import win.zwping.code.basic.helper.BasicXLazyFm;
import win.zwping.code.basic.pi.IFm;
import win.zwping.code.comm.CommCallback;
import win.zwping.code.utils.HandlerUtil;
import win.zwping.code.utils.ToastUtil;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:56:32 mail：1101558280@qq.com web: https://www.zwping.com </p>
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

    @Override
    public void setSafeClickLis(@Nullable View view, View.OnClickListener onClickListener) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            if (normalClick()) onClickListener.onClick(v);
        });
    }

    @Nullable
    @Override
    public <V extends View> V findViewById(int id) {
        if (mContentView != null) return mContentView.findViewById(id);
        return null;
    }

    private ImmersionBar imBar;

    @Override
    public ImmersionBar getImBar() {
        return imBar == null ? imBar = ImmersionBar.with(this).statusBarDarkFont(true) : imBar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // if (null != imBar) imBar.destroy(this);
    }

    @Override
    public void showToast(@Nullable final Object o) {
        ToastUtil.showShort(o + "");
    }

    @Override
    public void runOnUiThreadDelay(int delay, CommCallback<BasicFm> callback) {
        HandlerUtil.runOnUiThreadDelay(() -> {
            if (isChangeUi()) callback.callback(this);
        }, delay);
    }

    @Override
    public void runOnUiThreadDelay(CommCallback<BasicFm> callback) {
        runOnUiThreadDelay(200, callback);
    }
}
