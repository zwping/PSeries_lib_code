package win.zwping.code.utils;

import android.os.Environment;

import java.io.File;

import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;


/**
 * <p>describe：清除 相关工具类
 * <p>    note：
 * <p>    note：cleanInCache / cleanInFiles / cleanInDbs / cleanInDbByName / cleanInSp
 * <p>    note：cleanExCache / cleanCustomDir
 * <p>    note：
 * <p> @author：zwp on 2018/3/21 0021 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class CleanUtil implements IUtil.INativeUtil {

    private CleanUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 清除内部缓存
     * <p>directory: /data/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInCache() {
        return FileUtil.deleteFilesInDir(Util.getApp().getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>directory: /data/data/package/files</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInFiles() {
        return FileUtil.deleteFilesInDir(Util.getApp().getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>directory: /data/data/package/databases</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInDbs() {
        return FileUtil.deleteFilesInDir(new File(Util.getApp().getFilesDir().getParent(), "databases"));
    }

    /**
     * 清除内部指定数据库
     * <p>directory: /data/data/package/databases/dbName</p>
     *
     * @param dbName The name of database.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInDbByName(final String dbName) {
        return Util.getApp().deleteDatabase(dbName);
    }

    /**
     * 清除内部 SP
     * <p>directory: /data/data/package/shared_prefs</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanInSp() {
        return FileUtil.deleteFilesInDir(new File(Util.getApp().getFilesDir().getParent(), "shared_prefs"));
    }

    /**
     * 清除外部缓存
     * <p>directory: /storage/emulated/0/android/data/package/cache</p>
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanExCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && FileUtil.deleteFilesInDir(Util.getApp().getExternalCacheDir());
    }

    /**
     * 清除指定目录文件
     *
     * @param dirPath The path of directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanCustomDir(final String dirPath) {
        return FileUtil.deleteFilesInDir(dirPath);
    }

    /**
     * 清除指定目录文件
     *
     * @param dir The directory.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean cleanCustomDir(final File dir) {
        return FileUtil.deleteFilesInDir(dir);
    }
}
