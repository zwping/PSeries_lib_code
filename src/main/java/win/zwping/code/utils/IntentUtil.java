package win.zwping.code.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;

import androidx.annotation.RequiresPermission;
import androidx.core.content.FileProvider;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

import static android.Manifest.permission.CALL_PHONE;

/**
 * <p>describe：意图 工具
 * <p>    note：
 * <p>    note：isIntentAvailable
 * <p>    note：installAppIntent / uninstallAppIntent
 * <p>    note：launchAppIntent / appDetailsSettingIntent / openDefaultBrowser
 * <p>    note：shareIntent / otherAppComponentIntent
 * <p>    note：shutdownIntent / dialIntent / callIntent / sendSmsIntent / captureIntent
 * <p>    note：
 * <p> @author：zwp on 2018/5/12 0012 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class IntentUtil implements IUtil.INativeUtil {

    private IntentUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * Return whether the intent is available.
     *
     * @param intent The intent.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIntentAvailable(final Intent intent) {
        return Util.getApp().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    //<editor-fold desc="安装 / 卸载">

    /**
     * 获取安装APP意图
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param filePath The path of file.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final String filePath) {
        return getInstallAppIntent(FileUtil.getFileByPath(filePath));
    }

    /**
     * 获取安装APP意图
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file The file.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final File file) {
        return getInstallAppIntent(file, false);
    }

    /**
     * 获取安装APP意图
     * <p>Target APIs greater than 25 must hold
     * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
     *
     * @param file      The file.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of install app
     */
    public static Intent getInstallAppIntent(final File file, final boolean isNewTask) {
        if (file == null) return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            String authority = Util.getApp().getPackageName() + ".pseries.provider";
            data = FileProvider.getUriForFile(Util.getApp(), authority, file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        Util.getApp().grantUriPermission(Util.getApp().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(data, type);
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    /**
     * 获取卸载意图
     *
     * @param packageName The name of the package.
     * @return the intent of uninstall app
     */
    public static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    /**
     * 获取卸载意图
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of uninstall app
     */
    public static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    //</editor-fold>
    //<editor-fold desc="启动 / APP设置界面">

    /**
     * 获取启动APP意图
     *
     * @param packageName The name of the package.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    /**
     * 获取启动APP意图
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of launch app
     */
    public static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = Util.getApp().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) return null;
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取APP详细信息的设置界面
     *
     * @param packageName The name of the package.
     * @return the intent of launch app details settings
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName) {
        return getLaunchAppDetailsSettingsIntent(packageName, false);
    }

    /**
     * 获取APP详细信息的设置界面
     *
     * @param packageName The name of the package.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of launch app details settings
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String packageName,
                                                           final boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return getIntent(intent, isNewTask);
    }

    //</editor-fold>
    //<editor-fold desc="默认浏览器 / 分享 / 组件 / 关机">

    public static Intent openDefaultBrowser(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    /**
     * 获取文字分享意图
     *
     * @param content The content.
     * @return the intent of share text
     */
    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * 获取文字分享意图
     *
     * @param content   The content.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share text
     */

    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content   The content.
     * @param imagePath The path of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final String imagePath) {
        return getShareImageIntent(content, imagePath, false);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content   The content.
     * @param imagePath The path of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final String imagePath,
                                             final boolean isNewTask) {
        if (imagePath == null || imagePath.length() == 0) return null;
        return getShareImageIntent(content, new File(imagePath), isNewTask);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content The content.
     * @param image   The file of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final File image) {
        return getShareImageIntent(content, image, false);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content   The content.
     * @param image     The file of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final File image,
                                             final boolean isNewTask) {
        if (image != null && image.isFile()) return null;
        return getShareImageIntent(content, Uri.fromFile(image), isNewTask);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content The content.
     * @param uri     The uri of image.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content, final Uri uri) {
        return getShareImageIntent(content, uri, false);
    }

    /**
     * 获取图片分享的意图
     *
     * @param content   The content.
     * @param uri       The uri of image.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of share image
     */
    public static Intent getShareImageIntent(final String content,
                                             final Uri uri,
                                             final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取组件意图
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName, final String className) {
        return getComponentIntent(packageName, className, null, false);
    }

    /**
     * 获取组件意图
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final boolean isNewTask) {
        return getComponentIntent(packageName, className, null, isNewTask);
    }

    /**
     * 获取组件意图
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param bundle      The Bundle of extras to add to this intent.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle) {
        return getComponentIntent(packageName, className, bundle, false);
    }

    /**
     * 获取组件意图
     *
     * @param packageName The name of the package.
     * @param className   The name of class.
     * @param bundle      The Bundle of extras to add to this intent.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of component
     */
    public static Intent getComponentIntent(final String packageName,
                                            final String className,
                                            final Bundle bundle,
                                            final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取关机意图
     * <p>Requires root permission
     * or hold {@code android:sharedUserId="android.uid.system"},
     * {@code <uses-permission android:name="android.permission.SHUTDOWN/>}
     * in manifest.</p>
     *
     * @return the intent of shutdown
     */
    public static Intent getShutdownIntent() {
        return getShutdownIntent(false);
    }

    /**
     * 获取关机意图
     * <p>Requires root permission
     * or hold {@code android:sharedUserId="android.uid.system"},
     * {@code <uses-permission android:name="android.permission.SHUTDOWN/>}
     * in manifest.</p>
     *
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of shutdown
     */
    public static Intent getShutdownIntent(final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        return getIntent(intent, isNewTask);
    }

    //</editor-fold>
    //<editor-fold desc="拨号界面 / 拨号 / 发送短信 / 拍照">

    /**
     * 获取拨号界面意图
     *
     * @param phoneNumber The phone number.
     * @return the intent of dial
     */
    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * 获取拨号界面意图
     *
     * @param phoneNumber The phone number.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of dial
     */
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取拨打电话意图
     * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber The phone number.
     * @return the intent of call
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 获取拨打电话意图
     * <p>Must hold {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber The phone number.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of call
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取发送短信意图
     *
     * @param phoneNumber The phone number.
     * @param content     The content of SMS.
     * @return the intent of send SMS
     */
    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 获取发送短信意图
     *
     * @param phoneNumber The phone number.
     * @param content     The content of SMS.
     * @param isNewTask   True to add flag of new task, false otherwise.
     * @return the intent of send SMS
     */
    public static Intent getSendSmsIntent(final String phoneNumber,
                                          final String content,
                                          final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取拍照意图
     *
     * @param outUri The uri of output.
     * @return the intent of capture
     */
    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * 获取拍照意图
     *
     * @param outUri    The uri of output.
     * @param isNewTask True to add flag of new task, false otherwise.
     * @return the intent of capture
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    //</editor-fold>
    //<editor-fold desc="私有方法">

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    //</editor-fold>

//    /**
//     * 获取选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        return intent.setType("image*//*");
//    }
//
//    /**
//     * 获取从文件中选择照片的 Intent
//     *
//     * @return
//     */
//    public static Intent getPickIntentWithDocuments() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        return intent.setType("image*//*");
//    }
//
//
//    public static Intent buildImageGetIntent(final Uri saveTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageGetIntent(saveTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageGetIntent(Uri saveTo, int aspectX, int aspectY,
//                                             int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent();
//        if (Build.VERSION.SDK_INT < 19) {
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//        }
//        intent.setType("image*//*");
//        intent.putExtra("output", saveTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCropIntent(final Uri uriFrom, final Uri uriTo, final int outputX, final int outputY, final boolean returnData) {
//        return buildImageCropIntent(uriFrom, uriTo, 1, 1, outputX, outputY, returnData);
//    }
//
//    public static Intent buildImageCropIntent(Uri uriFrom, Uri uriTo, int aspectX, int aspectY,
//                                              int outputX, int outputY, boolean returnData) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uriFrom, "image*//*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("output", uriTo);
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);
//        intent.putExtra("return-data", returnData);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//        return intent;
//    }
//
//    public static Intent buildImageCaptureIntent(final Uri uri) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        return intent;
//    }
}
