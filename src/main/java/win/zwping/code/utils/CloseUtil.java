package win.zwping.code.utils;

import java.io.Closeable;
import java.io.IOException;

import win.zwping.code.basic.IUtil;

/**
 * <p>describe：IO关闭 工具类
 * <p>    note：
 * <p>    note：closeIo / closeIOQuietly
 * <p>    note：
 * <p> @author：zwp on 2018/3/9 0009 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class CloseUtil implements IUtil.INativeUtil {

    private CloseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Close the io stream.
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Close the io stream quietly.
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}