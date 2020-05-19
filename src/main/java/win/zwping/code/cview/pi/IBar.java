package win.zwping.code.cview.pi;

import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.DrawableRes;
import win.zwping.code.cview.ArrowView;
import win.zwping.code.cview.Bar;
import win.zwping.code.review.PImageView;
import win.zwping.code.review.PTextView;

/**
 * <p>describe：自定义Bar（代替ToolBar）公共接口
 * <p>    note：
 * <p> @author：zwp on 2019-03-25 14:41:40 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface IBar {

    // 获取对象 ** 暴露所有对象，使用时可以为所欲为 **
    PTextView getTitlePtv();

    RelativeLayout getReturnRl();

    PTextView getReturnPtv();

    PImageView getReturnPiv();

    ArrowView getReturnArrow();

    RelativeLayout getMenuRl();

    PTextView getMenuPtv();

    PImageView getMenuPiv();

    View getBottomLine();

    // 设置内容
    Bar setTitle(CharSequence title);

    Bar setReturnTxt(CharSequence returnTxt);

    Bar setReturnPivResId(@DrawableRes int resId);

    Bar setReturnPivPath(String path);

    Bar setMenuTxt(CharSequence menuTxt);

    Bar setMenuPivResId(@DrawableRes int resId);

    Bar setMenuPivPath(String path);

    // 设置距离 / 大小
    Bar setTitlePadding(int paddingDp);

    Bar setReturnPadding(int paddingDp);

    Bar setMenuPadding(int paddingDp);

    Bar setReturnArrowWH(int whDp);

    Bar setReturnPivWH(int whDp);

    Bar setMenuPivWH(int whDp);

    // 设置颜色 / 字号

    Bar setTitleColor(int color);

    Bar setTitleSize(int sizeDp);

    Bar setReturnTxtColor(int color);

    Bar setReturnTxtSize(int sizeDp);

    Bar setMenuTxtColor(int color);

    Bar setMenuTxtSize(int sizeDp);

    Bar setReturnArrowColor(int color);

    Bar setBottomLineColor(int color);

    Bar setReturnTxtBold();

    Bar setTitleTxtBold();

    Bar setMenuTxtBold();

    // 设置显示隐藏
    Bar setReturnVisibility(boolean visibility);

    Bar setReturnArrowVisibility(boolean visibility);

    Bar setMenuVisibility(boolean visibility);

    Bar setBottomLineVisibility(boolean visibility);

    // 点击事件
    Bar setReturnClickListener(View.OnClickListener listener);

    Bar setMenuClickListener(View.OnClickListener listener);

}
