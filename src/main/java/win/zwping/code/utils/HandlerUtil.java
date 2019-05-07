package win.zwping.code.utils;

import android.os.Handler;
import android.os.Looper;

import win.zwping.code.basic.IUtil;

/**
 * <p>describe：Handler 工具类
 * <p>    note：
 * <p>    note：runOnUiThread / runOnUiThreadDelay / removeRunnable
 * <p>    note：
 * <p> @author：zwp on 2018/3/9 0009 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class HandlerUtil implements IUtil.INativeUtil {

    private HandlerUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 所有的handler操作结果均放置主线程中处理(Looper.getMainLooper())
     */
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * Run on ui thread
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    /**
     * Run on ui thread delay
     *
     * @param runnable
     * @param delayMillis
     */
    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * Remove runnable
     *
     * @param runnable
     */
    public static void removeRunnable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }
}
