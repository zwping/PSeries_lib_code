package win.zwping.code.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import androidx.core.app.ActivityOptionsCompat;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;
import win.zwping.code.constant.MemoryConstants;
import win.zwping.code.constant.TimeConstants;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：转换相关
 * <p>    note：
 * <p>    note：basic data cov... / sp dp px cov... / code cov...
 * <p>    note：memorySize2Byte / byteSize2MemorySize / byteSize2FitMemorySize
 * <p>    note：timeSpan2Millis / millis2TimeSpan  / millis2FitTimeSpan
 * <p>    note：inputStream outputStream bytes string cov...
 * <p>    note：bytes bitmap drawable view cov...
 * <p>    note：getDistance
 * <p>    note：getTransitionOj
 * <p>    note：
 * <p>  author：zwp on 2017/9/21 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class ConversionUtil implements IUtil.INativeUtil {

    //<editor-fold desc="八大基本数据类型转换">

    public static CharSequence covCharSequence(Object o) {
        if (isEmpty(o)) return null;
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String covString(Object o) {
        if (isEmpty(o)) return null;
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int covInteger(String s) {
        if (isEmpty(s)) return 0;
        //去除美观度 千分位
        if (s.contains(",")) s = s.replaceAll(",", "");
        try {
            return (int) Math.round(Double.valueOf(s));
        } catch (NumberFormatException e) { //非标准的字符长度也有可能报错
            e.printStackTrace();
            return 0;
        }
    }

    public static long covLong(String s) {
        if (isEmpty(s)) return 0;
        //去除美观度 千分位
        if (s.contains(",")) s = s.replaceAll(",", "");
        try {
            return (long) Math.round(Double.valueOf(s)); //支持Double
        } catch (NumberFormatException e) { //非标准的字符长度也有可能报错
            e.printStackTrace();
            return 0;
        }
    }

    public static double covDouble(String s) {
        if (isEmpty(s)) return 0;
        //去除美观度 千分位
        if (s.contains(",")) s = s.replaceAll(",", "");
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) { //非标准的字符长度也有可能报错
            e.printStackTrace();
            return 0;
        }
    }

    public static float covFloat(String s) {
        if (isEmpty(s)) return 0;
        //去除美观度 千分位
        if (s.contains(",")) s = s.replaceAll(",", "");
        try {
            return Float.valueOf(s);
        } catch (NumberFormatException e) { //非标准的字符长度也有可能报错
            e.printStackTrace();
            return 0;
        }
    }

    public static String covBigDecimal(String s) {
        if (isEmpty(s)) return null;
        //去除美观度 千分位
        if (s.contains(",")) s = s.replaceAll(",", "");
        try {
            DecimalFormat format = new DecimalFormat("#,##0.00");//不以科学计数法显示，并把结果用逗号隔开保留两位小数
            //BigDecimal bigDecimal = new BigDecimal(s);//不以科学计数法显示，正常显示保留两位小数
            return format.format(covDouble(s));
//            return covString(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold desc="DP PX SP相互转换">

    /**
     * Convert dp to px by the density of phone
     * <p> translation：通过手机密度将dip 转换 为px
     */
    public static int dp2px(float dp) {
        return (int) (dipToPx(dp) + 0.5f);
    }

    public static float dipToPx(float dp) {
        float scale = Util.getApp().getResources().getDisplayMetrics().density;
        return dp * scale;
    }

    public static int px2dp(float px) {
        return (int) (pxToDip(px) + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        return (int) (pxToDip(context, px) + 0.5f);
    }

    public static float pxToDip(float px) {
        float scale = Util.getApp().getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static float pxToDip(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static int px2sp(float px) {
        return (int) (pxToSp(px) + 0.5f);
    }

    public static float pxToSp(float px) {
        float fontScale = Util.getApp().getResources().getDisplayMetrics().scaledDensity;
        return px / fontScale;
    }

    public static int sp2px(float sp) {
        return (int) (spToPx(sp) + 0.5f);
    }

    private static float spToPx(float sp) {
        float fontScale = Util.getApp().getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale;
    }

    /*** xml预览 ***/

    public static int dp2px(Context context, float dp) {
        return (int) (dipToPx(context, dp) + 0.5f);
    }

    public static float dipToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }

    public static int sp2px(Context context, float sp) {
        return (int) (spToPx(context, sp) + 0.5f);
    }

    private static float spToPx(Context context, float sp) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * fontScale;
    }
    //</editor-fold>

    //<editor-fold desc="编码转换">

    /**
     * 将unicode的汉字码转换成utf-8格式的汉字
     *
     * @param unicodeStr
     * @return
     */
    public static String unicodeToString(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    //</editor-fold>
    //<editor-fold desc="字节 比特... 相关转换">

    /**
     * B to b (1 Byte = 8 Bits)
     *
     * @param bytes The bytes.
     * @return bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * b to B (8 Bits = 1 Byte)
     *
     * @param bits The bits.
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // add "0" until length to 8 times
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * Bytes to chars.
     *
     * @param bytes The bytes.
     * @return chars
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * Chars to bytes.
     *
     * @param chars The chars.
     * @return bytes
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
    }

    /**
     * B to 16进制
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns "00A8"</p>
     *
     * @param bytes The bytes.
     * @return hex string
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 16进制 to B
     * <p>e.g. hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }</p>
     *
     * @param hexString The hex string.
     * @return the bytes
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Int(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //</editor-fold>
    //<editor-fold desc="内存 转换相关">

    /**
     * 内存单位转换
     *
     * @param memorySize Size of memory.
     * @param unit       The unit of memory size.
     *                   <ul>
     *                   <li>{@link MemoryConstants#BYTE}</li>
     *                   <li>{@link MemoryConstants#KB}</li>
     *                   <li>{@link MemoryConstants#MB}</li>
     *                   <li>{@link MemoryConstants#GB}</li>
     *                   </ul>
     * @return size of byte
     */
    public static long memorySize2Byte(final long memorySize, @MemoryConstants final int unit) {
        if (memorySize < 0) return -1;
        return memorySize * unit;
    }

    /**
     * B(字节) 转换为指定单位的内存大小
     *
     * @param byteSize Size of byte.
     * @param unit     The unit of memory size.
     *                 <ul>
     *                 <li>{@link MemoryConstants#BYTE}</li>
     *                 <li>{@link MemoryConstants#KB}</li>
     *                 <li>{@link MemoryConstants#MB}</li>
     *                 <li>{@link MemoryConstants#GB}</li>
     *                 </ul>
     * @return size of memory in unit
     */
    public static double byte2MemorySize(final long byteSize,
                                         @MemoryConstants final int unit) {
        if (byteSize < 0) return -1;
        return (double) byteSize / unit;
    }

    /**
     * B 转换为合适的内存单位
     * <p>to three decimal places</p>
     *
     * @param byteSize Size of byte.
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        if (byteSize < 0) {
            return "shouldn't be less than zero!";
        } else if (byteSize < MemoryConstants.KB) {
            return String.format("%.2fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB) {
            return String.format("%.2fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB) {
            return String.format("%.2fMB", (double) byteSize / MemoryConstants.MB);
        } else {
            return String.format("%.2fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

    //</editor-fold>
    //<editor-fold desc="时间戳 相关">

    /**
     * 指定单位的时间转换为毫秒
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return milliseconds
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants final int unit) {
        return timeSpan * unit;
    }

    /**
     * 毫秒转换为指定单位的时间
     *
     * @param millis The milliseconds.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return time span in unit
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants final int unit) {
        return millis / unit;
    }

    /**
     * 毫秒时间戳转合适时间长度
     *
     * @param millis    The milliseconds.
     *                  <p>millis &lt;= 0, return null</p>
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision = 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return fit time span
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    //</editor-fold>
    //<editor-fold desc="Stream 转换相关">

    /**
     * 输入流 转换为 输出流
     *
     * @param is The input stream.
     * @return output stream
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[MemoryConstants.KB];
            int len;
            while ((len = is.read(b, 0, MemoryConstants.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(is);
        }
    }

    /**
     * 输出流 转换为 输入流
     *
     * @param out The output stream.
     * @return input stream
     */
    public ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * 输入流 转换为 字节数组
     *
     * @param is The input stream.
     * @return bytes
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * 字节数组 转换为 输入流
     *
     * @param bytes The bytes.
     * @return input stream
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 输出流 转换为 字节数组
     *
     * @param out The output stream.
     * @return bytes
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * 字节数组 转换为 输出流
     *
     * @param bytes The bytes.
     * @return output stream
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtil.closeIO(os);
        }
    }

    /**
     * 输入流 转换为 字符串
     *
     * @param is          The input stream.
     * @param charsetName The name of charset.
     * @return string
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 转换为 输入流
     *
     * @param string      The string.
     * @param charsetName The name of charset.
     * @return input stream
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 输出流 转换为 字符串
     *
     * @param out         The output stream.
     * @param charsetName The name of charset.
     * @return string
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串 转换为 输出流
     *
     * @param string      The string.
     * @param charsetName The name of charset.
     * @return output stream
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>
    //<editor-fold desc="Bitmap Drawable View 转换相关">

    /**
     * Bitmap 转换为 字节数组
     *
     * @param bitmap The bitmap.
     * @param format The format of bitmap.
     * @return bytes
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 字节数组 转换为 Bitmap
     *
     * @param bytes The bytes.
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable 转换为 Bitmap
     *
     * @param drawable The drawable.
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap 转换为 Drawable
     *
     * @param bitmap The bitmap.
     * @return drawable
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Util.getApp().getResources(), bitmap);
    }

    /**
     * Drawable 转换为 字节数组
     *
     * @param drawable The drawable.
     * @param format   The format of bitmap.
     * @return bytes
     */
    public static byte[] drawable2Bytes(final Drawable drawable,
                                        final Bitmap.CompressFormat format) {
        return drawable == null ? null : bitmap2Bytes(drawable2Bitmap(drawable), format);
    }

    /**
     * 字节数组 转换为 drawable.
     *
     * @param bytes The bytes.
     * @return drawable
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return bytes == null ? null : bitmap2Drawable(bytes2Bitmap(bytes));
    }

    /**
     * View to bitmap.
     *
     * @param view The view.
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        Bitmap ret =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /*** View转换为Bitmap ***/
    public static Bitmap viewToBitmap(View view) {
        if (null == view) return null;
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache(true);
    }

    //</editor-fold>
    //<editor-fold desc="经纬度距离计算">

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离(单位为公里)
     *
     * @return
     */
    public double getDistance1(double lng1, double lat1, double lng2, double lat2) {
        return Math.round(getDistance(lng1, lat1, lng2, lat2) / 100d) / 10d;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(a / 2), 2)
                                + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)
                )
        );
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    //把经纬度转为度（°）
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    //地球平均半径
    private static final double EARTH_RADIUS = 6378137;
    //</editor-fold>
    //<editor-fold desc="共享元素">

    /*** 获取共享元素对象 --- 方法名过长 ***/
    public static ActivityOptionsCompat getTransitionOj(Activity ac, View view, String transitionName) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(ac, view, transitionName);
    }

    //</editor-fold>
    //<editor-fold desc="私有方法">

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    //</editor-fold>
}
