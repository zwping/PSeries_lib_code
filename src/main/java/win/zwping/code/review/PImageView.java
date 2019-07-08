package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import win.zwping.code.review.pi.PIvHelper;

import static win.zwping.code.utils.ConversionUtil.dp2px;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-25 14:07:30 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PImageView extends AppCompatImageView implements PIvHelper.IPIv {

    private PIvHelper helper;


    public PImageView(Context context) {
        this(context, null);
    }

    public PImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new PIvHelper().initAttrs(this, attrs);
    }

    @Override
    public PImageView displayImage(String url) {
        helper.display(url);
        return this;
    }

    @Override
    public PImageView displayResourceImage(@DrawableRes int resId) {
        helper.display(resId);
        return this;
    }

    @Override
    public PImageView setOptions(int loadingId, int errorId) {
        helper.option.loadingId = loadingId;
        helper.option.errorId = errorId;
        return this;
    }

    @Override
    public PImageView setCircle() {
        helper.option.circle = true;
        return this;
    }

    @Override
    public PImageView setRoundRect(int radiusDp) {
        helper.option.roundRect = true;
        helper.option.roundRectRadius = dp2px(getContext(), radiusDp);
        return this;
    }

    @Override
    public PImageView setGone(boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
}
