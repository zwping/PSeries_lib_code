package win.zwping.code.utils.frame;

import androidx.annotation.Nullable;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.lifecycle.LifecycleOwner;
import win.zwping.code.basic.IUtil;


/**
 * <p>describe：auto dispose 框架工具类
 * <p>    note：
 * <p> @author：zwp on 2017/12/19 0019 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class AutoDisposeUtil implements IUtil.IFrameUtil {

    public static AutoDisposeConverter<Long> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner));
    }

}
