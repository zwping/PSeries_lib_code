package win.zwping.code.comm;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import win.zwping.code.constant.ViewState;
import win.zwping.code.utils.DrawableUtil;

/**
 * <p>describe：视图状态颜色（文字 / 背景）控制
 * <p>    note：
 * <p> @author：zwp on 2019-04-04 16:47:51 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class ViewStateColor {

    public int unable, selected, pressed, checked, focused, normal;

    public ViewStateColor setUnable(int color) {
        this.unable = color;
        return this;
    }

    public ViewStateColor setSelected(int color) {
        this.selected = color;
        return this;
    }

    public ViewStateColor setPressed(int color) {
        this.pressed = color;
        return this;
    }

    public ViewStateColor setChecked(int color) {
        this.checked = color;
        return this;
    }

    public ViewStateColor setFocused(int color) {
        this.focused = color;
        return this;
    }

    public ViewStateColor setNormal(int color) {
        this.normal = color;
        return this;
    }

    public void apply(ViewStateColor colors) {
        if (colors.normal != 0) normal = colors.normal;
        if (colors.selected != 0) selected = colors.selected;
        if (colors.pressed != 0) pressed = colors.pressed;
        if (colors.unable != 0) unable = colors.unable;
        if (colors.checked != 0) checked = colors.checked;
        if (colors.focused != 0) focused = colors.focused;
    }

    public ColorStateList toTextColorStateList(int defaultTextColor) {
        List<DrawableUtil.ColorBean> colorList = new ArrayList<>();
        if (unable != 0) colorList.add(new DrawableUtil.ColorBean(ViewState.Unable, unable));
        if (selected != 0)
            colorList.add(new DrawableUtil.ColorBean(ViewState.Selected, selected));
        if (pressed != 0) colorList.add(new DrawableUtil.ColorBean(ViewState.Pressed, pressed));
        if (checked != 0) colorList.add(new DrawableUtil.ColorBean(ViewState.Checked, checked));
        if (focused != 0)
            colorList.add(new DrawableUtil.ColorBean(ViewState.Focused, focused));
        colorList.add(new DrawableUtil.ColorBean(ViewState.Normal, normal != 0 ? normal : defaultTextColor));
        return DrawableUtil.getColorStateList(colorList.toArray(new DrawableUtil.ColorBean[colorList.size()]));
    }

    public StateListDrawable toBgStateListDrawable(@Nullable Drawable defaultBg) {
        StateListDrawable drawable = new StateListDrawable();
        if (unable != 0)
            drawable.addState(new int[]{ViewState.Unable}, new ColorDrawable(unable));
        if (selected != 0)
            drawable.addState(new int[]{ViewState.Selected}, new ColorDrawable(selected));
        if (pressed != 0)
            drawable.addState(new int[]{ViewState.Pressed}, new ColorDrawable(pressed));
        if (focused != 0)
            drawable.addState(new int[]{ViewState.Focused}, new ColorDrawable(focused));
        if (checked != 0)
            drawable.addState(new int[]{ViewState.Checked}, new ColorDrawable(checked));
        if (normal != 0) drawable.addState(new int[0], new ColorDrawable(normal));
        if (0 == normal && defaultBg != null)
            drawable.addState(new int[0], defaultBg);
        return drawable;
    }
}
