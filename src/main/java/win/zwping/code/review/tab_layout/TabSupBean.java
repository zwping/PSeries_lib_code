package win.zwping.code.review.tab_layout;

import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

/**
 * <p>describe：Tab 的超级 Bean，当使用kt调用时，即可见到super bean
 * <p>    note：
 * <p> @author：zwp on 2019-03-18 15:17:00 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class TabSupBean<T> {

    private TabLayout.Tab tab;
    private T item;
    private BaseViewHolder helper;

    public TabSupBean() {
    }

    public TabSupBean(@Nullable TabLayout.Tab tab, @Nullable T item, @Nullable BaseViewHolder helper) {
        this.tab = tab;
        this.item = item;
        this.helper = helper;
    }

    public TabLayout.Tab getTab() {
        return tab;
    }

    public void setTab(TabLayout.Tab tab) {
        this.tab = tab;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public BaseViewHolder getHelper() {
        return helper;
    }

    public void setHelper(BaseViewHolder helper) {
        this.helper = helper;
    }
}
