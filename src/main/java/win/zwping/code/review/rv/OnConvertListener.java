package win.zwping.code.review.rv;

import androidx.annotation.Nullable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-04 17:21:21 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface OnConvertListener<T> {
    void convert(@Nullable ConvertSupBean<T> convertSupBean);
}
