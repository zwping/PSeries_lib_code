package win.zwping.code.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static win.zwping.code.constant.TimeConstants.DAY;
import static win.zwping.code.constant.TimeConstants.HOUR;
import static win.zwping.code.constant.TimeConstants.MIN;
import static win.zwping.code.constant.TimeConstants.MSEC;
import static win.zwping.code.constant.TimeConstants.SEC;

/**
 * <p>describe：时间 常量，控制输入安全
 * <p>    note：
 * <p> @author：zwp on 2018/3/16 0016 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
@IntDef({MSEC, SEC, MIN, HOUR, DAY})
@Retention(RetentionPolicy.SOURCE)
public @interface TimeConstants {
    int MSEC = 1;
    int SEC = 1000;
    int MIN = 60000;
    int HOUR = 3600000;
    int DAY = 86400000;
}
