package win.zwping.code.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import win.zwping.code.basic.IUtil;

import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.ConversionUtil.px2dp;

/**
 * <p>describe：View 工具类
 * <p>    note：
 * <p>  author：zwp on 2018-07-09 16:27:21 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class ViewUtil implements IUtil.INativeUtil {


    public static View inflate(Context context, @LayoutRes int id) {
        return LayoutInflater.from(context).inflate(id, null, false);
    }

    public static View inflate(Context context, @LayoutRes int id, ViewGroup root) {
        return LayoutInflater.from(context).inflate(id, root, false);
    }

    public static int getYOnScreen(View v) {
        int[] l = new int[2];
        v.getLocationOnScreen(l);
        return l[1];
    }

    public static int getXOnScreen(View v) {
        int[] l = new int[2];
        v.getLocationOnScreen(l);
        return l[0];
    }

    public static int getYOnWindow(View v) {
        int[] l = new int[2];
        v.getLocationInWindow(l);
        return l[1];
    }

    public static int getXOnWindow(View v) {
        int[] l = new int[2];
        v.getLocationInWindow(l);
        return l[0];
    }

    /**
     * 获取测量视图宽度
     *
     * @param view The view.
     * @return the width of view
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * 获取测量视图高度
     *
     * @param view The view.
     * @return the height of view
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * 测量视图尺寸
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }


    /**
     * 设置View margin
     */
    public static void setMargins(View v, int lDp, int tDp, int rDp, int bDp) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        p.setMargins(dp2px(v.getContext(), lDp), dp2px(v.getContext(), tDp), dp2px(v.getContext(), rDp), dp2px(v.getContext(), bDp));
        v.setLayoutParams(p);
    }

    /*** 设置View的宽高 ***/
    public static void setViewPostWH(@Nullable final View view, final int wDp, final int hDp) {
        if (null == view) return;
        setViewWHPx(view, dp2px(view.getContext(), wDp), dp2px(view.getContext(), hDp));
    }

    public static void setViewPostWHPx(@Nullable final View view, final int wPx, final int hPx) {
        if (view == null) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (0 != wPx) params.width = wPx;
        if (0 != hPx) params.height = hPx;
        view.post(() -> view.setLayoutParams(params));
    }

    public static void setViewWH(@Nullable final View view, final int wDp, final int hDp) {
        if (null == view) return;
        setViewWHPx(view, dp2px(view.getContext(), wDp), dp2px(view.getContext(), hDp));
    }

    public static void setViewWHPx(@Nullable final View view, final int wPx, final int hPx) {
        if (view == null) return;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (0 != wPx) params.width = wPx;
        if (0 != hPx) params.height = hPx;
        view.setLayoutParams(params);
    }

    /*** 设置隐藏 ***/
    public static void setGone(View... views) {
        for (View view : views) {
            if (view != null) view.setVisibility(View.GONE);
        }
    }

    public static void setGone(View view,Boolean visibility){
        if(view != null) view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /*** 设置显示 ***/
    public static void setVisible(View... views) {
        for (View view : views) {
            if (view != null) view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置空的点击事件
     * 点击事件屏蔽
     * @param views
     */
    public static void setNullClickLis(View... views) {
        for (View view : views)
            if (view != null)
                view.setOnClickListener(v -> {
                });
    }

}
