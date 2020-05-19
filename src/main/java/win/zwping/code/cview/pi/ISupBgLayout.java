package win.zwping.code.cview.pi;

import win.zwping.code.cview.SupBgLayout;

/**
 * <p>describe：超级背景布局公共接口
 * <p>    note：
 * <p> @author：zwp on 2019-03-26 11:00:46 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface ISupBgLayout {

    SupBgLayout setAloneRadius(int topLeftRadius, int trRadius, int blRadius, int brRadius);

    SupBgLayout setRadius(int radius);

    SupBgLayout setCircle();

    SupBgLayout setBorderColor(int color);

    SupBgLayout setBorderWidth(int widthDp);

    void init();

}
