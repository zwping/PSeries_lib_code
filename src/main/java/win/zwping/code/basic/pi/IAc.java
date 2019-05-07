package win.zwping.code.basic.pi;

import android.content.Intent;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.Nullable;

/**
 * <p>describe：this Ac of the public interface
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 16:33:38 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class IAc {

    public interface IBasicLifeCycle {
        // 判断是否为正常点击 <= 500
        boolean normalClick();

        // 安全更新ui onDestroy = false
        boolean isChangeUi();
    }

    public interface IBasicKeyBoard {
        // 触摸到非EditText的控件则隐藏键盘
        void setAutoHideKB();
    }

    public interface IBasic {

        /////////////// 需要抽象实现的 /////////////////

        /*** before of super.onCreate(savedInstanceState) ***/
        void initData(@Nullable Intent i);

        int bindLayout();

        void initView(@Nullable Bundle savedInstanceState);

        void doBusiness();

        /////////////// 需要抽象实现的 /////////////////

        // 解决沉浸式问题 https://github.com/gyf-dev/ImmersionBar
        ImmersionBar getImBar();

        void showToast(@Nullable Object o);
    }
}
