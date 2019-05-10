package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import win.zwping.code.R;
import win.zwping.code.review.pi.IPTabL;
import win.zwping.code.review.tab_layout.*;

/**
 * <p>describe：
 * <p>    note：代码控制customView样式
 * <p>    note：
 * <p> @author：zwp on 2019-02-13 14:10:04 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PTabLayout extends TabLayout implements IPTabL {
    public PTabLayout(Context context) {
        this(context, null);
    }

    public PTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tabStyle);
    }

    public PTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
    }

    private CViewTC cViewTC;

    // 主要实现代码动态更新tab 内容及样式
    @Override
    public <B> CViewTC setCViewTC(B statementBean, int resId, @NonNull OnSupTabUnSelected<B> unselectedListener) {
        return this.setCViewTC(statementBean, resId, unselectedListener, null);
    }

    @Override
    public <B> CViewTC setCViewTC(B statementBean, int resId, @NonNull OnSupTabUnSelected<B> unselectedListener, @Nullable OnSupTabSelected<B> selectedListener) {
        return this.setCViewTC(statementBean, resId, unselectedListener, selectedListener, null);
    }

    @Override
    public <B> CViewTC setCViewTC(B statementBean, int resId, @NonNull OnSupTabUnSelected<B> unselectedListener, @Nullable OnSupTabSelected<B> selectedListener, @Nullable OnSupTabReselected<B> reselectedListener) {
        return cViewTC = new CViewTC<B>(this, statementBean, resId, unselectedListener, selectedListener, reselectedListener);
    }

    @Override
    @Nullable
    public <B> CViewTC<B> getCViewTC() {
        return cViewTC;
    }
}
