package win.zwping.code.cview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import win.zwping.code.R;
import win.zwping.code.review.PImageView;
import win.zwping.code.review.PTextView;


/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-05-09 15:26:17 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class EmptyView extends RelativeLayout {

    private PTextView titleTv;
    private PImageView emptyIv;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.child_empty_view, this);
        emptyIv = findViewById(R.id.empty_v);
        titleTv = findViewById(R.id.empty_tv);
    }

    public EmptyView setTitle(CharSequence txt) {
        titleTv.setText(txt);
        return this;
    }

    public EmptyView setHideEmptyIcon(Boolean hide) {
        emptyIv.setVisibility(hide ? GONE : VISIBLE);
        return this;
    }

    public EmptyView setEmptyResId(@DrawableRes int resId) {
        emptyIv.setImageResource(resId);
        return this;
    }

    public EmptyView setLyBg(@ColorInt int color) {
        findViewById(R.id.root_layout).setBackgroundColor(color);
        return this;
    }
}
