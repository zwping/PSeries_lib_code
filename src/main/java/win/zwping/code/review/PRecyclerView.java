package win.zwping.code.review;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import win.zwping.code.review.pi.PRvHelper;
import win.zwping.code.review.rv.OnConvertListener;

/**
 * <p>describe：
 * <p>    note：建议高度默认设置为WRAP_CONTENT，规避一些莫名的Bug
 * <p>    note：
 * <pre>
 *     0、每个封装的控件不做过多的功能，所以几乎没有容错，用最简单的代码实现最基本的功能，且可扩充(BRVAH)
 *     1、xml中可配置 布局管理器 /  去除焦点 / 禁止竖 横向滑动 / 布局ID
 * </pre>
 * <p> @author：zwp on 2017/12/2 0002 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class PRecyclerView extends RecyclerView implements PRvHelper.IPRv {

    private PRvHelper helper;

    public PRecyclerView(Context context) {
        this(context, null);
    }

    public PRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        helper = new PRvHelper().initAttrs(this, attrs);
    }

    @Override
    public PRecyclerView removeFocus() {
        helper.removeFocus();
        return this;
    }


    /*** 设置线性布局Manager ***/
    @Override
    public PRecyclerView setLinearLayoutManager(@RecyclerView.Orientation int orientation, final boolean noScrollVertically, final boolean noScrollHorizontally) {
        helper.setLinearLayoutManager(orientation, noScrollVertically, noScrollHorizontally);
        return this;
    }

    /*** 设置网格布局Manager ***/
    @Override
    public PRecyclerView setGridLayoutManager(@RecyclerView.Orientation int orientation, int spanCount, final boolean noScrollVertically, final boolean noScrollHorizontally) {
        helper.setGridLayoutManager(orientation, spanCount, noScrollVertically, noScrollHorizontally);
        return this;
    }

    /*** 一些简单的分割线 ***/
    @Override
    public PRecyclerView setSimpleItemDecoration() {
        helper.setSimpleItemDecoration();
        return this;
    }

    @Override
    public PRecyclerView setSimpleItemDecoration(int width, int color, Drawable drawable) {
        helper.setSimpleItemDecoration(width, color, drawable);
        return this;
    }

    //<editor-fold desc="使用PRV 托管 Adapter">

    // 本身BRAVH大大缩小了使用RV的工作量，借助托管机制，大大缩减代码量及工作量

    /*** 配合BRAVH，有超类使用了{@link #getAdapterSup()} ***/
    @Override
    public RecyclerView.Adapter getAdapter() {
        return super.getAdapter();
    }

    public BaseQuickAdapter getAdapterSup() {
        return (BaseQuickAdapter) super.getAdapter();
    }

    @Override
    public <B> BaseQuickAdapter<B, BaseViewHolder> getAdapterSup(B declaredBean) {
        return helper.supAdapter != null ? helper.supAdapter : (BaseQuickAdapter<B, BaseViewHolder>) super.getAdapter();
    }

    @Override
    public <B> BaseQuickAdapter<B, BaseViewHolder> setAdapterSup(B b, int layoutId, final OnConvertListener<B> convertListener) {
        helper.setAdapterSup(b, layoutId, convertListener);
        return getAdapterSup(b);
    }

    /*** attrs p_layout_id ***/
    @Override
    public <B> BaseQuickAdapter<B, BaseViewHolder> setAdapterSup(B b, OnConvertListener<B> convertListener) {
        helper.setAdapterSup(b, convertListener);
        return getAdapterSup(b);
    }

    @Override
    public <B> PRecyclerView setNewData(@Nullable List<B> data) {
        getAdapterSup().setNewData(data);
        return this;
    }

    @Override
    public PRecyclerView setNullData() {
        getAdapterSup().setNewData(null);
        return this;
    }

    @Override
    public <B> PRecyclerView addData(@NonNull List<B> data) {
        getAdapterSup().addData(data);
        return this;
    }


    @Override
    public PRecyclerView loadMoreComplete() {
        getAdapterSup().loadMoreComplete();
        return this;
    }

    @Override
    public PRecyclerView loadMoreFail() {
        getAdapterSup().loadMoreFail();
        return this;
    }

    @Override
    public PRecyclerView loadMoreEnd() {
        getAdapterSup().loadMoreEnd();
        return this;
    }

    //</editor-fold>

    // 需要注意recyclerView 嵌套在ScrollView 中无限加载Bug loadMore
    @Override
    public PRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        setOnLoadMoreListener(2, requestLoadMoreListener);
        return this;
    }

    @Override
    public PRecyclerView setOnLoadMoreListener(int preLoadNumber, final BaseQuickAdapter.RequestLoadMoreListener loadMoreListener) {
        helper.setOnLoadMoreListener(preLoadNumber, loadMoreListener);
        return this;
    }

    // 借助BRAVH的分组列表功能，简单的实现吸顶功能，可实现通讯录功能 {@stickTv 与prv等顶的TextView}
    public PRecyclerView setStickMode(@Nullable final TextView stickTv) {
        helper.setStickMode(stickTv);
        return this;
    }


    /**
     * 智能填充获取成功的数据
     *
     * @param perPageNum    每页数据的数量
     * @param refreshLayout 刷新控件，刷新成功时设置刷新成功状态
     */
    @Override
    public <B> void setSucDataSmartFill(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout) {
        helper.setSucDataSmartFill(refreshOrLoadMore, data, perPageNum, refreshLayout);
    }

    @Override
    public <B> void setSucDataSmartFill(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum) {
        setSucDataSmartFill(refreshOrLoadMore, data, perPageNum, null);
    }

    /*** 针对prv外部包裹了scrollLayout，adapter的loadMore失效(无限调用loadMore) ***/
    @Override
    public <B> void setSucDataSmartFillOfRefreshLayout(Boolean refreshOrLoadMore, @Nullable List<B> data, int perPageNum, @Nullable SmartRefreshLayout refreshLayout) {
        helper.setSucDataSmartFillOfRefreshLayout(refreshOrLoadMore, data, perPageNum, refreshLayout);
    }

    @Override
    public void setSmartError(Boolean refreshOrLoadMore, @Nullable SmartRefreshLayout refreshLayout) {
        helper.setSmartError(refreshOrLoadMore, refreshLayout);
    }

    @Override
    public void setSmartError(Boolean refreshOrLoadMore) {
        setSmartError(refreshOrLoadMore, null);
    }

    /**
     * 获取当前页
     *
     * @param startPage  开始的页数下标（一般是1）
     * @param perPageNum 每页数据的数量
     * @return
     */
    @Override
    public int getCurPage(Boolean refresh, int startPage, int perPageNum) {
        return helper.getCurPage(refresh, startPage, perPageNum);
    }

    @Override
    public int getCurPage(Boolean refresh, int perPageNum) {
        return getCurPage(refresh, 1, perPageNum);
    }


}