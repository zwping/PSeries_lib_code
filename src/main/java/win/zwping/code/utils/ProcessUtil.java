package win.zwping.code.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

import static android.Manifest.permission.KILL_BACKGROUND_PROCESSES;


/**
 * <p>describe：进程 工具类
 * <p>    note：
 * <p>    note：getForegroundProcessName / getAllBackgroundProcesses / killAllBackgroundProcesses / killBackgroundProcesses
 * <p>    note：
 * <p> @author：zwp on 2018/3/12 0012 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class ProcessUtil  implements IUtil.INativeUtil{

    private ProcessUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取前台线程包名
     * <p>Target APIs greater than 21 must hold
     * {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />}</p>
     *
     * @return the foreground process name
     */
    public static String getForegroundProcessName() {
        ActivityManager am =
                (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return null;
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance
                        == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = Util.getApp().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list =
                    pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            Log.i("ProcessUtil", list.toString());
            if (list.size() <= 0) {
                Log.i("ProcessUtil",
                        "getForegroundProcessName: noun of access to usage information.");
                return null;
            }
            try {// Access to usage information.
                ApplicationInfo info =
                        pm.getApplicationInfo(Util.getApp().getPackageName(), 0);
                AppOpsManager aom =
                        (AppOpsManager) Util.getApp().getSystemService(Context.APP_OPS_SERVICE);
                if (aom != null) {
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                            info.uid,
                            info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Util.getApp().startActivity(intent);
                    }
                    if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                            info.uid,
                            info.packageName) != AppOpsManager.MODE_ALLOWED) {
                        Log.i("ProcessUtil",
                                "getForegroundProcessName: refuse to device usage stats.");
                        return null;
                    }
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) Util.getApp()
                        .getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager
                            .queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                                    beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return null;
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null
                            || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取所有后台进程名
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @return all background processes
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> getAllBackgroundProcesses() {
        ActivityManager am =
                (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        if (info != null) {
            for (ActivityManager.RunningAppProcessInfo aInfo : info) {
                Collections.addAll(set, aInfo.pkgList);
            }
        }
        return set;
    }

    /**
     * 杀死所有的后台服务进程
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @return background processes were killed
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static Set<String> killAllBackgroundProcesses() {
        ActivityManager am =
                (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        Set<String> set = new HashSet<>();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            for (String pkg : aInfo.pkgList) {
                am.killBackgroundProcesses(pkg);
                set.add(pkg);
            }
        }
        info = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            for (String pkg : aInfo.pkgList) {
                set.remove(pkg);
            }
        }
        return set;
    }

    /**
     * 杀死后台服务进程
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />}</p>
     *
     * @param packageName The name of the package.
     * @return {@code true}: success<br>{@code false}: fail
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static boolean killBackgroundProcesses(@NonNull final String packageName) {
        ActivityManager am =
                (ActivityManager) Util.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (Arrays.asList(aInfo.pkgList).contains(packageName)) {
                am.killBackgroundProcesses(packageName);
            }
        }
        info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return true;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (Arrays.asList(aInfo.pkgList).contains(packageName)) {
                return false;
            }
        }
        return true;
    }

}
