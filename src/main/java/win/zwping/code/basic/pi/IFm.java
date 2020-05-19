package win.zwping.code.basic.pi;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import win.zwping.code.basic.BasicFm;
import win.zwping.code.comm.CommCallback;

/**
 * <p>describe：this Fm of the public interface
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 16:53:09 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class IFm {

    public interface IBasicLifeCycle {
        // 判断是否为正常点击 <= 500
        boolean normalClick();

        // 安全更新ui onDestroy = false
        boolean isChangeUi();
    }

    public interface IBasicSafety {
        // 安全的获取Activity
        Activity getAc();

        void setContentView(int layoutResID);

        void setContentView(View view);

        View getContentView();

        @Nullable
        <V extends View> V findViewById(int id);
    }

    public interface IBasicLazy {
        // 是否支持拉加载
        boolean setIsLazy();

        void initData(Bundle bundle);

        View setContentView(@NonNull LayoutInflater inflater);

        void onCreateViewLazy(@Nullable Bundle savedInstanceState);
    }

    public interface IBasic {

        /////////////// 需要抽象实现的 /////////////////

        int bindLayout();

        void initView(@Nullable Bundle savedInstanceState);

        void doBusiness();

        /////////////// 需要抽象实现的 /////////////////

        @Nullable
        <V extends View> V findViewById(int id);

        void setSafeClickLis(@Nullable View view, View.OnClickListener onClickListener);

        // 解决沉浸式问题 https://github.com/gyf-dev/ImmersionBar
        ImmersionBar getImBar();

        void showToast(@Nullable Object o);

        void runOnUiThreadDelay(int delay, CommCallback<BasicFm> callback);

        void runOnUiThreadDelay(CommCallback<BasicFm> callback);
    }

}
