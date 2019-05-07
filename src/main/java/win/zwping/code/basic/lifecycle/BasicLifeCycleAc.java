package win.zwping.code.basic.lifecycle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import win.zwping.code.basic.pi.IAc;

/**
 * <p>describe：Activity详细的生命周期
 * <p>    note：
 * <p>    note：isChangeUi / normalClick
 * <p>    note：整个basicAc的架构{@link IAc}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:28:15 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicLifeCycleAc extends AppCompatActivity implements IAc.IBasicLifeCycle {

    //<editor-fold desc="保险做法">

    private Boolean isChange = false; // 所有更新UI的方法最好依据@param isCanChangeUi二次封装

    private long lastClick = 0;

    @Override
    public boolean normalClick() {
        if (System.currentTimeMillis() - lastClick <= 500) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean isChangeUi() {
        return isChange;
    }


    @Override
    public void finish() {
        if (isChange) super.finish();
    }

    //</editor-fold>
    //<editor-fold desc="生命周期">

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isChange = true;
        // if (null != savedInstanceState) { // 系统恢复再恢复
        //     xx = (XX)savedInstanceState.getParcelable(TAG);
        // }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable(TAG,xx); // 系统回收保存数据
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged(); // 布局改动时
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isChange = true;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow(); // Activity 会与它的 Window 关联
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus); // UI变成可交互状态 -->可以增加一些Dialog / PopupWindow
    }


    @Override
    protected void onPause() {
        super.onPause(); // 不要在onPause中执行耗时操作，因为下一个Activity的创建必须要等到当前onPause执行完成才会创建
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy(); // 不要在onDestroy中执行资源释放，因为onDestroy执行时机不确定(有可能较晚)
        isChange = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //</editor-fold>
}
