package win.zwping.code.review.tab_layout;

import androidx.annotation.Nullable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 10:54:10 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface OnSupTabReselected<T> {
    void onTabReselected(@Nullable TabSupBean<T> bean);
}
