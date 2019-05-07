package win.zwping.code.cview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import win.zwping.code.R;

/**
 * <p>describe：越界回弹布局
 * <p>    note：
 * <p>    note：todo 横向越界回弹
 * <p>    note：
 * <p> @author：zwp on 2018/3/12 0012 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class OverScrollLayout extends SmartRefreshLayout {

    public OverScrollLayout(Context context) {
        this(context, null);
    }

    public OverScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.OverScrollLayout);
        try {
            setOverScroll(array.getBoolean(R.styleable.OverScrollLayout_p_enable, true));
        } finally {
            array.recycle();
        }
    }

    public void setOverScroll(Boolean enable) {
        setEnablePureScrollMode(enable); // 纯滑动
        setEnableRefresh(enable);
        setEnableLoadMore(enable); // 上下都可以滑动
    }
}
