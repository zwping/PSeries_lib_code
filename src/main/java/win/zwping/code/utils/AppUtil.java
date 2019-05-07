package win.zwping.code.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

/**
 * <p>describe：App 相关工具类
 * <p>    note：
 * <p>    note：registerAppStatusChangedListener
 * <p>    note：installApp / installAppSilent / uninstallApp / uninstallAppSilent
 * <p>    note：isAppInstalled / isAppRoot / isAppSystem / isAppForeground / isAppDebug
 * <p>    note：relaunchApp / launchApp / launchAppDetailsSettings / exitApp
 * <p>    note：getAppVersionName / getAppVersionCode / getAppIcon / getAppPackageName / getAppName / getAppPath /
 * <p>    note：getAppSignature / getAppSignatureSHA1
 * <p>    note：getAppInfo / getAppsInfo
 * <p>    note：
 * <p> @author：zwp on 2018/3/21 0021 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class AppUtil  implements IUtil.INativeUtil{

    private AppUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    //<editor-fold desc="App状态监听">

    /**
     * Register the status of application changed listener.
     *
     * @param obj      The object.
     * @param listener The status of application changed listener
     */
    public static void registerAppStatusChangedListener(@NonNull final Object obj, @NonNull final Util.OnAppStatusChangedListener listener) {
        Util.getActivityLifecycle().addOnAppStatusChangedListener(obj, listener);
    }

    /**
     * Unregister the status of application changed listener.
     *
     * @param obj The object.
     */
    public static void unregisterAppStatusChangedListener(@NonNull final Object obj) {
        Util.getActivityLifecycle().removeOnAppStatusChangedListener(obj);
    }

    //</editor-fold>
    //<editor-fold desc="APP的操作">

    /**
     * 安装App(支持8.0)
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     */
    public static void installApp(final String filePath) {
        installApp(getFileByPath(filePath));
    }

    /**
     * 安装App(支持8.0)
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     */
    public static void installApp(final File file) {
        if (!isFileExists(file)) return;
        Util.getApp().startActivity(IntentUtil.getInstallAppIntent(file, true));
    }

    /**
     * 安装App(支持8.0)
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    The activity.
     * @param filePath    The path of file.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void installApp(final Activity activity,
                                  final String filePath,
                                  final int requestCode) {
        installApp(activity, getFileByPath(filePath), requestCode);
    }

    /**
     * 安装App(支持8.0)
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param activity    The activity.
     * @param file        The file.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void installApp(final Activity activity,
                                  final File file,
                                  final int requestCode) {
        if (!isFileExists(file)) return;
        activity.startActivityForResult(IntentUtil.getInstallAppIntent(file), requestCode);
    }

    /**
     * 静默安装App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final String filePath) {
        return installAppSilent(getFileByPath(filePath), null);
    }

    /**
     * 静默安装App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final File file) {
        return installAppSilent(file, null);
    }


    /**
     * 静默安装App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @param params   The params of installation.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final String filePath, final String params) {
        return installAppSilent(getFileByPath(filePath), params);
    }

    /**
     * 静默安装App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param file   The file.
     * @param params The params of installation.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean installAppSilent(final File file, final String params) {
        if (!isFileExists(file)) return false;
        boolean isRoot = isDeviceRooted();
        String filePath = file.getAbsolutePath();
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install " + (params == null ? "" : params + " ") + filePath;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, isRoot);
        if (commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            LogUtil.i("installAppSilent successMsg: " + commandResult.successMsg + ", errorMsg: " + commandResult.errorMsg);
            return false;
        }
    }

    /**
     * 卸载App
     *
     * @param packageName The name of the package.
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) return;
        Util.getApp().startActivity(IntentUtil.getUninstallAppIntent(packageName, true));
    }

    /**
     * 卸载App
     *
     * @param activity    The activity.
     * @param packageName The name of the package.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void uninstallApp(final Activity activity,
                                    final String packageName,
                                    final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtil.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 静默卸载App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false);
    }

    /**
     * 静默卸载App
     * <p>Without root permission must hold
     * {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param packageName The name of the package.
     * @param isKeepData  Is keep the data.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        if (isSpace(packageName)) return false;
        boolean isRoot = isDeviceRooted();
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall "
                + (isKeepData ? "-k " : "")
                + packageName;
        ShellUtil.CommandResult commandResult = ShellUtil.execCmd(command, isRoot, true);
        if (commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            LogUtil.i("uninstallAppSilent successMsg: " + commandResult.successMsg +
                    ", errorMsg: " + commandResult.errorMsg);
            return false;
        }
    }

    /**
     * App是否存在
     *
     * @param action   The Intent action, such as ACTION_VIEW.
     * @param category The desired category.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String action, @NonNull final String category) {
        Intent intent = new Intent(action);
        intent.addCategory(category);
        PackageManager pm = Util.getApp().getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    }

    /**
     * App是否存在
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String packageName) {
        return !isSpace(packageName) && IntentUtil.getLaunchAppIntent(packageName) != null;
    }

    /**
     * App是否有Root权限
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRoot() {
        ShellUtil.CommandResult result = ShellUtil.execCmd("echo root", true);
        if (result.result == 0) return true;
        if (result.errorMsg != null) {
            LogUtil.i("isAppRoot() called" + result.errorMsg);
        }
        return false;
    }

    /**
     * App是否是系统应用
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem() {
        return isAppSystem(Util.getApp().getPackageName());
    }

    /**
     * App是否是系统应用
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取APP是否Debug状态
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(Util.getApp().getPackageName());
    }

    /**
     * 获取APP是否Debug状态
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * App是否是否处在前台
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        ActivityManager am =
                (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Util.getApp().getPackageName());
            }
        }
        return false;
    }

    /**
     * App是否是否处在前台
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground(@NonNull final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtil.getForegroundProcessName());
    }


    /**
     * 重启APP
     */
    public static void relaunchApp() {
        PackageManager packageManager = Util.getApp().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(Util.getApp().getPackageName());
        if (intent == null) return;
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        Util.getApp().startActivity(mainIntent);
        System.exit(0);
    }

    /**
     * 打开App
     *
     * @param packageName The name of the package.
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        Util.getApp().startActivity(IntentUtil.getLaunchAppIntent(packageName, true));
    }

    /**
     * 打开App
     *
     * @param activity    The activity.
     * @param packageName The name of the package.
     * @param requestCode If &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(IntentUtil.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 打开App具体设置
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(Util.getApp().getPackageName());
    }

    /**
     * 打开App具体设置
     *
     * @param packageName The name of the package.
     */
    public static void launchAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return;
        Util.getApp().startActivity(
                IntentUtil.getLaunchAppDetailsSettingsIntent(packageName, true)
        );
    }

    /**
     * 关闭App
     */
    public static void exitApp() {
        List<Activity> activityList = Util.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
        }
        System.exit(0);
    }

    //</editor-fold>
    //<editor-fold desc="获取App基本信息">


    /**
     * 获取App版本名
     *
     * @return the application's version name
     */
    public static String getAppVersionName() {
        return getAppVersionName(Util.getApp().getPackageName());
    }

    /**
     * 获取App版本名
     *
     * @param packageName The name of the package.
     * @return the application's version name
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取App版本号
     *
     * @return the application's version code
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Util.getApp().getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName The name of the package.
     * @return the application's version code
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取AppIcon
     *
     * @return the application's icon
     */
    public static Drawable getAppIcon() {
        return getAppIcon(Util.getApp().getPackageName());
    }

    /**
     * 获取AppIcon
     *
     * @param packageName The name of the package.
     * @return the application's icon
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App包名
     *
     * @return the application's package name
     */
    public static String getAppPackageName() {
        return Util.getApp().getPackageName();
    }

    /**
     * 获取App名称
     *
     * @return the application's name
     */
    public static String getAppName() {
        return getAppName(Util.getApp().getPackageName());
    }

    /**
     * 获取App名称
     *
     * @param packageName The name of the package.
     * @return the application's name
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App路径
     *
     * @return the application's path
     */
    public static String getAppPath() {
        return getAppPath(Util.getApp().getPackageName());
    }

    /**
     * 获取App路径
     *
     * @param packageName The name of the package.
     * @return the application's path
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App签名
     *
     * @return the application's signature
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(Util.getApp().getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param packageName The name of the package.
     * @return the application's signature
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App SHA1 值
     *
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Util.getApp().getPackageName());
    }

    /**
     * 获取App SHA1 值
     *
     * @param packageName The name of the package.
     * @return the application's signature for SHA1 value
     */
    public static String getAppSignatureSHA1(@NonNull final String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length <= 0) return null;
        return encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 获取App信息
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @return the application's information
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(Util.getApp().getPackageName());
    }

    /**
     * 获取App信息
     * <ul>
     * <li>name of package</li>
     * <li>icon</li>
     * <li>name</li>
     * <li>path of package</li>
     * <li>version name</li>
     * <li>version code</li>
     * <li>is system</li>
     * </ul>
     *
     * @param packageName The name of the package.
     * @return 当前应用的 AppInfo
     */
    public static AppInfo getAppInfo(final String packageName) {
        try {
            PackageManager pm = Util.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取手机内所有APP信息
     *
     * @return the applications' information
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = Util.getApp().getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

    //</editor-fold>
    //<editor-fold desc="私有方法">

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String encryptSHA1ToString(final byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    private static byte[] encryptSHA1(final byte[] data) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * The application's information.
     */
    public static class AppInfo {

        private String packageName;
        private String name;
        private Drawable icon;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(final Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(final boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(final String packageName) {
            this.packageName = packageName;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(final String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(final int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(final String versionName) {
            this.versionName = versionName;
        }

        public AppInfo(String packageName, String name, Drawable icon, String packagePath,
                       String versionName, int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() +
                    "\napp icon: " + getIcon() +
                    "\napp name: " + getName() +
                    "\napp path: " + getPackagePath() +
                    "\napp v name: " + getVersionName() +
                    "\napp v code: " + getVersionCode() +
                    "\nis system: " + isSystem();
        }
    }
    //</editor-fold>
}
