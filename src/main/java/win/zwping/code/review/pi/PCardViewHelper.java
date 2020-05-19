package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.PCardView;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-01 13:43:30 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class PCardViewHelper extends IHelper<PCardViewHelper, PCardView> {

    //<editor-fold desc="公共API">

    public interface IVSwitchStatus {
        PCardView setVStateBgColor(ViewStateColor stateColor);
    }

    //</editor-fold>


    private int defaultBg;
    private ViewStateColor stateBc = new ViewStateColor();

    @Override
    public PCardViewHelper initAttrs(PCardView view, @Nullable AttributeSet attrs) {
        v = view;
        if (null != attrs) {
            TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.PCardView);
            try {
                stateBc.normal = array.getColor(R.styleable.PCardView_p_normal_bg_color, 0);
                stateBc.focused = array.getColor(R.styleable.PCardView_p_focused_bg_color, 0);
                stateBc.selected = array.getColor(R.styleable.PCardView_p_selected_bg_color, 0);
                stateBc.unable = array.getColor(R.styleable.PCardView_p_unable_bg_color, 0);
                stateBc.checked = array.getColor(R.styleable.PCardView_p_checked_bg_color, 0);
                stateBc.pressed = array.getColor(R.styleable.PCardView_p_pressed_bg_color, 0);
            } finally {
                array.recycle();
            }
        }
        initColor();
        return this;
    }

    public void setVStateBgColor(ViewStateColor stateColor) {
        stateBc.apply(stateColor);
        initColor();
    }

    private void initColor() {
        v.setCardBackgroundColor(stateBc.toTextColorStateList(getBg()));
    }

    private int getBg() {
        if (defaultBg == 0) defaultBg = v.getCardBackgroundColor().getDefaultColor();
        return defaultBg;
    }

}
