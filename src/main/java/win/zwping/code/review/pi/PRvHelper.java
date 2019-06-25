package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PRecyclerView;
import win.zwping.code.review.rv.ConvertSupBean;
import win.zwping.code.review.rv.ItemDecoration;
import win.zwping.code.review.rv.OnConvertListener;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.recyclerview.widget.OrientationHelper.HORIZONTAL;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-09 14:43:22 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PRvHelper extends IHelper<PRvHelper, PRecyclerView> {

    public interface IPRv {
        // 去除焦点 - scrollView嵌套recyclerView，recyclerView自动获取焦点导致界面滑动
        PRecyclerView removeFocus();

        PRecyclerView setLinearLayoutManager(@RecyclerView.Orientation int orientation, final boolean canScrollVertically, final boolean canScrollHorizontally);

        PRecyclerView setGridLayoutManager(@RecyclerView.Orientation int orientation, int spanCount, final boolean canScrollVertically, final boolean canScrollHorizontally);

        // 设置简单的网格线
        PRecyclerView setSimpleItemDecoration();

        PRecyclerView setSimpleItemDecoration(int width, int color, Drawable drawable);

        ///////////////////////////////////////
        // 借助BRAVH 框架 实现的功能
        <B> BaseQuickAdapter<B, BaseViewHolder> setAdapterSup(B b, int layoutId, final OnConvertListener<B> convertListener);

        <B> BaseQuickAdapter<B, BaseViewHolder> setAdapterSup(B b, OnConvertListener<B> convertListener);

        BaseQuickAdapter getAdapterSup();

        <B> BaseQuickAdapter<B, BaseViewHolder> getAdapterSup(B declaredBean);

        <B> PRecyclerView setNewData(@Nullable List<B> data);

        PRecyclerView setNullData();

        <B> PRecyclerView addData(@NonNull List<B> data);

        // 上拉加载更多(倒数第几个Item)
        PRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener);

        PRecyclerView setOnLoadMoreListener(int PreLoadNumber, BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener);

        <B> void setSucDataSmartFill(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout);

        <B> void setSucDataSmartFill(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum);

        <B> void setSucDataSmartFillOfRefreshLayout(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout);

        void setSmartError(Boolean refreshOrLoadMore, @Nullable SmartRefreshLayout refreshLayout);

        void setSmartError(Boolean refreshOrLoadMore);

        int getCurPage(Boolean refresh, int startPage, int perPageNum);

        int getCurPage(Boolean refresh, int perPageNum);

        // 加载完成
        PRecyclerView loadMoreComplete();

        // 加载失败
        PRecyclerView loadMoreFail();

        // 加载结束 // 不能再进行加载更多了，需要setNewData重新开启
        PRecyclerView loadMoreEnd();

        ////////////////////////////////

        // 吸顶效果
        PRecyclerView setStickMode(final TextView stickTv);
    }

    private int mLayoutManger;
    private int mSpanCount, mNoScroll, mOrientation, mDecorationWidth, mDecorationColor;
    private Drawable mDecorationDrawable;

    private int adapterLayoutId;

    @Override
    public PRvHelper initAttrs(PRecyclerView view, @Nullable AttributeSet attrs) {
        v = view;
        boolean simpleDecoration = false;
        if (null != attrs) {
            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PRecyclerView);
            try {
                if (array.getBoolean(R.styleable.PRecyclerView_p_removeFocus, false)) { //是否去除焦点
                    removeFocus();
                }
                mLayoutManger = array.getInt(R.styleable.PRecyclerView_p_layout_manager, 1); //默认列表
                mOrientation = array.getInt(R.styleable.PRecyclerView_p_orientation, 1); // 默认竖向
                mSpanCount = array.getInt(R.styleable.PRecyclerView_p_spanCount, 2);
                mNoScroll = array.getInt(R.styleable.PRecyclerView_p_noScroll, -1);
                adapterLayoutId = array.getResourceId(R.styleable.PRecyclerView_p_item_layout_id, 0);
                mDecorationColor = array.getColor(R.styleable.PRecyclerView_p_decoration_color, Color.GRAY);
                mDecorationWidth = array.getDimensionPixelSize(R.styleable.PRecyclerView_p_decoration_width, dp2px(v.getContext(), 1));
                mDecorationDrawable = array.getDrawable(R.styleable.PRecyclerView_p_decoration_drawable);
                simpleDecoration = array.getBoolean(R.styleable.PRecyclerView_p_simple_decoration_enable, false);
            } finally {
                array.recycle();
            }
        }
        RecyclerView.ItemAnimator animator = v.getItemAnimator();
        if (null != animator) animator.setChangeDuration(0); //禁止刷新闪烁

        if (mLayoutManger == 1)
            setLinearLayoutManager(mOrientation != 1 ? HORIZONTAL : VERTICAL, mNoScroll == 1 || mNoScroll == 3, mNoScroll == 2 || mNoScroll == 3);
        if (mLayoutManger == 2)
            setGridLayoutManager(mOrientation != 1 ? HORIZONTAL : VERTICAL, mSpanCount, mNoScroll == 1 || mNoScroll == 3, mNoScroll == 2 || mNoScroll == 3);
        if (simpleDecoration) setSimpleItemDecoration();
        return this;
    }

    // 如果还抢站焦点，在scrollView的根布局下增加 android:descendantFocusability="blocksDescendants"
    public void removeFocus() {
        v.setFocusableInTouchMode(false); // 去除嵌套引发自动获取焦点的bug
        v.requestFocus();
    }

    public void setLinearLayoutManager(@RecyclerView.Orientation int orientation, final boolean noScrollVertically, final boolean noScrollHorizontally) {
        v.setLayoutManager(new LinearLayoutManager(v.getContext(), orientation, false) {
            @Override
            public boolean canScrollVertically() {
                if (noScrollVertically) return false;
                else return super.canScrollVertically();
            }

            @Override
            public boolean canScrollHorizontally() {
                if (noScrollHorizontally) return false;
                else return super.canScrollHorizontally();
            }
        });
    }

    public void setGridLayoutManager(@RecyclerView.Orientation int orientation, int spanCount, final boolean noScrollVertically, final boolean noScrollHorizontally) {
        v.setLayoutManager(new GridLayoutManager(v.getContext(), spanCount, orientation, false) {
            @Override
            public boolean canScrollVertically() {
                if (noScrollVertically) return false;
                else return super.canScrollVertically();
            }

            @Override
            public boolean canScrollHorizontally() {
                if (noScrollHorizontally) return false;
                else return super.canScrollHorizontally();
            }
        });
    }

    public void setSimpleItemDecoration() {
        setSimpleItemDecoration(mDecorationWidth, mDecorationColor, mDecorationDrawable);
    }

    public void setSimpleItemDecoration(int width, int color, Drawable drawable) {
        for (int i = 0; i < v.getItemDecorationCount(); i++)
            v.removeItemDecoration(v.getItemDecorationAt(i));
        v.addItemDecoration(new ItemDecoration(1, width, color, drawable));
        if (mLayoutManger == 2) // 网格的话需要绘制纵向分割线
            v.addItemDecoration(new ItemDecoration(2, width, color, drawable)); // 最右侧不需要绘制分割线
    }

    public BaseQuickAdapter supAdapter;

    public <B> void setAdapterSup(B b, int layoutId, final OnConvertListener<B> convertListener) {
        v.setAdapter(supAdapter = new BaseQuickAdapter<B, BaseViewHolder>(layoutId, null) {
            protected void convert(BaseViewHolder helper, B item) {
                convertListener.convert(new ConvertSupBean<B>(helper, item));
            }
        });
    }

    public <B> void setAdapterSup(B b, OnConvertListener<B> convertListener) {
        if (adapterLayoutId == 0) throw new NullPointerException();
        setAdapterSup(b, adapterLayoutId, convertListener);
    }

    public void setOnLoadMoreListener(int preLoadNumber, final BaseQuickAdapter.RequestLoadMoreListener loadMoreListener) {
        v.getAdapterSup().setPreLoadNumber(preLoadNumber);
        v.getAdapterSup().disableLoadMoreIfNotFullPage(v);
        v.getAdapterSup().setOnLoadMoreListener(loadMoreListener, v);
    }

    public void setStickMode(@Nullable final TextView stickTv) {
        if (stickTv == null) return;
        v.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = v.getLayoutManager() instanceof LinearLayoutManager ? (LinearLayoutManager) v.getLayoutManager() : null;
                BaseSectionQuickAdapter adapter = (BaseSectionQuickAdapter) v.getAdapter(); // 编译检测 如果crash，请使用BRAVH的分组列表功能
                if (lm == null || adapter == null || adapter.getData().size() == 0)
                    return; // 目前只支持线性布局
                int p = lm.findFirstCompletelyVisibleItemPosition(); // 完全可见的item的坐标
                boolean isHeader = ((SectionEntity) adapter.getData().get(p)).isHeader; // 完全可见的item是否是head
                View v = lm.findViewByPosition(p);

                String head = ((SectionEntity) adapter.getData().get(lm.findFirstVisibleItemPosition())).header; // 可见的item对应的head内容

                if (v == null) {
                    stickTv.setVisibility(GONE);
                } else {
                    stickTv.setVisibility(VISIBLE);
                    if (isHeader) {
                        if (v.getTop() <= stickTv.getMeasuredHeight()) {
                            stickTv.setTranslationY(-(stickTv.getMeasuredHeight() - v.getTop()));
                            stickTv.setText(head);
                        } else {
                            stickTv.setTranslationY(0);
                            stickTv.setText(head);
                        }
                    } else {
                        stickTv.setTranslationY(0);
                        stickTv.setText(head);
                    }
                }
            }
        });
    }

    public <B> void setSucDataSmartFill(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout) {
        boolean empty = isEmpty(data);
        if (refreshOrLoadMore) {
            if (refreshLayout != null) refreshLayout.finishRefresh();
            if (empty) v.setNullData();
            else {
                v.setNewData(data);
                if (data.size() % perPageNum != 0) v.getAdapterSup().loadMoreEnd();
            }
        } else {
            if (empty) {
                v.getAdapterSup().loadMoreEnd();
            } else {
                v.addData(data);
                if (data.size() % perPageNum != 0) v.getAdapterSup().loadMoreEnd();
                else v.getAdapterSup().loadMoreComplete();
            }
        }
    }

    /*** 针对prv外部包裹了scrollLayout，adapter的loadMore失效(无限调用loadMore) ***/
    public <B> void setSucDataSmartFillOfRefreshLayout(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout) {
        boolean empty = isEmpty(data);
        if (refreshOrLoadMore) {
            if (refreshLayout != null) {
                if (refreshLayout.getState() == RefreshState.Refreshing)
                    refreshLayout.finishRefresh();
                refreshLayout.setEnableLoadMore(true);
                v.getAdapterSup().removeAllFooterView();
            }
            if (empty) {
                v.setNullData();
                if (null != refreshLayout) refreshLayout.setEnableLoadMore(false);
            } else {
                v.setNewData(data);
                if (data.size() % perPageNum != 0) {
                    v.getAdapterSup().addFooterView(getFootView());
                    if (null != refreshLayout) refreshLayout.setEnableLoadMore(false);
                }
            }
        } else {
            if (null != refreshLayout && refreshLayout.getState() == RefreshState.Loading)
                refreshLayout.finishLoadMore();
            if (empty) {
                v.getAdapterSup().addFooterView(getFootView());
                if (null != refreshLayout) refreshLayout.setEnableLoadMore(false);
            } else {
                v.addData(data);
                if (data.size() % perPageNum != 0) {
                    v.getAdapterSup().addFooterView(getFootView());
                    if (null != refreshLayout) refreshLayout.setEnableLoadMore(false);
                }
            }
        }
    }

    public void setSmartError(Boolean refreshOrLoadMore, @Nullable SmartRefreshLayout refreshLayout) {
        if (refreshOrLoadMore) {
            if (refreshLayout != null && refreshLayout.getState() == RefreshState.Refreshing)
                refreshLayout.finishRefresh(false);
        } else {
            if (refreshLayout != null && refreshLayout.getState() == RefreshState.Loading)
                refreshLayout.finishLoadMore(false);
            v.loadMoreFail();
        }
    }

    private View getFootView() {
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.brvah_quick_view_load_more, null, false);
        view.findViewById(R.id.load_more_loading_view).setVisibility(GONE);
        view.findViewById(R.id.load_more_load_end_view).setVisibility(VISIBLE);
        return view;
    }

    public int getCurPage(Boolean refresh, int startPage, int perPageNum) {
        return refresh || isEmpty(v.getAdapterSup().getData()) ? startPage : (v.getAdapterSup().getData().size() / perPageNum) + startPage;
    }
}
