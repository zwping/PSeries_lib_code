package win.zwping.code.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static win.zwping.code.constant.MemoryConstants.BYTE;
import static win.zwping.code.constant.MemoryConstants.GB;
import static win.zwping.code.constant.MemoryConstants.KB;
import static win.zwping.code.constant.MemoryConstants.MB;

/**
 * <p>describe：内存常量，控制输入安全
 * <p>    note：
 * <p> @author：zwp on 2018/3/30 0030 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
@IntDef({BYTE, KB, MB, GB})
@Retention(RetentionPolicy.SOURCE)
public @interface MemoryConstants {

    int BYTE = 1;
    int KB = 1024;
    int MB = 1048576;
    int GB = 1073741824;

}
