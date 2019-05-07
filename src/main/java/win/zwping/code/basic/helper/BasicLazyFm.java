package win.zwping.code.basic.helper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import win.zwping.code.basic.pi.IFm;

/**
 * <p>describe：懒加载Fragment
 * <p>    note：
 * <p>    note：isLazyLoad
 * <p>    note：onFragmentStartLazy / onFragmentStopLazy / onCreateViewLazy / onResumeLazy / onPauseLazy / onDestroyViewLazy
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:57:31 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicLazyFm extends BasicSafetyFm implements IFm.IBasicLazy {

    private boolean isInit = false;
    private Bundle savedInstanceState;

    @Override
    public boolean setIsLazy() {
        return true; // 默认懒加载
    }

    private FrameLayout layout;

    @Deprecated
    protected final void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        // 为什么不直接getUserVisibleHint();而是通过自己存isVisibleToUserState变量判断
        // 因为v4的25的版本 已经调用 setUserVisibleHint(true)，结果到这里getUserVisibleHint是false
        //  （ps:看了FragmentManager源码Fragment被重新创建有直接赋值isVisibleToUser不知道是不是那里和之前v4有改动的地方）
        // 所以我默认VISIBLE_STATE_NOTSET，之前没有调用setUserVisibleHint方法，就用系统的getUserVisibleHint，否则就用setUserVisibleHint后保存的值
        // 总之就是调用了setUserVisibleHint 就使用setUserVisibleHint的值
        boolean isVisibleToUser;
        if (isVisibleToUserState == VISIBLE_STATE_NOT_SET) {
            isVisibleToUser = getUserVisibleHint();
        } else {
            isVisibleToUser = isVisibleToUserState == VISIBLE_STATE_VISIBLE;
        }
        if (setIsLazy()) {
            if (isVisibleToUser && !isInit) {
                isInit = true;
                onCreateViewLazy(savedInstanceState);
            } else {
                LayoutInflater layoutInflater = inflater;
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(getContext());
                }
                layout = new FrameLayout(layoutInflater.getContext());
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                super.setContentView(layout);
            }
        } else {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
        }
    }

    private int isVisibleToUserState = VISIBLE_STATE_NOT_SET;
    //未设置值
    private static final int VISIBLE_STATE_NOT_SET = -1;
    //可见
    private static final int VISIBLE_STATE_VISIBLE = 1;
    //不可见
    private static final int VISIBLE_STATE_GONE = 0;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisibleToUserState = isVisibleToUser ? VISIBLE_STATE_VISIBLE : VISIBLE_STATE_GONE;
        if (isVisibleToUser && !isInit && getContentView() != null) {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            onResumeLazy();
        }
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    @Deprecated
    @Override
    public void onStart() {
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }

    @Deprecated
    @Override
    public void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    private boolean isStart = false;

    protected void onFragmentStartLazy() {

    }

    protected void onFragmentStopLazy() {

    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    protected void onResumeLazy() {

    }

    protected void onPauseLazy() {

    }

    protected void onDestroyViewLazy() {

    }

    @Override
    public void setContentView(int layoutResID) {
        if (setIsLazy() && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (setIsLazy() && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    @Deprecated
    public void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public void onDestroyView() {
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }
}
