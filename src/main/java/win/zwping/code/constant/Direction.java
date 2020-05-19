package win.zwping.code.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static win.zwping.code.constant.Direction.Down;
import static win.zwping.code.constant.Direction.Left;
import static win.zwping.code.constant.Direction.Right;
import static win.zwping.code.constant.Direction.Up;

/**
 * <p>describe：方向，控制输入安全
 * <p>    note：
 * <p> @author：zwp on 2019-03-25 11:24:33 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */

@IntDef({Up, Right, Down, Left})
@Retention(RetentionPolicy.SOURCE)
public @interface Direction {
    int Up = 0;
    int Right = 1;
    int Down = 2;
    int Left = 3;
}
