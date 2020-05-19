package win.zwping.code.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

/**
 * <p>describe：软键盘相关方法
 * <p>    note：
 * <p>    note：showSoftInput / hideSoftInput / transferFocus / isSoftInputVisible
 * <p>    note：toggleSoftInput / getDecorViewInvisibleHeight / getContentViewInvisibleHeight / registerSoftInputChangedListener
 * <p>    note：fixSoftInputLeaks / fixAndroidBug5497
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 17:13:56 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class KeyboardUtil implements IUtil.INativeUtil {

    private static int sDecorViewInvisibleHeightPre;
    private static ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private static OnSoftInputChangedListener onSoftInputChangedListener;
    private static int sContentViewInvisibleHeightPre5497;

    private KeyboardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /*
      避免输入法面板遮挡
      <p>在 manifest.xml 中 activity 中设置</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */
    //<editor-fold desc="显示 / 隐藏">

    /**
     * 动态显示软键盘
     *
     * @param activity activity
     */
    public static void showSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        showSoftInput(view);
    }

    /**
     * 动态显示软键盘
     *
     * @param view 视图
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm = (InputMethodManager) Util.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        transferFocus(view);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput();
                }
            }
        });
    }


    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        hideSoftInput(view);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param view 视图
     */
    public static void hideSoftInput(@Nullable final View view) {
        if(null == view) return;
        View parentView = (View) view.getParent();
        if (null != parentView) transferFocus(parentView);

        InputMethodManager imm = (InputMethodManager) Util.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN || resultCode == InputMethodManager.RESULT_SHOWN) {
                    toggleSoftInput();
                }
            }
        });
    }

    //</editor-fold>
    //<editor-fold desc="转移焦点 / 键盘切换">

    /**
     * 转移焦点
     *
     * @param view
     */
    public static void transferFocus(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
    }

    /**
     * 切换键盘输入
     */
    public static void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) Util.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    //</editor-fold>
    //<editor-fold desc="键盘是否可见 / 键盘监听">

    /**
     * 键盘是否可见
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return getDecorViewInvisibleHeight(activity) > 0;
    }

    /**
     * 键盘是否可见
     *
     * @param activity             The activity.
     * @param minHeightOfSoftInput The minimum height of soft input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(final Activity activity, final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    private static int sDecorViewDelta = 0;

    private static int getDecorViewInvisibleHeight(final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        if (decorView == null) return sDecorViewInvisibleHeightPre;
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= ScreenUtil.getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        if (contentView == null) return sContentViewInvisibleHeightPre5497;
        final Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        if (delta <= ScreenUtil.getStatusBarHeight() + ScreenUtil.getNavBarHeight()) {
            return 0;
        }
        return delta;
    }

    /**
     * 注册监听键盘改变监听
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    public static void registerSoftInputChangedListener(final Activity activity, final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(activity);
        onSoftInputChangedListener = listener;
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onSoftInputChangedListener != null) {
                    int height = getDecorViewInvisibleHeight(activity);
                    if (sDecorViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener.onSoftInputChanged(height);
                        sDecorViewInvisibleHeightPre = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * Unregister soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void unregisterSoftInputChangedListener(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftInputChangedListener = null;
        onGlobalLayoutListener = null;
    }

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int height);
    }

    //</editor-fold>
    //<editor-fold desc="解决软键盘内存泄露">

    /**
     * Fix the leaks of soft input.
     * <p>Call the function in {@link Activity#onDestroy()}.</p>
     *
     * @param context The context.
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) return;
        InputMethodManager imm = (InputMethodManager) Util.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) continue;
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }


    /**
     * Fix the bug of 5497 in Android.
     * <p>Don't set adjustResize</p>
     * <p>
     * 全屏模式 + adjustPan + webView = 5497  √ （onCreate中注入）
     *
     * @param activity The activity.
     */
    public static void fixAndroidBug5497(final Activity activity) {
//        Window window = activity.getWindow();
//        int softInputMode = window.getAttributes().softInputMode;
//        window.setSoftInputMode(softInputMode & ~WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        sContentViewInvisibleHeightPre5497 = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getContentViewInvisibleHeight(activity);
                        if (sContentViewInvisibleHeightPre5497 != height) {
                            contentViewChild.setPadding(
                                    contentViewChild.getPaddingLeft(),
                                    contentViewChild.getPaddingTop(),
                                    contentViewChild.getPaddingRight(),
                                    paddingBottom + getDecorViewInvisibleHeight(activity)
                            );
                            sContentViewInvisibleHeightPre5497 = height;
                        }
                    }
                });
    }

    //</editor-fold>
}
