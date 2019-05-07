package win.zwping.code.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import win.zwping.code.Util;
import win.zwping.code.basic.IUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：资源相关工具
 * <p>    note：
 * <p> @author：zwp on 2018/5/10 0010 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class ResourceUtil  implements IUtil.INativeUtil{

    public static int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(Util.getApp(), colorId);
    }

    public static String getString(@StringRes int stringId) {
        return Util.getApp().getString(stringId);
    }

    public static Drawable getDrawable(@DrawableRes int res) {
        return Util.getApp().getResources().getDrawable(res);
    }

    public static Bitmap getBitmap(@DrawableRes int res) {
        Activity ac = AcUtil.getTopActivity();
        if (isEmpty(ac)) return null;
        return BitmapFactory.decodeResource(ac.getResources(), res);
    }
}
