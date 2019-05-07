package win.zwping.code.review.tab_layout;

import androidx.annotation.Nullable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 10:53:37 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnSupTabSelected<T> {
    void onTabSelected(@Nullable TabSupBean<T> bean);
}
