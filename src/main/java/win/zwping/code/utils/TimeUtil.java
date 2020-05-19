package win.zwping.code.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import win.zwping.code.basic.IUtil;
import win.zwping.code.constant.TimeConstants;

/**
 * <p>describe：时间 相关工具类
 * <p>    note：
 * <p>    note：millis2String / string2Millis / string2Date / date2String / millis2Date / date2Millis
 * <p>    note：getNowMills / getNowString / getNowDate /  getNow
 * <p>    note：geTimeSpan / getFitTimeSpan / getFitTimeSpanByNow / getFriendlyTimeSpanByNow
 * <p>    note：getMillis / getString / getDate / getDateByNow
 * <p>    note：isToday / isLeapYear
 * <p>    note：getChineseWeek / getUSWeek / getWeekIndex / getWeekOfMonth / getWeekOfYear
 * <p>    note：getChineseZodiac / getZodiac
 * <p>    note：
 * <p> @author：zwp on 2018/3/16 0016 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class TimeUtil implements IUtil.INativeUtil {

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TimeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //<editor-fold desc="时间戳 / 时间串 / Date 转换">

    /**
     * 时间戳转换为时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis The milliseconds.
     * @return the formatted time string
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 时间戳转换为时间字符串
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 时间字符串 转换为 时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the milliseconds
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 时间字符串 转换为 时间戳
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the milliseconds
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 时间字符串 转换为 Date类型
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    /**
     * 时间字符串 转换为 Date类型
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * date类型 转换为 时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param date The date.
     * @return the formatted time string
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    /**
     * date类型 转换为 时间字符串
     *
     * @param date   The date.
     * @param format The format.
     * @return the formatted time string
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * Date to the milliseconds.
     *
     * @param date The date.
     * @return the milliseconds
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 时间戳 转换为 Date类型
     *
     * @param millis The milliseconds.
     * @return the date
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    //</editor-fold>
    //<editor-fold desc="当前时间">

    /**
     * 获取当前时间戳
     *
     * @return the current time in milliseconds
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @return the current formatted time string
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * 获取当前时间字符串
     *
     * @param format The format.
     * @return the current formatted time string
     */
    public static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 获取当前时间Date格式
     *
     * @return the current date
     */
    public static Date getNowDate() {
        return new Date();
    }

    public static int getNowYearInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("yyyy")));
    }
    public static int getNowMonthInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("MM")));
    }
    public static int getNowDayInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("dd")));
    }
    public static int getNowHourInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("HH")));
    }
    public static int getNowMinuteInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("mm")));
    }
    public static int getNowSecondInteger() {
        return ConversionUtil.covInteger(getNowString(new SimpleDateFormat("ss")));
    }

    //</editor-fold>
    //<editor-fold desc="友好时间差 - 返回时间戳 / 时间串">

    /**
     * 获取时间差，返回时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time1 The first formatted time string.
     * @param time2 The second formatted time string.
     * @param unit  The unit of time span.
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return the time span, in unit
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   @TimeConstants final int unit) {
        return getTimeSpan(time1, time2, DEFAULT_FORMAT, unit);
    }

    /**
     * 获取时间差，返回时间戳
     *
     * @param time1  The first formatted time string.
     * @param time2  The second formatted time string.
     * @param format The format.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return the time span, in unit
     */
    public static long getTimeSpan(final String time1,
                                   final String time2,
                                   final DateFormat format,
                                   @TimeConstants final int unit) {
        return millis2TimeSpan(
                Math.abs(string2Millis(time1, format) - string2Millis(time2, format)), unit
        );
    }

    /**
     * 获取时间差，返回时间戳
     *
     * @param date1 The first date.
     * @param date2 The second date.
     * @param unit  The unit of time span.
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}</li>
     *              <li>{@link TimeConstants#SEC }</li>
     *              <li>{@link TimeConstants#MIN }</li>
     *              <li>{@link TimeConstants#HOUR}</li>
     *              <li>{@link TimeConstants#DAY }</li>
     *              </ul>
     * @return the time span, in unit
     */
    public static long getTimeSpan(final Date date1,
                                   final Date date2,
                                   @TimeConstants final int unit) {
        return millis2TimeSpan(Math.abs(date2Millis(date1) - date2Millis(date2)), unit);
    }

    /**
     * 获取时间差，返回时间戳
     *
     * @param millis1 The first milliseconds.
     * @param millis2 The second milliseconds.
     * @param unit    The unit of time span.
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}</li>
     *                <li>{@link TimeConstants#SEC }</li>
     *                <li>{@link TimeConstants#MIN }</li>
     *                <li>{@link TimeConstants#HOUR}</li>
     *                <li>{@link TimeConstants#DAY }</li>
     *                </ul>
     * @return the time span, in unit
     */
    public static long getTimeSpan(final long millis1,
                                   final long millis2,
                                   @TimeConstants final int unit) {
        return millis2TimeSpan(Math.abs(millis1 - millis2), unit);
    }

    /**
     * 获取时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time1     The first formatted time string.
     * @param time2     The second formatted time string.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        final int precision) {
        long delta = string2Millis(time1, DEFAULT_FORMAT) - string2Millis(time2, DEFAULT_FORMAT);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 获取时间差，返回时间字符串
     *
     * @param time1     The first formatted time string.
     * @param time2     The second formatted time string.
     * @param format    The format.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final String time1,
                                        final String time2,
                                        final DateFormat format,
                                        final int precision) {
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 获取时间差，返回时间字符串
     *
     * @param date1     The first date.
     * @param date2     The second date.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final Date date1, final Date date2, final int precision) {
        return millis2FitTimeSpan(Math.abs(date2Millis(date1) - date2Millis(date2)), precision);
    }

    /**
     * 获取时间差，返回时间字符串
     *
     * @param millis1   The first milliseconds.
     * @param millis2   The second milliseconds.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return the fit time span
     */
    public static String getFitTimeSpan(final long millis1,
                                        final long millis2,
                                        final int precision) {
        return millis2FitTimeSpan(Math.abs(millis1 - millis2), precision);
    }

    /**
     * 获取友好的时间差，返回时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @param unit The unit of time span.
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final String time, @TimeConstants final int unit) {
        return getTimeSpan(getNowString(), time, DEFAULT_FORMAT, unit);
    }

    /**
     * 获取友好的时间差，返回时间戳
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final String time,
                                        final DateFormat format,
                                        @TimeConstants final int unit) {
        return getTimeSpan(getNowString(format), time, format, unit);
    }

    /**
     * 获取友好的时间差，返回时间戳
     *
     * @param date The date.
     * @param unit The unit of time span.
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}</li>
     *             <li>{@link TimeConstants#SEC }</li>
     *             <li>{@link TimeConstants#MIN }</li>
     *             <li>{@link TimeConstants#HOUR}</li>
     *             <li>{@link TimeConstants#DAY }</li>
     *             </ul>
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants final int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * 获取友好的时间差，返回时间戳
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
     * @return the time span by now, in unit
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants final int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * 获取友好的时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time      The formatted time string.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final String time, final int precision) {
        return getFitTimeSpan(getNowString(), time, DEFAULT_FORMAT, precision);
    }

    /**
     * 获取友好的时间差，返回时间字符串
     *
     * @param time      The formatted time string.
     * @param format    The format.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final String time,
                                             final DateFormat format,
                                             final int precision) {
        return getFitTimeSpan(getNowString(format), time, format, precision);
    }

    /**
     * 获取友好的时间差，返回时间字符串
     *
     * @param date      The date.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(getNowDate(), date, precision);
    }

    /**
     * 获取友好的时间差，返回时间字符串
     *
     * @param millis    The milliseconds.
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return the fit time span by now
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * 获取与当前时间的时间差，返回友好的时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_FORMAT);
    }

    /**
     * 获取与当前时间的时间差，返回友好的时间字符串
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time, final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * 获取与当前时间的时间差，返回友好的时间字符串
     *
     * @param date The date.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 获取与当前时间的时间差，返回友好的时间字符串
     *
     * @param millis The milliseconds.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            return String.format("%tc", millis);
        if (span < 1000) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    //</editor-fold>
    //<editor-fold desc="时间差 - 返回 时间戳/时间串/Date">

    /**
     * 获取两个时间的时间差，返回时间戳
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the milliseconds differ time span
     */
    public static long getMillis(final long millis,
                                 final long timeSpan,
                                 @TimeConstants final int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间戳
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the milliseconds differ time span
     */
    public static long getMillis(final String time,
                                 final long timeSpan,
                                 @TimeConstants final int unit) {
        return getMillis(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间戳
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the milliseconds differ time span.
     */
    public static long getMillis(final String time,
                                 final DateFormat format,
                                 final long timeSpan,
                                 @TimeConstants final int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间戳
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the milliseconds differ time span.
     */
    public static long getMillis(final Date date,
                                 final long timeSpan,
                                 @TimeConstants final int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final long millis,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return getString(millis, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     *
     * @param millis   The milliseconds.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final long millis,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final String time,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return getString(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final String time,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final Date date,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return getString(date, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回时间字符串
     *
     * @param date     The date.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span
     */
    public static String getString(final Date date,
                                   final DateFormat format,
                                   final long timeSpan,
                                   @TimeConstants final int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    /**
     * 获取两个时间的时间差，返回Date格式
     *
     * @param millis   The milliseconds.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final long millis,
                               final long timeSpan,
                               @TimeConstants final int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取两个时间的时间差，返回Date格式
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time     The formatted time string.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final String time,
                               final long timeSpan,
                               @TimeConstants final int unit) {
        return getDate(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    /**
     * 获取两个时间的时间差，返回Date格式
     *
     * @param time     The formatted time string.
     * @param format   The format.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final String time,
                               final DateFormat format,
                               final long timeSpan,
                               @TimeConstants final int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取两个时间的时间差，返回Date格式
     *
     * @param date     The date.
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the date differ time span
     */
    public static Date getDate(final Date date,
                               final long timeSpan,
                               @TimeConstants final int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    /**
     * 获取与当前时间的时间差，返回时间戳
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
     * @return the milliseconds differ time span by now
     */
    public static long getMillisByNow(final long timeSpan, @TimeConstants final int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    /**
     * 获取与当前时间的时间差，返回时间字符串
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
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
     * @return the formatted time string differ time span by now
     */
    public static String getStringByNow(final long timeSpan, @TimeConstants final int unit) {
        return getStringByNow(timeSpan, DEFAULT_FORMAT, unit);
    }

    /**
     * 获取与当前时间的时间差，返回时间字符串
     *
     * @param timeSpan The time span.
     * @param format   The format.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return the formatted time string differ time span by now
     */
    public static String getStringByNow(final long timeSpan,
                                        final DateFormat format,
                                        @TimeConstants final int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    /**
     * 获取与当前时间的时间差，返回Date格式
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
     * @return the date differ time span by now
     */
    public static Date getDateByNow(final long timeSpan, @TimeConstants final int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    //</editor-fold>
    //<editor-fold desc="是否是今天 / 闰年判断">

    /**
     * 是否是今天
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time) {
        return isToday(string2Millis(time, DEFAULT_FORMAT));
    }

    /**
     * 是否是今天
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final String time, final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 是否是今天
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 是否是今天
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    /**
     * 是否是闰年
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time) {
        return isLeapYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 是否是闰年
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final String time, final DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    /**
     * 是否是闰年
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 是否是闰年
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 是否是闰年
     *
     * @param year The year.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    //</editor-fold>
    //<editor-fold desc="中文星期 / 美式星期 / 星期数 / 本月周数 / 本年周数">

    /**
     * 获取中文格式的星期
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final String time) {
        return getChineseWeek(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取中文格式的星期
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final String time, final DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    /**
     * 获取中文格式的星期
     *
     * @param date The date.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    /**
     * 获取中文格式的星期
     *
     * @param millis The milliseconds.
     * @return the day of week in Chinese
     */
    public static String getChineseWeek(final long millis) {
        return getChineseWeek(new Date(millis));
    }

    /**
     * 获取美式星期
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the day of week in US
     */
    public static String getUSWeek(final String time) {
        return getUSWeek(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取美式星期
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the day of week in US
     */
    public static String getUSWeek(final String time, final DateFormat format) {
        return getUSWeek(string2Date(time, format));
    }

    /**
     * 获取美式星期
     *
     * @param date The date.
     * @return the day of week in US
     */
    public static String getUSWeek(final Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    /**
     * 获取美式星期
     *
     * @param millis The milliseconds.
     * @return the day of week in US
     */
    public static String getUSWeek(final long millis) {
        return getUSWeek(new Date(millis));
    }

    /**
     * 返回指定时间的星期数
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final String time) {
        return getWeekIndex(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 返回指定时间的星期数
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final String time, final DateFormat format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * 返回指定时间的星期数
     *
     * @param date The date.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 返回指定时间的星期数
     *
     * @param millis The milliseconds.
     * @return the number for indicating the day of the week
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * 获取指定时间在本月的周数
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the week number within the current month
     */
    public static int getWeekOfMonth(final String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取指定时间在本月的周数
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the week number within the current month
     */
    public static int getWeekOfMonth(final String time, final DateFormat format) {
        return getWeekOfMonth(string2Date(time, format));
    }

    /**
     * 获取指定时间在本月的周数
     *
     * @param date The date.
     * @return the number for indicating the week number within the current month
     */
    public static int getWeekOfMonth(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取指定时间在本月的周数
     *
     * @param millis The milliseconds.
     * @return the number for indicating the week number within the current month
     */
    public static int getWeekOfMonth(final long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * 获取指定时间在本年的周数
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the number for indicating the week number within the current year
     */
    public static int getWeekOfYear(final String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取指定时间在本年的周数
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the number for indicating the week number within the current year
     */
    public static int getWeekOfYear(final String time, final DateFormat format) {
        return getWeekOfYear(string2Date(time, format));
    }

    /**
     * 获取指定时间在本年的周数
     *
     * @param date The date.
     * @return the number for indicating the week number within the current year
     */
    public static int getWeekOfYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定时间在本年的周数
     *
     * @param millis The milliseconds.
     * @return the number for indicating the week number within the current year
     */
    public static int getWeekOfYear(final long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

    //</editor-fold>
    //<editor-fold desc="生肖 / 星座">

    private static final String[] CHINESE_ZODIAC =
            {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 获取指定时间的12生肖
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final String time) {
        return getChineseZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取指定时间的12生肖
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final String time, final DateFormat format) {
        return getChineseZodiac(string2Date(time, format));
    }

    /**
     * 获取指定时间的12生肖
     *
     * @param date The date.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 获取指定时间的12生肖
     *
     * @param millis The milliseconds.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * 获取指定时间的12生肖
     *
     * @param year The year.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    };

    /**
     * 获取指定时间的星座
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @param time The formatted time string.
     * @return the zodiac
     */
    public static String getZodiac(final String time) {
        return getZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    /**
     * 获取指定时间的星座
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the zodiac
     */
    public static String getZodiac(final String time, final DateFormat format) {
        return getZodiac(string2Date(time, format));
    }

    /**
     * 获取指定时间的星座
     *
     * @param date The date.
     * @return the zodiac
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 获取指定时间的星座
     *
     * @param millis The milliseconds.
     * @return the zodiac
     */
    public static String getZodiac(final long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * 获取指定时间的星座
     *
     * @param month The month.
     * @param day   The day.
     * @return the zodiac
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

    //</editor-fold>
    //<editor-fold desc="私有方法">

    private static long timeSpan2Millis(final long timeSpan, @TimeConstants final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants final int unit) {
        return millis / unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (millis < 0 || precision <= 0) return null;
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) return 0 + units[precision - 1];
        StringBuilder sb = new StringBuilder();
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
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
}
