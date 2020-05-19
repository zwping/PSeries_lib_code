package win.zwping.code.review.tab_layout;

import androidx.annotation.Nullable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 10:53:53 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface OnSupTabUnSelected<T> {
    void onTabUnselected(@Nullable TabSupBean<T> bean);
}
