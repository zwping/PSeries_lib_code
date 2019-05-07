package win.zwping.code.basic.helper;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import win.zwping.code.basic.lifecycle.BasicLifeCycleAc;
import win.zwping.code.basic.pi.IAc;
import win.zwping.code.utils.KeyboardUtil;

/**
 * <p>describe：对于软键盘处理的Ac
 * <p>    note：
 * <p>    note：mAutoHideKeyboard
 * <p>    note：整个basicAc的架构{@link IAc}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:57:16 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicKeyBoardAc extends BasicLifeCycleAc implements IAc.IBasicKeyBoard {

    /*** 触摸自动隐藏键盘 ***/
    private boolean mAutoHideKeyboard;

    @Override
    public void setAutoHideKB() {
        mAutoHideKeyboard = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtil.transferFocus(findViewById(android.R.id.content)); // 默认隐藏键盘焦点
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mAutoHideKeyboard && ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                KeyboardUtil.hideSoftInput(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAutoHideKeyboard) KeyboardUtil.hideSoftInput(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtil.fixSoftInputLeaks(this); // 解决软键盘内存泄露
    }

    /**
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * @param v     当前视图
     * @param event 手势事件
     * @return 是否是触摸了editText
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
