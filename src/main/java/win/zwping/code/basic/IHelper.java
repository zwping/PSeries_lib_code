package win.zwping.code.basic;

import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * <p>describe：针对cview(自定义View) 和 review(重写View)的帮助类
 * <p>    note：
 * <p> @author：zwp on 2019-03-29 15:38:17 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public abstract class IHelper<H, V> {

    /*** cview或者review的对象 ***/
    protected V v;

    /*** 帮助类必须取得的两个对象
     * @return H 舔一下链式编程
     * ***/
    public abstract H initAttrs(V view, @Nullable AttributeSet attrs);
}
