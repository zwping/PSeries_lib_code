package win.zwping.code.cview.pi;

import android.view.View;

import androidx.annotation.DrawableRes;
import win.zwping.code.cview.ArrowView;
import win.zwping.code.cview.MenuBar;
import win.zwping.code.review.PEditText;
import win.zwping.code.review.PImageView;
import win.zwping.code.review.PTextView;

/**
 * <p>describe：自定义菜单栏公共接口
 * <p>    note：
 * <p> @author：zwp on 2019-03-27 11:44:21 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public interface IMenuBar {

    // 获取对象 ** 暴露所有对象，使用时可以为所欲为 **

    PImageView getTitleIconPiv();

    PTextView getTitlePtv();

    PTextView getContentPtv();

    PEditText getContentInputPet();

    ArrowView getArrowV();

    View getTopLineV();

    View getBottomLineV();

    // 设置内容
    MenuBar setTitleIconPivResId(@DrawableRes int resId);

    MenuBar setTitleIconPivPath(String path);

    MenuBar setTitleTxt(CharSequence txt);

    MenuBar setContentTxt(CharSequence txt);

    MenuBar setContentInputTxt(CharSequence hint);

    MenuBar setContentInputHintTxt(CharSequence txt);

    // 设置距离 / 大小
    MenuBar setTitleIconMarginLeft(int dp);

    MenuBar setTitleMarginLeft(int dp);

    MenuBar setContentMarginRight(int dp);

    MenuBar setArrowMarginRight(int dp);

    MenuBar setTitleIconWH(int whDp);

    MenuBar setArrowWH(int whDp);

    // 设置颜色 / 字号

    MenuBar setTitleColor(int color);

    MenuBar setTitleSize(int sizeDp);

    MenuBar setContentColor(int color);

    MenuBar setContentHintColor(int color);

    MenuBar setContentSize(int sizeDp);

    MenuBar setArrowColor(int color);

    MenuBar setTopLineColor(int color);

    MenuBar setBottomLineColor(int color);

    // 设置显示隐藏
    MenuBar setContentIsTextOrInput(boolean text);

    MenuBar setTitleIconVisibility(boolean visibility);

    MenuBar setArrowVisibility(boolean visibility);

    MenuBar setTopLineVisibility(boolean visibility);

    MenuBar setBottomLineVisibility(boolean visibility);

}
