package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSeekBar;
import win.zwping.code.review.pi.PSeekBarHelper;

import static win.zwping.code.utils.ConversionUtil.dp2px;

public class PSeekBar extends AppCompatSeekBar implements PSeekBarHelper.ISeekBar {

    private PSeekBarHelper helper;

    public PSeekBar(Context context) {
        this(context, null);
    }

    public PSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new PSeekBarHelper().initAttrs(this, attrs);
    }

    @Override
    public PSeekBar setProgressColor(int backgroundColor, int progressColor) {
        setProgressColor(0, backgroundColor, progressColor);
        return this;
    }

    @Override
    public PSeekBar setProgressColor(int radiusDp, int backgroundColor, int progressColor) {
        setProgressColor(radiusDp, backgroundColor, progressColor, 0);
        return this;
    }

    @Override
    public PSeekBar setProgressColor(int radiusDp, int backgroundColor, int progressColor, int secondaryProgressColor) {
        helper.initHColor(backgroundColor, progressColor, secondaryProgressColor, dp2px(getContext(), radiusDp));
        return this;
    }


}
