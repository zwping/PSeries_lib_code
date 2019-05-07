package win.zwping.code.utils;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import win.zwping.code.basic.IUtil;

import static android.text.TextUtils.isEmpty;

/**
 * <p>describe：编码解码相关工具类
 * <p>    note：
 * <p>    note：getEncoding
 * <p>    note：urlEncode / urlDecode / base64Encode / base64Decode / htmlEncode / htmlDecode
 * <p>    note：
 * <p> @author：zwp on 2017/12/28 0028 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class EncodeUtil implements IUtil.INativeUtil {

    private EncodeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    //<editor-fold desc="Url">

    /**
     * 字符串转换为url编码格式（自定义模式 - 模拟浏览器地址）
     *
     * @param s 等待转换的
     * @return url编码字符串
     */
    public static String urlEncode1(final String s) {
        if (isEmpty(s)) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    //System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString().replaceAll(" ", "%20");
    }

    /**
     * URL 编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为 UTF-8 的字符串
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * URL 编码
     * <p>若系统不支持指定的编码字符集,则直接将 input 原样返回</p>
     *
     * @param input   要编码的字符
     * @param charset 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(final String input, final String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL 解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL 解码后的字符串
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL 解码
     * <p>若系统不支持指定的解码字符集,则直接将 input 原样返回</p>
     *
     * @param input   要解码的字符串
     * @param charset 字符集
     * @return URL 解码为指定字符集的字符串
     */
    public static String urlDecode(final String input, final String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    //</editor-fold>
    //<editor-fold desc="Base64">

    /**
     * Base64 编码
     *
     * @param input 要编码的字符串
     * @return Base64 编码后的字符串
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64 编码
     *
     * @param input 要编码的字节数组
     * @return Base64 编码后的字符串
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     *
     * @param input 要编码的字节数组
     * @return Base64 编码后的字符串
     */
    public static String base64Encode2String(final byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
    public static byte[] base64Decode(final String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     *
     * @param input 要解码的字符串
     * @return Base64 解码后的字符串
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL 安全编码
     * <p>将 Base64 中的 URL 非法字符�?,/=转为其他字符, 见 RFC3548</p>
     *
     * @param input 要 Base64URL 安全编码的字符串
     * @return Base64URL 安全编码后的字符串
     */
    public static byte[] base64UrlSafeEncode(final String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    //</editor-fold>
    //<editor-fold desc="Html">

    /**
     * Html 编码
     *
     * @param input 要 Html 编码的字符串
     * @return Html 编码后的字符串
     */
    public static String htmlEncode(final CharSequence input) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Html 解码
     *
     * @param input 待解码的字符串
     * @return Html 解码后的字符串
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }

    //</editor-fold>

}
