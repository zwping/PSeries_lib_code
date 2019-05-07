package win.zwping.code.review.rv;

import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.Nullable;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-18 17:16:27 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class ConvertSupBean<T> {
    private BaseViewHolder helper;
    private T item;

    public ConvertSupBean() {
    }

    public ConvertSupBean(BaseViewHolder helper, T item) {
        this.helper = helper;
        this.item = item;
    }

    public BaseViewHolder getHelper() {
        return helper;
    }

    public void setHelper(BaseViewHolder helper) {
        this.helper = helper;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
