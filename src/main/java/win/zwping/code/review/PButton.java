package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LifecycleOwner;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.pi.PBtnHelper;
import win.zwping.code.review.pi.ViewStateColorSwitchHelper;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：
 * <p> @author：zwp on 2019-01-03 14:31:00 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PButton extends AppCompatButton implements PBtnHelper.IPBtn, ViewStateColorSwitchHelper.IVSwitchStatus<PButton> {

    private PBtnHelper helper1;
    private ViewStateColorSwitchHelper helper;

    public PButton(Context context) {
        this(context, null);
    }

    public PButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public PButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new ViewStateColorSwitchHelper().initAttrs(this, attrs);
        helper1 = new PBtnHelper().initAttrs(this, attrs);
    }

    @Override
    public PButton startCountDown(@Nullable LifecycleOwner owner) {
        helper1.startCountDown(owner);
        return this;
    }

    @Override
    public PButton stopCountDown() {
        helper1.stopCountDown();
        return this;
    }

    @Override
    public PButton setGone(boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public PButton setVStateBgColor(ViewStateColor stateColor) {
        helper.setVStateBgColor(stateColor);
        return this;
    }

    @Override
    public PButton setVStateTextColor(ViewStateColor stateColor) {
        helper.setVStateTextColor(stateColor);
        return this;
    }
}
