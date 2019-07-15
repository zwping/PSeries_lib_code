package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import win.zwping.code.R;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.pi.PCardViewHelper;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-01 13:28:55 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PCardView extends CardView implements PCardViewHelper.IVSwitchStatus {

    private PCardViewHelper helper;

    public PCardView(@NonNull Context context) {
        this(context, null);
    }

    public PCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new PCardViewHelper().initAttrs(this, attrs);
    }

    @Override
    public PCardView setVStateBgColor(ViewStateColor stateColor) {
        helper.setVStateBgColor(stateColor);
        return this;
    }

    public PCardView setGone(Boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
}
