package win.zwping.code.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import win.zwping.code.basic.IUtil;

/**
 * <p>describe：Fragment 工具类
 * <p>    note：
 * <p>    note：add / show / showHide / hide / replace / pop / remove
 * <p>    note：getTopFm / getFm / getAllFm / findFm
 * <p>    note：dispatchBackPress / setBackground / getFmName
 * <p>    note：
 * <p> @author：zwp on 2018/3/12 0012 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class FragmentUtil implements IUtil.INativeUtil {

    //<editor-fold desc="常量">

    private static final int TYPE_ADD_FRAGMENT = 0x01;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT = 0x01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FRAGMENT = 0x01 << 6;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";
    private static final String ARGS_TAG = "args_tag";

    //</editor-fold>

    private FragmentUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //<editor-fold desc="Add">

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param add         The fragment will be add.
     * @param containerId The id of container.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId) {
        add(fm, add, containerId, false, false);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param add         The fragment will be add.
     * @param containerId The id of container.
     * @param isHide      True to hide, false otherwise.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide) {
        add(fm, add, containerId, isHide, false);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param add         The fragment will be add.
     * @param containerId The id of container.
     * @param isHide      True to hide, false otherwise.
     * @param isAddStack  True to add fragment in stack, false otherwise.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isHide,
                           final boolean isAddStack) {
        putArgs(add, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param add         The fragment will be add.
     * @param containerId The id of container.
     * @param enterAnim   An animation or animator resource ID used for the enter animation on the
     *                    view of the fragment being added or attached.
     * @param exitAnim    An animation or animator resource ID used for the exit animation on the
     *                    view of the fragment being removed or detached.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param containerId The id of container.
     * @param add         The fragment will be add.
     * @param isAddStack  True to add fragment in stack, false otherwise.
     * @param enterAnim   An animation or animator resource ID used for the enter animation on the
     *                    view of the fragment being added or attached.
     * @param exitAnim    An animation or animator resource ID used for the exit animation on the
     *                    view of the fragment being removed or detached.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim) {
        add(fm, add, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 新增 Fragment
     *
     * @param fm           The manager of fragment.
     * @param containerId  The id of container.
     * @param add          The fragment will be add.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 新增 Fragment
     *
     * @param fm           The manager of fragment.
     * @param containerId  The id of container.
     * @param add          The fragment will be add.
     * @param isAddStack   True to add fragment in stack, false otherwise.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @AnimRes final int enterAnim,
                           @AnimRes final int exitAnim,
                           @AnimRes final int popEnterAnim,
                           @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增 Fragment
     *
     * @param fm             The manager of fragment.
     * @param add            The fragment will be add.
     * @param containerId    The id of container.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @NonNull final View... sharedElements) {
        add(fm, add, containerId, false, sharedElements);
    }

    /**
     * 新增 Fragment
     *
     * @param fm             The manager of fragment.
     * @param add            The fragment will be add.
     * @param containerId    The id of container.
     * @param isAddStack     True to add fragment in stack, false otherwise.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param adds        The fragments will be add.
     * @param containerId The id of container.
     * @param showIndex   The index of fragment will be shown.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final List<Fragment> adds,
                           @IdRes final int containerId,
                           final int showIndex) {
        add(fm, adds.toArray(new Fragment[adds.size()]), containerId, showIndex);
    }

    /**
     * 新增 Fragment
     *
     * @param fm          The manager of fragment.
     * @param adds        The fragments will be add.
     * @param containerId The id of container.
     * @param showIndex   The index of fragment will be shown.
     */
    public static void add(@NonNull final FragmentManager fm,
                           @NonNull final Fragment[] adds,
                           @IdRes final int containerId,
                           final int showIndex) {
        for (int i = 0, len = adds.length; i < len; ++i) {
            putArgs(adds[i], new Args(containerId, showIndex != i, false));
        }
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, adds);
    }

    //</editor-fold>
    //<editor-fold desc="显示 / 隐藏">

    /**
     * 显示Fragment
     *
     * @param show The fragment will be show.
     */
    public static void show(@NonNull final Fragment show) {
        putArgs(show, false);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, null, show);
    }

    /**
     * 显示Fragment
     *
     * @param fm The manager of fragment.
     */
    public static void show(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment show : fragments) {
            putArgs(show, false);
        }
        operateNoAnim(fm,
                TYPE_SHOW_FRAGMENT,
                null,
                fragments.toArray(new Fragment[fragments.size()])
        );
    }

    /**
     * 隐藏Fragment
     *
     * @param hide The fragment will be hide.
     */
    public static void hide(@NonNull final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(hide.getFragmentManager(), TYPE_HIDE_FRAGMENT, null, hide);
    }

    /**
     * 隐藏Fragment
     *
     * @param fm The manager of fragment.
     */
    public static void hide(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment hide : fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(fm,
                TYPE_HIDE_FRAGMENT,
                null,
                fragments.toArray(new Fragment[fragments.size()])
        );
    }

    /**
     * 显示Fragment隐藏其它Fragment...
     *
     * @param showIndex The index of fragment will be shown.
     * @param fragments The fragments will be hide.
     */
    public static void showHide(final int showIndex, @NonNull final List<Fragment> fragments) {
        showHide(fragments.get(showIndex), fragments);
    }

    /**
     * 显示Fragment隐藏其它Fragment...
     *
     * @param show The fragment will be show.
     * @param hide The fragment will be hide.
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final List<Fragment> hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show,
                hide.toArray(new Fragment[hide.size()]));
    }

    /**
     * 显示Fragment隐藏其它Fragment...
     *
     * @param showIndex The index of fragment will be shown.
     * @param fragments The fragment will be hide.
     */
    public static void showHide(final int showIndex, @NonNull final Fragment... fragments) {
        showHide(fragments[showIndex], fragments);
    }

    /**
     * 显示Fragment隐藏其它Fragment...
     *
     * @param show The fragment will be show.
     * @param hide The fragment will be hide.
     */
    public static void showHide(@NonNull final Fragment show, @NonNull final Fragment... hide) {
        for (Fragment fragment : hide) {
            putArgs(fragment, fragment != show);
        }
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    /**
     * 显示Fragment隐藏其它Fragment...
     *
     * @param show The fragment will be show.
     * @param hide The fragment will be hide.
     */
    public static void showHide(@NonNull final Fragment show,
                                @NonNull final Fragment hide) {
        putArgs(show, false);
        putArgs(hide, true);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_HIDE_FRAGMENT, show, hide);
    }

    //</editor-fold>
    //<editor-fold desc="Replace">

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment) {
        replace(srcFragment, destFragment, false);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     * @param isAddStack   True to add fragment in stack, false otherwise.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     * @param isAddStack   True to add fragment in stack, false otherwise.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(srcFragment, destFragment, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(srcFragment, destFragment, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment  The source of fragment.
     * @param destFragment The destination of fragment.
     * @param isAddStack   True to add fragment in stack, false otherwise.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(), destFragment, args.id, isAddStack,
                enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment    The source of fragment.
     * @param destFragment   The destination of fragment.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final View... sharedElements) {
        replace(srcFragment, destFragment, false, sharedElements);
    }

    /**
     * 替换显示Fragment
     *
     * @param srcFragment    The source of fragment.
     * @param destFragment   The destination of fragment.
     * @param isAddStack     True to add fragment in stack, false otherwise.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void replace(@NonNull final Fragment srcFragment,
                               @NonNull final Fragment destFragment,
                               final boolean isAddStack,
                               final View... sharedElements) {
        Args args = getArgs(srcFragment);
        replace(srcFragment.getFragmentManager(),
                destFragment,
                args.id,
                isAddStack,
                sharedElements
        );
    }

    /**
     * 替换显示Fragment
     *
     * @param fm          The manager of fragment.
     * @param fragment    The new fragment to place in the container.
     * @param containerId The id of container.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId) {
        replace(fm, fragment, containerId, false);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm          The manager of fragment.
     * @param containerId The id of container.
     * @param fragment    The new fragment to place in the container.
     * @param isAddStack  True to add fragment in stack, false otherwise.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm          The manager of fragment.
     * @param containerId The id of container.
     * @param fragment    The new fragment to place in the container.
     * @param enterAnim   An animation or animator resource ID used for the enter animation on the
     *                    view of the fragment being added or attached.
     * @param exitAnim    An animation or animator resource ID used for the exit animation on the
     *                    view of the fragment being removed or detached.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm          The manager of fragment.
     * @param containerId The id of container.
     * @param fragment    The new fragment to place in the container.
     * @param isAddStack  True to add fragment in stack, false otherwise.
     * @param enterAnim   An animation or animator resource ID used for the enter animation on the
     *                    view of the fragment being added or attached.
     * @param exitAnim    An animation or animator resource ID used for the exit animation on the
     *                    view of the fragment being removed or detached.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim) {
        replace(fm, fragment, containerId, isAddStack, enterAnim, exitAnim, 0, 0);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm           The manager of fragment.
     * @param containerId  The id of container.
     * @param fragment     The new fragment to place in the container.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        replace(fm, fragment, containerId, false, enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm           The manager of fragment.
     * @param containerId  The id of container.
     * @param fragment     The new fragment to place in the container.
     * @param isAddStack   True to add fragment in stack, false otherwise.
     * @param enterAnim    An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being added or attached.
     * @param exitAnim     An animation or animator resource ID used for the exit animation on the
     *                     view of the fragment being removed or detached.
     * @param popEnterAnim An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being readded or reattached caused by
     *                     popBackStack() or similar methods.
     * @param popExitAnim  An animation or animator resource ID used for the enter animation on the
     *                     view of the fragment being removed or detached caused by
     *                     popBackStack() or similar methods.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               @AnimRes final int enterAnim,
                               @AnimRes final int exitAnim,
                               @AnimRes final int popEnterAnim,
                               @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm             The manager of fragment.
     * @param containerId    The id of container.
     * @param fragment       The new fragment to place in the container.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final View... sharedElements) {
        replace(fm, fragment, containerId, false, sharedElements);
    }

    /**
     * 替换显示Fragment
     *
     * @param fm             The manager of fragment.
     * @param containerId    The id of container.
     * @param fragment       The new fragment to place in the container.
     * @param isAddStack     True to add fragment in stack, false otherwise.
     * @param sharedElements A View in a disappearing Fragment to match with a View in an
     *                       appearing Fragment.
     */
    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               @IdRes final int containerId,
                               final boolean isAddStack,
                               final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        addSharedElement(ft, sharedElements);
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);
    }

    //</editor-fold>
    //<editor-fold desc="出栈 / 移除">

    /**
     * 出栈 Fragment
     *
     * @param fm The manager of fragment.
     */
    public static void pop(@NonNull final FragmentManager fm) {
        pop(fm, true);
    }

    /**
     * 出栈 Fragment
     *
     * @param fm          The manager of fragment.
     * @param isImmediate True to pop immediately, false otherwise.
     */
    public static void pop(@NonNull final FragmentManager fm,
                           final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    /**
     * 指定Fragment出栈
     *
     * @param fm            The manager of fragment.
     * @param popClz        The class of fragment will be popped to.
     * @param isIncludeSelf True to include the fragment, false otherwise.
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isIncludeSelf) {
        popTo(fm, popClz, isIncludeSelf, true);
    }

    /**
     * 指定Fragment出栈
     *
     * @param fm            The manager of fragment.
     * @param popClz        The class of fragment will be popped to.
     * @param isIncludeSelf True to include the fragment, false otherwise.
     * @param isImmediate   True to pop immediately, false otherwise.
     */
    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isIncludeSelf,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(),
                    isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(),
                    isIncludeSelf ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
    }

    /**
     * 所有Fragment出栈
     *
     * @param fm The manager of fragment.
     */
    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    /**
     * 所有Fragment出栈
     *
     * @param fm The manager of fragment.
     */
    public static void popAll(@NonNull final FragmentManager fm, final boolean isImmediate) {
        while (fm.getBackStackEntryCount() > 0) {
            if (isImmediate) {
                fm.popBackStackImmediate();
            } else {
                fm.popBackStack();
            }
        }
    }

    /**
     * 移除 Fragment
     *
     * @param remove The fragment will be removed.
     */
    public static void remove(@NonNull final Fragment remove) {
        operateNoAnim(remove.getFragmentManager(), TYPE_REMOVE_FRAGMENT, null, remove);
    }

    /**
     * 移除 指定Fragment
     *
     * @param removeTo      The fragment will be removed to.
     * @param isIncludeSelf True to include the fragment, false otherwise.
     */
    public static void removeTo(@NonNull final Fragment removeTo, final boolean isIncludeSelf) {
        operateNoAnim(removeTo.getFragmentManager(), TYPE_REMOVE_TO_FRAGMENT,
                isIncludeSelf ? removeTo : null, removeTo);
    }

    /**
     * 移除所有Fragment
     *
     * @param fm The manager of fragment.
     */
    public static void removeAll(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        operateNoAnim(fm,
                TYPE_REMOVE_FRAGMENT,
                null,
                fragments.toArray(new Fragment[fragments.size()])
        );
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
        bundle.putString(ARGS_TAG, args.tag);
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static Args getArgs(final Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        return new Args(bundle.getInt(ARGS_ID, fragment.getId()),
                bundle.getBoolean(ARGS_IS_HIDE),
                bundle.getBoolean(ARGS_IS_ADD_STACK));
    }

    private static void operateNoAnim(final FragmentManager fm,
                                      final int type,
                                      final Fragment src,
                                      Fragment... dest) {
        FragmentTransaction ft = fm.beginTransaction();
        operate(type, fm, ft, src, dest);
    }

    private static void operate(final int type,
                                @NonNull final FragmentManager fm,
                                final FragmentTransaction ft,
                                final Fragment src,
                                final Fragment... dest) {
        if (src != null && src.isRemoving()) {
            Log.e("FragmentUtils", src.getClass().getName() + " is isRemoving");
            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT:
                for (Fragment fragment : dest) {
                    args = fragment.getArguments();
                    if (args == null) return;
                    name = args.getString(ARGS_TAG, fragment.getClass().getName());
                    Fragment fragmentByTag = fm.findFragmentByTag(name);
                    if (fragmentByTag != null && fragmentByTag.isAdded()) {
                        ft.remove(fragmentByTag);
                    }
                    ft.add(args.getInt(ARGS_ID), fragment, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(fragment);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                }
                break;
            case TYPE_HIDE_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.hide(fragment);
                }
                break;
            case TYPE_SHOW_FRAGMENT:
                for (Fragment fragment : dest) {
                    ft.show(fragment);
                }
                break;
            case TYPE_SHOW_HIDE_FRAGMENT:
                ft.show(src);
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.hide(fragment);
                    }
                }
                break;
            case TYPE_REPLACE_FRAGMENT:
                args = dest[0].getArguments();
                if (args == null) return;
                name = args.getString(ARGS_TAG, dest[0].getClass().getName());
                ft.replace(args.getInt(ARGS_ID), dest[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;
            case TYPE_REMOVE_FRAGMENT:
                for (Fragment fragment : dest) {
                    if (fragment != src) {
                        ft.remove(fragment);
                    }
                }
                break;
            case TYPE_REMOVE_TO_FRAGMENT:
                for (int i = dest.length - 1; i >= 0; --i) {
                    Fragment fragment = dest[i];
                    if (fragment == dest[0]) {
                        if (src != null) ft.remove(fragment);
                        break;
                    }
                    ft.remove(fragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private static void addAnim(final FragmentTransaction ft,
                                final int enter,
                                final int exit,
                                final int popEnter,
                                final int popExit) {
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    private static void addSharedElement(final FragmentTransaction ft,
                                         final View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : views) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    //</editor-fold>
    //<editor-fold desc="获取栈和FM中 Fragment的数量/位置">

    /**
     * 获取*顶部*的Fragment
     *
     * @param fm The manager of fragment.
     * @return the top fragment
     */
    public static Fragment getTop(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, false);
    }

    /**
     * 获取*栈顶*的Fragment
     *
     * @param fm The manager of fragment.
     * @return the top fragment in stack
     */
    public static Fragment getTopInStack(@NonNull final FragmentManager fm) {
        return getTopIsInStack(fm, true);
    }

    private static Fragment getTopIsInStack(@NonNull final FragmentManager fm,
                                            final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取*顶部*可见的Fragment
     *
     * @param fm The manager of fragment.
     * @return the top fragment which is shown
     */
    public static Fragment getTopShow(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, false);
    }

    /**
     * 获取*栈顶*可见的Fragment
     *
     * @param fm The manager of fragment.
     * @return the top fragment which is shown in stack
     */
    public static Fragment getTopShowInStack(@NonNull final FragmentManager fm) {
        return getTopShowIsInStack(fm, true);
    }

    private static Fragment getTopShowIsInStack(@NonNull final FragmentManager fm,
                                                final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                        return fragment;
                    }
                } else {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * 获取同级别的Fragment
     *
     * @param fm The manager of fragment.
     * @return the fragments in manager
     */
    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        @SuppressWarnings("RestrictedApi")
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.isEmpty()) return Collections.emptyList();
        return fragments;
    }

    /**
     * 获取栈中同级别的Fragment
     *
     * @param fm The manager of fragment.
     * @return the fragments in stack in manager
     */
    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        List<Fragment> result = new ArrayList<>();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(fragment);
            }
        }
        return result;
    }

    /**
     * 获取所有的Fragment
     *
     * @param fm The manager of fragment.
     * @return all fragments in manager
     */
    public static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm) {
        return getAllFragments(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm,
                                                      final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                result.add(new FragmentNode(fragment,
                        getAllFragments(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    /**
     * 获取栈中所有的Fragment
     *
     * @param fm The manager of fragment.
     * @return all fragments in stack in manager
     */
    public static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm) {
        return getAllFragmentsInStack(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragmentsInStack(@NonNull final FragmentManager fm,
                                                             final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) {
                result.add(new FragmentNode(fragment,
                        getAllFragmentsInStack(fragment.getChildFragmentManager(),
                                new ArrayList<FragmentNode>())));
            }
        }
        return result;
    }

    //</editor-fold>
    //<editor-fold desc="查找 获取">

    /**
     * 查找Fragment
     *
     * @param fm      The manager of fragment.
     * @param findClz The class of fragment will be found.
     * @return the fragment matches class
     */
    public static Fragment findFragment(@NonNull final FragmentManager fm,
                                        final Class<? extends Fragment> findClz) {
        return fm.findFragmentByTag(findClz.getName());
    }

    /**
     * 获取Fragment Class 简称
     *
     * @param fragment The fragment.
     * @return the simple name of fragment
     */
    public static String getSimpleName(final Fragment fragment) {
        return fragment == null ? "null" : fragment.getClass().getSimpleName();
    }

    //</editor-fold>
    //<editor-fold desc="设置 Fragment">


    /**
     * 处理Fragment的后退键
     *
     * @param fragment The fragment.
     * @return {@code true}: the fragment consumes the back press<br>{@code false}: otherwise
     */
    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    /**
     * 处理Fragment的后退键
     *
     * @param fm The manager of fragment.
     * @return {@code true}: the fragment consumes the back press<br>{@code false}: otherwise
     */
    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null
                    && fragment.isResumed()
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof OnBackClickListener
                    && ((OnBackClickListener) fragment).onBackClick()) {
                return true;
            }
        }
        return false;
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }

    /**
     * 设置Fragment背景色
     *
     * @param fragment The fragment.
     * @param color    The background color.
     */
    public static void setBackgroundColor(@NonNull final Fragment fragment,
                                          @ColorInt final int color) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置Fragment背景资源
     *
     * @param fragment The fragment.
     * @param resId    The resource id.
     */
    public static void setBackgroundResource(@NonNull final Fragment fragment,
                                             @DrawableRes final int resId) {
        View view = fragment.getView();
        if (view != null) {
            view.setBackgroundResource(resId);
        }
    }

    /**
     * 设置Fragment背景
     *
     * @param fragment   The fragment.
     * @param background The background.
     */
    public static void setBackground(@NonNull final Fragment fragment, final Drawable background) {
        ViewCompat.setBackground(fragment.getView(), background);
    }

    //</editor-fold>

    //<editor-fold desc="私有方法">

    private static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;
        final String tag;

        Args(final int id, final boolean isHide, final boolean isAddStack) {
            this(id, null, isHide, isAddStack);
        }

        Args(final int id, final String tag,
             final boolean isHide, final boolean isAddStack) {
            this.id = id;
            this.tag = tag;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    public static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(final Fragment fragment, final List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }

        @Override
        public String toString() {
            return fragment.getClass().getSimpleName()
                    + "->"
                    + ((next == null || next.isEmpty()) ? "no child" : next.toString());
        }
    }


    //</editor-fold>
}
