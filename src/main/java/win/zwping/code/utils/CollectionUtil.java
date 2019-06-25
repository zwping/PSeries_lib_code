package win.zwping.code.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import win.zwping.code.basic.IUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：集合工具栏
 * <p>    note：
 * <p>    note：split / splitList / splitLength  / covStrings
 * <p>    note：
 * <p>  author：zwp on 2018-06-11 14:26:46 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class CollectionUtil implements IUtil.INativeUtil {

    /**
     * 将文字切割为数组
     *
     * @param s
     * @param splitX
     * @return deprecated 注意Kotlin与Java编码并不相通
     */
    public static String[] split(String s, String splitX) {
        if (isEmpty(s) || isEmpty(splitX)) return null;
        if (!s.contains(splitX)) return new String[]{s};
        return s.split(splitX);
    }

    /**
     * 将文字切割为字符集合
     *
     * @param s
     * @param splitX
     * @return
     */
    @Nullable
    public static List<String> splitList(String s, String splitX) {
        String[] strings = split(s, splitX);
        if (isEmpty(strings)) return null;
        return new ArrayList<>(Arrays.asList(strings));
    }

    /*** 根据长度切割字符串 ***/
    public static List<String> splitLength(String s, int length) {
        if (isEmpty(s) || 0 == length) return null;
        if (s.length() <= length) return Collections.singletonList(s);
        List<String> list = new ArrayList<>();
        boolean open = true;
        while (open) {
            list.add(s.substring(0, length));
            s = s.substring(length, s.length());
            if (s.length() <= length) {
                open = false;
                list.add(s);
            }
        }
        return list;
    }

    /*** List 2 [] ***/
    public static String[] covStrings(List<String> list) {
        return (list.toArray(new String[list.size()]));
    }

}
