package win.zwping.code.review.tab_layout;

import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import win.zwping.code.review.PTabLayout;
import win.zwping.code.review.pi.IPTabL;
import win.zwping.code.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static win.zwping.code.utils.EmptyUtil.isNotEmpty;
import static win.zwping.code.utils.EmptyUtil.isNotEmptys;

/**
 * <p>describe：使用代码动态更新TabLayout
 * <p>    note：
 * <p> @author：zwp on 2019-03-12 10:19:55 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class CViewTC<B> implements IPTabL.ICViewTC<B>, TabLayout.OnTabSelectedListener {
    private LayoutInflater inflater;
    private PTabLayout tabLayout;
    private int layoutId;

    private List<B> data;
    private List<TabLayout.Tab> tabs;

    /*** 重写Tab状态listener，可在listener中更改tab状态 ***/
    private OnSupTabSelected<B> selectedListener;
    private OnSupTabUnSelected<B> unselectedListener;
    private OnSupTabReselected<B> reselectedListener;

    public CViewTC(PTabLayout tableLayout, B bean, @LayoutRes int resId, @NonNull OnSupTabUnSelected<B> unselectedListener, @Nullable OnSupTabSelected<B> selectedListener, @Nullable OnSupTabReselected<B> reselectedListener) {
        this.layoutId = resId;
        this.tabLayout = tableLayout;
        this.unselectedListener = unselectedListener;
        this.selectedListener = selectedListener;
        this.reselectedListener = reselectedListener;
        inflater = LayoutInflater.from(tableLayout.getContext());
        tableLayout.addOnTabSelectedListener(this);
    }

    @Override
    @Nullable
    public List<TabLayout.Tab> getTabs() {
        return tabs;
    }

    @Override
    public CViewTC<B> setData(@Nullable List<B> list) {
        if (isNotEmpty(list)) refreshData(this.data = list);
        return null;
    }

    @Override
    @Nullable
    public List<B> getData() {
        return this.data;
    }

    private void refreshData(List<B> list) {
        tabs = new ArrayList<>();
        tabLayout.removeAllTabs();
        for (int i = 0; i < list.size(); i++) {
            if (isNotEmptys(unselectedListener) && layoutId > 0) {
                View view = inflater.inflate(layoutId, null);
                TabLayout.Tab tab = tabLayout.newTab().setCustomView(view);
                unselectedListener.onTabUnselected(new TabSupBean<B>(tab, list.get(i), new BaseViewHolder(view)));
                tabLayout.addTab(tab);
                tabs.add(tab);
            } else LogUtil.i("customView 为空，无法渲染TabLayout");
        }
    }

    //<editor-fold desc="tab 三个状态">

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (this.selectedListener == null) return;
        int i = tab.getPosition();
        B bean = isNotEmpty(data) && data.size() > i ? data.get(tab.getPosition()) : null;
        View view = tab.getCustomView();
        this.selectedListener.onTabSelected(new TabSupBean<B>(tab, bean, isNotEmpty(view) ? new BaseViewHolder(view) : null));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (this.unselectedListener == null) return;
        int i = tab.getPosition();
        B bean = isNotEmpty(data) && data.size() > i ? data.get(tab.getPosition()) : null;
        View view = tab.getCustomView();
        this.unselectedListener.onTabUnselected(new TabSupBean<B>(tab, bean, isNotEmpty(view) ? new BaseViewHolder(view) : null));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (this.reselectedListener == null) return;
        int i = tab.getPosition();
        B bean = isNotEmpty(data) && data.size() > i ? data.get(tab.getPosition()) : null;
        View view = tab.getCustomView();
        this.reselectedListener.onTabReselected(new TabSupBean<B>(tab, bean, isNotEmpty(view) ? new BaseViewHolder(view) : null));
    }
    //</editor-fold>
}
