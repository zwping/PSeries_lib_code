package win.zwping.code.utils;

import android.content.res.ColorStateList;

import win.zwping.code.basic.IUtil;
import win.zwping.code.constant.ViewState;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019/4/3 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * <p>
 **/
public final class DrawableUtil implements IUtil.INativeUtil {

    public static ColorStateList getColorStateList(ColorBean... bean) {
        int[] colors = new int[bean.length];
        int[][] states = new int[bean.length][];
        for (int i = 0; i < bean.length; i++) {
            int s = bean[i].state;
            states[i] = s == ViewState.Normal ? new int[]{} : new int[]{s};
            colors[i] = bean[i].color;
        }
        return new ColorStateList(states, colors);
    }


    public static class ColorBean {
        private int state;
        private int color;

        public ColorBean(@ViewState int state, int color) {
            this.state = state;
            this.color = color;
        }
    }

}
