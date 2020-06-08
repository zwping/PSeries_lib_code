package win.zwping.code.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;


import com.gyf.immersionbar.ImmersionBar;

import win.zwping.code.basic.helper.BasicKeyBoardAc;
import win.zwping.code.basic.pi.IAc;
import win.zwping.code.comm.CommCallback;
import win.zwping.code.utils.HandlerUtil;
import win.zwping.code.utils.ToastUtil;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：整个basicAc的架构{@link IAc}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:10:35 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public abstract class BasicAc extends BasicKeyBoardAc implements IAc.IBasic {


    @Override
    public void initData(@Nullable Intent intent) {

    }

    @Override
    public void setContentView() {
        if (0 != bindLayout()) setContentView(bindLayout());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initData(getIntent()); // before of super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState);
        setContentView();
        initView(savedInstanceState);
        getWindow().getDecorView().findViewById(android.R.id.content).post(this::doBusiness);
    }


    @Override
    public void setSafeClickLis(@Nullable View view, View.OnClickListener onClickListener) {
        if (view == null) return;
        view.setOnClickListener(v -> {
            if (normalClick()) onClickListener.onClick(v);
        });
    }

    private ImmersionBar imBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if (null != imBar) imBar.destroy(this,);
    }

    ////////////////////////////////////////

    @Override
    public ImmersionBar getImBar() {
        return imBar == null ? imBar = ImmersionBar.with(this).statusBarDarkFont(true) : imBar;
    }

    @Override
    public void showToast(@Nullable Object o) {
        ToastUtil.showShort(o + "");
    }

    @Override
    public void runOnUiThreadDelay(int delay, CommCallback<BasicAc> callback) {
        HandlerUtil.runOnUiThreadDelay(() -> {
            if (isChangeUi()) callback.callback(this);
        }, delay);
    }

    @Override
    public void runOnUiThreadDelay(CommCallback<BasicAc> callback) {
        runOnUiThreadDelay(200, callback);
    }
}
