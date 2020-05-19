package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.PButton;
import win.zwping.code.review.PEditText;
import win.zwping.code.review.PTextView;

/**
 * <p>describe：视图简单的状态颜色切换助手 -- 复杂的请使用shape xml
 * <p>    note：
 * <p> @author：zwp on 2019-03-29 15:11:29 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class ViewStateColorSwitchHelper extends IHelper<ViewStateColorSwitchHelper, View> {

    //<editor-fold desc="公共API">
    // 单一些，只支持Color
    public interface IVSwitchStatus<V> {
        V setVStateBgColor(ViewStateColor stateColor);

        V setVStateTextColor(ViewStateColor stateColor);
    }
    //</editor-fold>

    private TextView v;
    // 默认颜色，需要注意存/取的顺序，优先级低于normal
    @Nullable
    private Drawable defaultBg;
    private int defaultTextColor;
    private ViewStateColor stateBc = new ViewStateColor();
    private ViewStateColor stateTc = new ViewStateColor();

    @Override
    public ViewStateColorSwitchHelper initAttrs(View view, @Nullable AttributeSet attrs) {
        v = (TextView) view;
        if (v instanceof PButton) init((PButton) v, attrs);
        if (v instanceof PEditText) init((PEditText) v, attrs);
        if (v instanceof PTextView) init((PTextView) v, attrs);
        initColor();
        return this;
    }

    private void initColor() {
        StateListDrawable drawable = stateBc.toBgStateListDrawable(getBg());
        if (null != drawable) v.setBackground(drawable);
        v.setTextColor(stateTc.toTextColorStateList(getTextColor()));
    }

    public void setVStateBgColor(ViewStateColor stateColor) {
        stateBc.apply(stateColor);
        initColor();
    }

    public void setVStateTextColor(ViewStateColor stateColor) {
        stateTc.apply(stateColor);
        initColor();
    }

    @Nullable
    private Drawable getBg() {
        if (defaultBg == null) defaultBg = v.getBackground();
        return defaultBg;
    }

    private int getTextColor() {
        if (defaultTextColor == 0) defaultTextColor = v.getCurrentTextColor();
        return defaultTextColor;
    }

    private void init(PButton view, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.PButton);
            try {
                stateBc.normal = array.getColor(R.styleable.PButton_p_normal_bg_color, 0);
                stateBc.focused = array.getColor(R.styleable.PButton_p_focused_bg_color, 0);
                stateBc.selected = array.getColor(R.styleable.PButton_p_selected_bg_color, 0);
                stateBc.unable = array.getColor(R.styleable.PButton_p_unable_bg_color, 0);
                stateBc.checked = array.getColor(R.styleable.PButton_p_checked_bg_color, 0);
                stateBc.pressed = array.getColor(R.styleable.PButton_p_pressed_bg_color, 0);

                stateTc.normal = array.getColor(R.styleable.PButton_p_normal_text_color, 0);
                stateTc.focused = array.getColor(R.styleable.PButton_p_focused_text_color, 0);
                stateTc.selected = array.getColor(R.styleable.PButton_p_selected_text_color, 0);
                stateTc.unable = array.getColor(R.styleable.PButton_p_unable_text_color, 0);
                stateTc.checked = array.getColor(R.styleable.PButton_p_checked_text_color, 0);
                stateTc.pressed = array.getColor(R.styleable.PButton_p_pressed_text_color, 0);
            } finally {
                array.recycle();
            }
        }
    }


    private void init(PEditText view, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.PEditText);
            try {
                stateBc.normal = array.getColor(R.styleable.PEditText_p_normal_bg_color, 0);
                stateBc.focused = array.getColor(R.styleable.PEditText_p_focused_bg_color, 0);
                stateBc.selected = array.getColor(R.styleable.PEditText_p_selected_bg_color, 0);
                stateBc.unable = array.getColor(R.styleable.PEditText_p_unable_bg_color, 0);
                stateBc.checked = array.getColor(R.styleable.PEditText_p_checked_bg_color, 0);
                stateBc.pressed = array.getColor(R.styleable.PEditText_p_pressed_bg_color, 0);

                stateTc.normal = array.getColor(R.styleable.PEditText_p_normal_text_color, 0);
                stateTc.focused = array.getColor(R.styleable.PEditText_p_focused_text_color, 0);
                stateTc.selected = array.getColor(R.styleable.PEditText_p_selected_text_color, 0);
                stateTc.unable = array.getColor(R.styleable.PEditText_p_unable_text_color, 0);
                stateTc.checked = array.getColor(R.styleable.PEditText_p_checked_text_color, 0);
                stateTc.pressed = array.getColor(R.styleable.PEditText_p_pressed_text_color, 0);
            } finally {
                array.recycle();
            }
        }
    }

    private void init(PTextView view, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.PTextView);
            try {
                stateBc.normal = array.getColor(R.styleable.PTextView_p_normal_bg_color, 0);
                stateBc.focused = array.getColor(R.styleable.PTextView_p_focused_bg_color, 0);
                stateBc.selected = array.getColor(R.styleable.PTextView_p_selected_bg_color, 0);
                stateBc.unable = array.getColor(R.styleable.PTextView_p_unable_bg_color, 0);
                stateBc.checked = array.getColor(R.styleable.PTextView_p_checked_bg_color, 0);
                stateBc.pressed = array.getColor(R.styleable.PTextView_p_pressed_bg_color, 0);

                stateTc.normal = array.getColor(R.styleable.PTextView_p_normal_text_color, 0);
                stateTc.focused = array.getColor(R.styleable.PTextView_p_focused_text_color, 0);
                stateTc.selected = array.getColor(R.styleable.PTextView_p_selected_text_color, 0);
                stateTc.unable = array.getColor(R.styleable.PTextView_p_unable_text_color, 0);
                stateTc.checked = array.getColor(R.styleable.PTextView_p_checked_text_color, 0);
                stateTc.pressed = array.getColor(R.styleable.PTextView_p_pressed_text_color, 0);
            } finally {
                array.recycle();
            }
        }
    }
}
// 部分思想借鉴了
// https://github.com/JavaNoober/BackgroundLibrary

