package win.zwping.code.basic;

import android.os.Bundle;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.Nullable;
import win.zwping.code.basic.helper.BasicKeyBoardAc;
import win.zwping.code.basic.pi.IAc;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：整个basicAc的架构{@link IAc}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:10:35 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public abstract class BasicAc extends BasicKeyBoardAc implements IAc.IBasic {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initData(getIntent()); // before of super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());
        initView(savedInstanceState);
        doBusiness();
    }

    private ImmersionBar imBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != imBar) imBar.destroy();
    }

    ////////////////////////////////////////

    @Override
    public ImmersionBar getImBar() {
        return imBar == null ? imBar = ImmersionBar.with(this).statusBarDarkFont(true) : imBar;
    }

    @Override
    public void showToast(@Nullable Object o) {
        SmartToast.show(o + "");
    }

}
