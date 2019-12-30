package win.zwping.code.cview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.cview.pi.ISwitchPageLayout;
import win.zwping.code.utils.NetworkUtil;
import win.zwping.code.utils.ToastUtil;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * <p>describe：页面状态切换布局
 * <p>    note：
 * <p>    note：实现 加载中View、空数据View、网络出错View、异常出错View、自定义View、内容View 随意切换
 * <p>    note：架构描述{@link ISwitchPageLayout}
 * <p>    note：
 * <p> @author：zwp on 2017/12/19 0019 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class SwitchPageStateLayout extends FrameLayout implements ISwitchPageLayout {

    //<editor-fold desc="内部参数">

    private int mLoadingResId, mEmptyResId, mNetErrorResId, mErrorResId, mCusResId, mContentResId;
    private SparseArray<View> mResIds = new SparseArray<>();

    private OnClickListener mOnRetryClickListener;
    private LayoutInflater mInflater;

    private Boolean defaultShowLoading = false; // 是否默认展示loadingView
    //</editor-fold>
    //<editor-fold desc="构造函数">

    public SwitchPageStateLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwitchPageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchPageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    //</editor-fold>
    //<editor-fold desc="内部方法">

    private void initView(AttributeSet attrs) {
        // setLayoutTransition(new LayoutTransition());
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchPageStateLayout);
            try {
                mLoadingResId = array.getResourceId(R.styleable.SwitchPageStateLayout_p_loadingView, R.layout.child_page_state_loading_layout);
                mEmptyResId = array.getResourceId(R.styleable.SwitchPageStateLayout_p_emptyView, R.layout.child_page_state_empty_layout);
                mNetErrorResId = array.getResourceId(R.styleable.SwitchPageStateLayout_p_netErrorView, R.layout.child_page_state_net_error_layout);
                mErrorResId = array.getResourceId(R.styleable.SwitchPageStateLayout_p_errorView, R.layout.child_page_state_error_layout);
                mCusResId = array.getResourceId(R.styleable.SwitchPageStateLayout_p_cusView, 0);
                defaultShowLoading = array.getBoolean(R.styleable.SwitchPageStateLayout_p_default_show_loading, false);
            } finally {
                array.recycle();
            }
        }
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            //removeViews(1, getChildCount() - 1);
            View view = mInflater.inflate(R.layout.child_page_state_empty_layout, this, false);
            ((TextView)view.findViewById(R.id.title_tv)).setText("SwitchPageStatesLayout can host only one direct child");
            addView(view);
            throw new IllegalStateException("SwitchPageStatesLayout can host only one direct child");
        }
        View view = getChildAt(0);
        setContentView(view);
        if (defaultShowLoading) show(mLoadingResId);
    }

    private void setContentView(View view) {
        mContentResId = view.getId();
        mResIds.put(mContentResId, view);
    }

    /*** 显示状态布局 ***/
    private void show(int resId) {
        if (0 == resId) return;
        if (layout(resId).getVisibility() == VISIBLE) return;
        for (int i = 0; i < mResIds.size(); i++) {
            mResIds.get(mResIds.keyAt(i)).setVisibility(GONE);
        }
        layout(resId).setVisibility(VISIBLE);
    }

    /*** 设置布局，并酌情增加点击事件 ***/
    private View layout(int resId) {
        for (int i = 0; i < mResIds.size(); i++) {
            if (resId == mResIds.keyAt(i)) return mResIds.get(mResIds.keyAt(i));
        }
        View view = mInflater.inflate(resId, this, false);
        view.setVisibility(GONE);
        addView(view);
        mResIds.put(resId, view);
        if (null != mOnRetryClickListener && (resId == mErrorResId || resId == mNetErrorResId)) {
            View v = view.findViewById(R.id.page_state_retry); // 如果布局中没有R.id.page_state_retry，则整个布局都可点击
            if (v != null)
                v.setOnClickListener(v1 -> {
                    showLoading();
                    mOnRetryClickListener.onClick(v1);
                });
            else
                view.setOnClickListener(v12 -> {
                    showLoading();
                    mOnRetryClickListener.onClick(v12);
                });
        }
        return view;
    }

    //</editor-fold>
    //<editor-fold desc="API">

    @Override
    public SwitchPageStateLayout setLoadingResId(@LayoutRes int loadingResId) {
        if (loadingResId != -1) this.mLoadingResId = loadingResId;
        return this;
    }

    @Override
    public SwitchPageStateLayout setEmptyResId(@LayoutRes int emptyResId) {
        this.mEmptyResId = emptyResId;
        return this;
    }

    @Override
    public SwitchPageStateLayout setNetErrorResId(@LayoutRes int netErrorResId) {
        this.mNetErrorResId = netErrorResId;
        return this;
    }

    @Override
    public SwitchPageStateLayout setErrorResId(@LayoutRes int errorResId) {
        this.mErrorResId = errorResId;
        return this;
    }

    @Override
    public SwitchPageStateLayout setCusResId(@LayoutRes int cusResId) {
        this.mCusResId = cusResId;
        return this;
    }

    @Override
    public SwitchPageStateLayout showContent() {
        show(mContentResId);
        return this;
    }

    @Override
    public SwitchPageStateLayout showLoading() {
        show(mLoadingResId);
        return this;
    }

    @Override
    public SwitchPageStateLayout showEmpty() {
        show(mEmptyResId);
        return this;
    }

    @Override
    public SwitchPageStateLayout showNetErrorWork() {
        show(mNetErrorResId);
        return this;
    }

    @Override
    public SwitchPageStateLayout showError() {
        show(mErrorResId);
        return this;
    }

    @Override
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public SwitchPageStateLayout showErrorOfSmart() {
        if (NetworkUtil.isConnected()) show(mErrorResId);
        else show(mNetErrorResId);
        return this;
    }

    @Override
    public SwitchPageStateLayout showCusView() {
        if (0 != mCusResId) show(mCusResId);
        else ToastUtil.showShort("界面缺失");
        return this;
    }

    @Override
    /*** 设置重试点击事件 ***/
    public SwitchPageStateLayout setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
        return this;
    }

    @Override
    public SwitchPageStateLayout setOnRetryClickListener(View view) {
        if (null == mOnRetryClickListener)
            throw new IllegalStateException("请先调用setOnRetryClickListener");
        view.setOnClickListener(mOnRetryClickListener);
        return this;
    }

    @Override
    public SwitchPageStateLayout setOnRetryClickListener(View view, OnClickListener onRetryClickListener) {
        setOnRetryClickListener(onRetryClickListener);
        view.setOnClickListener(mOnRetryClickListener);
        return this;
    }

    @Override
    public View getContentView() {
        return layout(mContentResId);
    }

    @Override
    public View getLoadingView() {
        return layout(mLoadingResId);
    }

    @Override
    public View getEmptyView() {
        return layout(mEmptyResId);
    }

    @Override
    public View getNetErrorView() {
        return layout(mNetErrorResId);
    }

    @Override
    public View getErrorView() {
        return layout(mErrorResId);
    }

    @Override
    public View getCusView() {
        return layout(mCusResId);
    }

    //</editor-fold>
}
