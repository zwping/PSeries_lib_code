package win.zwping.code.utils;

import android.content.Context;
import android.os.Vibrator;

import androidx.annotation.RequiresPermission;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

import static android.Manifest.permission.VIBRATE;


/**
 * <p>describe：震动相关
 * <p>    note：
 * <p>    note：vibrate / cancel
 * <p>    note：
 * <p> @author：zwp on 2019/2/26 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class VibrateUtil implements IUtil.INativeUtil {

    private static Vibrator vibrator;

    private VibrateUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param milliseconds The number of milliseconds to vibrate.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(milliseconds);
    }

    /**
     * Vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     *
     * @param pattern An array of longs of times for which to turn the vibrator on or off.
     * @param repeat  The index into pattern at which to repeat, or -1 if you don't want to repeat.
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * Cancel vibrate.
     * <p>Must hold {@code <uses-permission android:name="android.permission.VIBRATE" />}</p>
     */
    @RequiresPermission(VIBRATE)
    public static void cancel() {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) return;
        vibrator.cancel();
    }

    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) Util.getApp().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }
}
