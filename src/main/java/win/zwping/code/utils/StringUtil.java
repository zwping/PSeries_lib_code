package win.zwping.code.utils;

import java.text.NumberFormat;
import java.util.Locale;

import win.zwping.code.basic.IUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：字符串相关工具类
 * <p>    note：
 * <p>    note：length / isSpace / replaceSpace / equals / equalsIgnoreCase /
 * <p>    note：isContainsNewLine / newLineToSpace / replaceMaxNewline / null2Length0
 * <p>    note：upperFirstLetter / lowerFirstLetter / reverse / toDBC / toSBC
 * <p>    note：changeSafetyPhone / changeNicePhone / changeSafetyIDCard / niceMoney / niceBigMoney
 * <p>    note：
 * <p> @author：zwp on 2017/12/9 0009 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class StringUtil implements IUtil.INativeUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //<editor-fold desc="长度 / 是否为空 / 替换空格 / 全等 / 忽略大小全等 ">

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空白字符<br> {@code false}: 不为 null 且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去除空格
     *
     * @param s
     * @return
     */
    public static String replaceSpace(String s) {
        if (isEmpty(s)) return null;
        return s.replaceAll("\\s*", ""); //去除空格 \s可以匹配空格、制表符、换页符等空白字符的其中任意一个
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if(isEmpty(a) && isEmpty(b)) return false;
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }


    //</editor-fold>
    //<editor-fold desc="是否包含换行 / 换行使用空格替代 / 最多允许多少个换行 / null 2 '' ">

    /**
     * 是否包含换行符
     *
     * @param s /r Mac /n Unix/Linux /r/n Windows
     * @return contains or unContains or empty
     */
    public static boolean isContainsNewLine(String s) {
        if (isEmpty(s)) return false;
        return s.contains("\n") || s.contains("\r") || s.contains("\r\n");
    }

    /**
     * 换行转换为空格
     *
     * @param s
     * @return
     */
    public static String newLineToSpace(String s) {
        if (isEmpty(s)) return null;
        return s.replaceAll("\r|\n", " ");
    }

    /**
     * 连续换行中，最多允许max个换行，其余的换行用空格顶替
     *
     * @param s
     * @return
     */
    public static String replaceMaxNewline(String s, int max) {
        if (!isContainsNewLine(s)) return s;
        if (max < 0) max = 0;
        int length = s.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i < length - max) {
                boolean exist = true; //假设max个换行成立
                for (int i1 = 0; i1 < max + 1; i1++) {
                    if (!isContainsNewLine(String.valueOf(s.charAt(i + i1)))) exist = false;
                }
                stringBuilder.append(exist ? " " : s.charAt(i));
            } else stringBuilder.append(s.charAt(i));
        }
        return stringBuilder.toString();
    }

    /**
     * null 转为长度为 0 的字符串
     *
     * @param s 待转字符串
     * @return s 为 null 转为长度为 0 字符串，否则不改变
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }


    //</editor-fold>
    //<editor-fold desc="首字母大/小写 / 字符串反转 / 全/半角转换">

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    //</editor-fold>
    //<editor-fold desc="好看的 手机号 / 身份证号 / 千分位">

    /*** 转变为安全的手机号 - 中间打码 ***/
    public static String changeSafetyPhone(String phone) {
        if (isEmpty(phone) || phone.equals("0")) return "";
        if (phone.length() != 11) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }

    /*** 转变为好看的手机号 - 中间空格 ***/
    public static String changeNicePhone(String phone) {
        if (isEmpty(phone) || phone.equals("0")) return "";
        if (phone.length() != 11) return phone;
        return phone.substring(0, 3) + " " + phone.substring(3, 7) + " " + phone.substring(7, 11);
    }

    /*** 转变为安全的身份证号 - 中间打码 ***/
    public static String changeSafetyIDCard(String phone) {
        if (isEmpty(phone) || phone.equals("0")) return "";
        if (phone.length() != 18) return phone;
        return phone.substring(0, 8) + "******" + phone.substring(phone.length() - 4, phone.length());
    }

    /*** 转换为好看的金钱格式 -- 999,999,999 ***/
    public static String niceMoney(String s) {
        if (isEmpty(s) || s.equals("0")) return "0元";
        if (s.contains(",")) s = s.replaceAll(",", "");
        //if (s.contains(",")) return s + "元"; // 已经数字格式化了
        try {
            double d = Double.valueOf(s);
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.CHINA);
            nf.setMaximumFractionDigits(2);
            return nf.format(d) + "元";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return s + "元";
        }
    }

    public static String niceBigMoney(String s) {
        if (isEmpty(s) || s.equals("0")) return "0元";
        if (s.contains(",")) s = s.replaceAll(",", "");
        //if (s.contains(",")) return s + "元"; // 已经数字格式化了
        try {
            double d = Double.valueOf(s);
            NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
            nf.setMaximumFractionDigits(2);
            if (d < 100000) {
                return nf.format(d) + "元";
            } else if (d < 100000000) {
                return nf.format(Double.valueOf(d / 10000D)) + "万元";
//                return new BigDecimal(d / 10000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万元";
            } else {
                return nf.format(d / 100000000D) + "亿元";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return s + "元";
        }
    }

    //</editor-fold>


}
