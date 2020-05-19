package win.zwping.code.utils;

import android.widget.TextView;

import androidx.annotation.NonNull;
import win.zwping.code.basic.IUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;
import static win.zwping.code.utils.EmptyUtil.isNotEmpty;

/**
 * <p>describe：TextView 工具类
 * <p>    note：
 * <p>    note：setText / setEmpty
 * <p>    note：
 * <p>  author：zwp on 2017/9/22 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public final class TvUtil implements IUtil.INativeUtil {

    /**
     * 精修textView.setText()，防止空指针、优化代码显示
     *
     * @param t 使用可变参数
     */
    public static void setText(@NonNull TvC... t) { //内容手动装箱过程
        if (t != null && t.length != 0) {
            for (TvC element : t) {
                TextView textView = element.textView;
                CharSequence o = element.value;
                if (null != textView && isNotEmpty(element.value))
                    textView.setText(isEmpty(o) ? "" : o);
            }
        }
    }

    public static void setText(TextView textView, Object value) {
        setText(new TvC(textView, value));
    }

    //<editor-fold desc="TextView内容装箱容器">

    public static class TvC {
        public TextView textView;
        public CharSequence value;

        public TvC(TextView textView, Object value) {
            this.textView = textView;
            this.value = (isNotEmpty(value) && value instanceof CharSequence ? (CharSequence) value : isNotEmpty(value) ? value + "" : "");
        }

        public TvC(TextView textView, Object value, String emptyReplaceValue) {
            this.textView = textView;
            this.value = (isNotEmpty(value) && value instanceof CharSequence ? (CharSequence) value : isNotEmpty(value) ? value + "" : emptyReplaceValue);
        }
    }
    //</editor-fold>


    /*** 置空 ***/
    public static void setEmpty(TextView... tv) {
        if (isNotEmpty(tv)) {
            for (TextView textView : tv) {
                if (isNotEmpty(textView)) {
                    textView.setText(null);
                    textView.setTag(null);
                }
            }
        }
    }


}
