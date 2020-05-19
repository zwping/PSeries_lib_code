package win.zwping.code.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static win.zwping.code.constant.ViewState.Checked;
import static win.zwping.code.constant.ViewState.Focused;
import static win.zwping.code.constant.ViewState.Normal;
import static win.zwping.code.constant.ViewState.Pressed;
import static win.zwping.code.constant.ViewState.Selected;
import static win.zwping.code.constant.ViewState.Unable;

/**
 * <p>describe：视图状态，控制输入安全
 * <p>    note：
 * <p> @author：zwp on 2019-04-04 11:04:46 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */

@IntDef({Selected, Normal, Unable, Pressed, Checked, Focused})
@Retention(RetentionPolicy.SOURCE)
public @interface ViewState {
    int Unable = -android.R.attr.state_enabled; // 不可用
    int Selected = android.R.attr.state_selected; // 选中
    int Pressed = android.R.attr.state_pressed; // 按压
    int Checked = android.R.attr.state_checked; // 选中
    int Focused = android.R.attr.state_focused; // 焦点
    int Normal = -2;
    // 设置颜色，normal一定要最后一个设置
}
