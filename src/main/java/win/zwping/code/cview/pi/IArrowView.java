package win.zwping.code.cview.pi;

import win.zwping.code.constant.Direction;
import win.zwping.code.cview.ArrowView;

/**
 * <p>describe：方向视图 功能接口
 * <p>    note：
 * <p> @author：zwp on 2019-03-25 11:21:14 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface IArrowView {

    ArrowView setArrowColor(int color);

    ArrowView setArrowWidth(int moreWidth);

    ArrowView setArrowDirection(@Direction int direction);

    void init();
}
