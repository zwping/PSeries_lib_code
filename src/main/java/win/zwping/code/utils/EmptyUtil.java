package win.zwping.code.utils;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import win.zwping.code.basic.IUtil;

/**
 * <p>describe：空值判断 工具类
 * <p>    note：
 * <p>    note：isEmpty / isNotEmpty  / equals
 * <p>    note：isEmptys / isNotEmptys / isEmptysII / isNotEmptysII
 * <p>    note：
 * <p>  author：zwp on 2017/9/22 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class EmptyUtil implements IUtil.INativeUtil {
    private EmptyUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray
                    && ((android.util.LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return {@code true}: 非空<br>{@code false}: 空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /*** Empty && Empty ***/
    public static boolean isEmptys(Object... objs) {
        if (isEmpty(objs)) return true;
        for (Object obj : objs) if (isNotEmpty(obj)) return false; // 一个不为空就Pass
        return true;
    }

    /*** NotEmpty && NotEmpty ***/
    public static boolean isNotEmptys(Object... objs) {
        if (isEmpty(objs)) return false;
        for (Object obj : objs) if (isEmpty(obj)) return false;
        return true;
    }

    /*** Empty || Empty ***/
    public static boolean isEmptysII(Object... objs) {
        if (isEmpty(objs)) return true;
        for (Object obj : objs) if (isEmpty(obj)) return true;
        return false;
    }

    /*** NotEmpty || NotEmpty ***/
    public static boolean isNotEmptysII(Object... objs) {
        if (isEmpty(objs)) return false;
        for (Object obj : objs) if (isNotEmpty(obj)) return true;
        return false;
    }

    /**
     * 判断两个Object是否相等
     *
     * @param o1 The first object.
     * @param o2 The second object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

}
