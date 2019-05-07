package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import win.zwping.code.R;
import win.zwping.code.review.pi.PProgressBarHelper;

import static win.zwping.code.utils.ConversionUtil.dp2px;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-09 15:43:38 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PProgressBar extends ProgressBar implements PProgressBarHelper.IProgressBar {

    private PProgressBarHelper helper;

    // new PProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);

    public PProgressBar(Context context) {
        this(context, null);
    }

    public PProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.progressBarStyle);
    }

    public PProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new PProgressBarHelper().initAttrs(this, attrs);
    }

    @Override
    public PProgressBar setProgressOfAnim(int progress, int duration) {
        helper.setProgressOfAnim(progress, duration);
        return this;
    }

    @Override
    public PProgressBar setHorizontalParams(int backgroundColor, int progressColor) {
        this.setHorizontalParams(0, backgroundColor, progressColor);
        return this;
    }

    @Override
    public PProgressBar setHorizontalParams(int radiusDp, int backgroundColor, int progressColor) {
        this.setHorizontalParams(radiusDp, backgroundColor, progressColor, 0);
        return this;
    }

    @Override
    public PProgressBar setHorizontalParams(int radiusDp, int backgroundColor, int progressColor, int secondaryProgressColor) {
        helper.initHColor(backgroundColor, progressColor, secondaryProgressColor, dp2px(getContext(), radiusDp));
        return this;
    }

    @Override
    public PProgressBar setCircleColor(int color) {
        helper.setCircleColor(color);
        return this;
    }
}
