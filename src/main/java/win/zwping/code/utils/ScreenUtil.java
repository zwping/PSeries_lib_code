package win.zwping.code.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

import java.lang.reflect.Method;

import static android.Manifest.permission.EXPAND_STATUS_BAR;
import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * <p>describe：屏幕 工具类
 * <p>    note：
 * <p>    note：isLandscape / isPortrait / getScreenDensity / getScreenDensityDPI / printDisplayInfo
 * <p>    note：setFullScreen / getLandscape / toggleFullScreen / isFullScreen / setPortrait / isLandscape / isPortrait / getScreenRotation
 * <p>    note：screenShot / isScreenLock / isTablet / setSleepDuration / getSleepDuration
 * <p>    note：getScreenHeight / getScreenWidth /
 * <p>    note：getStatusBarHeight / setStatusBarVisibility / isStatusBarVisible
 * <p>    note：getNavBarHeight / setNavBarVisibility /  setNavBarImmersive / setNavBarColor / getNavBarColor / isNavBarVisible / isSupportNavBar
 * <p>    note：getActionBarHeight
 * <p>    note：getNotificationBarVisibility
 * <p>    note：
 * <p> @author：zwp on 2018/3/13 0013 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class ScreenUtil implements IUtil.INativeUtil {

    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";
    private static final String TAG_OFFSET = "TAG_OFFSET";
    private static final int KEY_OFFSET = -123;

    public ScreenUtil() {
        throw new UnsupportedOperationException("u can't instantiate me..."); //不支持操作的异常
    }

    //<editor-fold desc="横竖屏判断 / 密度 / DPI / 屏幕信息">

    /**
     * 判断当前屏幕是否横屏
     *
     * @return boolean
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断当前屏幕是否竖屏
     *
     * @return boolean
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Get screen density（密度）, the logical density of the display
     * <P> note:density值表示每英寸有多少个显示点，与分辨率是两个概念。
     *
     * @return density
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Get screen density dpi（像素密度）, the screen density expressed as dots-per-inch
     * <P> note:屏幕像素密度 一般计算公式为，由屏幕长宽的分辨率，根据勾股定律求出对角线的像素数，再除以屏幕的尺寸，即可得densityDpi.
     *
     * @return densityDpi
     */
    public static int getScreenDensityDPI() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * 打印屏幕显示信息
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append("_______  显示信息:  ");
        sb.append("\ndensity         :").append(dm.density); //密度
        sb.append("\ndensityDpi      :").append(dm.densityDpi); //像素密度
        sb.append("\nheightPixels    :").append(dm.heightPixels); //屏幕高
        sb.append("\nwidthPixels     :").append(dm.widthPixels); //屏幕宽
        sb.append("\nscaledDensity   :").append(dm.scaledDensity); //缩放密度
        sb.append("\nxdpi            :").append(dm.xdpi); //分辨率 宽
        sb.append("\nydpi            :").append(dm.ydpi); //分辨率 高
        Log.i("TAG", sb.toString());
        return dm;
    }

    //</editor-fold>
    //<editor-fold desc="设置全屏 / 横竖屏 / 旋转角度">


    /**
     * 设置全屏
     *
     * @param activity The activity.
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 设置为横屏
     *
     * @param activity The activity.
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * 设置为竖屏
     *
     * @param activity The activity.
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 是否为横屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape() {
        return Util.getApp().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 是否为竖屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return Util.getApp().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity The activity.
     * @return the rotation of screen
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    //</editor-fold>
    //<editor-fold desc="截屏 / 锁屏 / 进入休眠时长">

    /**
     * 截屏
     *
     * @param activity The activity.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * 截屏
     *
     * @param activity          The activity.
     * @param isDeleteStatusBar True to delete status bar, false otherwise.
     * @return the bitmap of screen
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 是否锁屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) Util.getApp().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * 是否为平板
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTablet() {
        return (Util.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 设置进入休眠时长
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration The duration.
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                Util.getApp().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * 获取进入休眠时长
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    Util.getApp().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }
    //</editor-fold>
    //<editor-fold desc="屏幕宽高">

    /**
     * 获取屏幕宽度,in px
     *
     * @return the width of screen, in pixel
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) Util.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return Util.getApp().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return Util.getApp().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度，包含状态栏导航栏,in px
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) Util.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return Util.getApp().getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    //</editor-fold>
    //<editor-fold desc="状态栏">


    /**
     * 获取状态栏高度
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Util.getApp().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 设置状态栏是否隐藏
     *
     * @param activity  The activity.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity, final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * 设置状态栏是否隐藏
     *
     * @param window    The window.
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window, final boolean isVisible) {
        if (isVisible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            showStatusBarView(window);
            addMarginTopEqualStatusBarHeight(window);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideStatusBarView(window);
            subtractMarginTopEqualStatusBarHeight(window);
        }
    }

    /**
     * 状态栏是否显示
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * Add the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        view.setTag(TAG_OFFSET);
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin + getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, true);
    }

    /**
     * Subtract the top margin size equals status bar's height for view.
     *
     * @param view The view.
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                layoutParams.topMargin - getStatusBarHeight(),
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, false);
    }
    //</editor-fold>
    //<editor-fold desc="导航栏">

    /**
     * 获取导航栏高度
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        Resources res = Util.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 设置导航栏显示隐藏
     *
     * @param activity  The activity.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Activity activity, boolean isVisible) {
        setNavBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * 设置导航栏显示隐藏
     *
     * @param window    The window.
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    public static void setNavBarVisibility(@NonNull final Window window, boolean isVisible) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        final ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = Util.getApp().getResources().getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName)) {
                    child.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
                }
            }
        }
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (isVisible) {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~uiOptions);
        } else {
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | uiOptions);
        }
    }

    /**
     * 设置导航栏的沉浸
     *
     * @param activity The activity.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(@NonNull final Activity activity) {
        setNavBarImmersive(activity.getWindow());
    }

    /**
     * 设置导航栏的沉浸
     *
     * @param window The window.
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(@NonNull final Window window) {
        View decorView = window.getDecorView();
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 设置导航栏的颜色
     *
     * @param activity The activity.
     * @param color    The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setNavBarColor(activity.getWindow(), color);
    }

    /**
     * 设置导航栏的颜色
     *
     * @param window The window.
     * @param color  The navigation bar's color.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavBarColor(@NonNull final Window window, @ColorInt final int color) {
        window.setNavigationBarColor(color);
    }

    /**
     * 获取导航栏的颜色
     *
     * @param activity The activity.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Activity activity) {
        return getNavBarColor(activity.getWindow());
    }

    /**
     * 获取导航栏的颜色
     *
     * @param window The window.
     * @return the color of navigation bar
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public static int getNavBarColor(@NonNull final Window window) {
        return window.getNavigationBarColor();
    }

    /**
     * 导航栏是否可见
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Activity activity) {
        return isNavBarVisible(activity.getWindow());
    }

    /**
     * 导航栏是否可见
     *
     * @param window The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarVisible(@NonNull final Window window) {
        boolean isVisible = false;
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
            final View child = decorView.getChildAt(i);
            final int id = child.getId();
            if (id != View.NO_ID) {
                String resourceEntryName = Util.getApp().getResources().getResourceEntryName(id);
                if ("navigationBarBackground".equals(resourceEntryName) && child.getVisibility() == View.VISIBLE) {
                    isVisible = true;
                    break;
                }
            }
        }
        if (isVisible) {
            int visibility = decorView.getSystemUiVisibility();
            isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
        }
        return isVisible;
    }

    /**
     * Return whether the navigation bar visible.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSupportNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager wm = (WindowManager) Util.getApp().getSystemService(Context.WINDOW_SERVICE);
            if (wm == null) return false;
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y || realSize.x != size.x;
        }
        boolean menu = ViewConfiguration.get(Util.getApp()).hasPermanentMenuKey();
        boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !menu && !back;
    }
    //</editor-fold>
    //<editor-fold desc="Action Bar">

    /**
     * 获取ActionBar的高度
     *
     * @return the action bar's height
     */
    public static int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        if (Util.getApp().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, Util.getApp().getResources().getDisplayMetrics()
            );
        }
        return 0;
    }
    //</editor-fold>
    //<editor-fold desc="通知栏">

    /**
     * 设置通知栏显示隐藏
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />}</p>
     *
     * @param isVisible True to set notification bar visible, false otherwise.
     */
    @RequiresPermission(EXPAND_STATUS_BAR)
    public static void setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        }
        invokePanels(methodName);
    }

    private static void invokePanels(final String methodName) {
        try {
            @SuppressLint("WrongConstant")
            Object service = Util.getApp().getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>
    //<editor-fold desc="私有方法">

    private static void hideStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    private static void showStatusBarView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    private static void addMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        addMarginTopEqualStatusBarHeight(withTag);
    }

    private static void subtractMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        subtractMarginTopEqualStatusBarHeight(withTag);
    }

    //</editor-fold>


}
