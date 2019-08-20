package win.zwping.code.utils;

import android.os.Bundle;
import android.os.Parcelable;
import win.zwping.code.basic.IUtil;
import win.zwping.code.comm.Bean;

import java.io.Serializable;
import java.util.ArrayList;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：装箱工具类 BoxedUtil
 * <p>    note：
 * <p>    note：bundle
 * <p>    note：
 * <p>  author：zwp on 2018-06-22 17:11:31 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public final class BoxedUtil implements IUtil.INativeUtil {

    //<editor-fold desc="Bundle装箱">

    public static Bundle covBundle(String key, Object v) {
        return covBundle(new Bean(key, v));
    }

    public static Bundle covBundle(Bean... beans) {
        Bundle bundle = new Bundle();
        for (Bean bean : beans) {
            if (isEmpty(bean.value)) {
                //throw new NullPointerException("内容为空，Bundle装箱失败");
                continue;
            }
            if (bean.value instanceof String) {
                bundle.putString(bean.key, String.valueOf(bean.value));
            } else if (bean.value instanceof Boolean) {
                bundle.putBoolean(bean.key, (Boolean) bean.value);
            } else if (bean.value instanceof Integer) {
                bundle.putInt(bean.key, (Integer) bean.value);
            } else if (bean.value instanceof Float) {
                bundle.putFloat(bean.key, (Float) bean.value);
            } else if (bean.value instanceof Double) {
                bundle.putDouble(bean.key, (Double) bean.value);
            } else if (bean.value instanceof Parcelable) {
                bundle.putParcelable(bean.key, (Parcelable) bean.value);
            } else if (bean.value instanceof Serializable) {
                bundle.putSerializable(bean.key, (Serializable) bean.value);
            } else {
                throw new RuntimeException("未知数据类型，请本地化Bundle装箱方法");
            }
        }
        return bundle;
    }

    //</editor-fold>

}
