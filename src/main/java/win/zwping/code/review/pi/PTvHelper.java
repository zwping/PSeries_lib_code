package win.zwping.code.review.pi;

import android.util.AttributeSet;

import androidx.annotation.Nullable;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PTextView;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-04 17:26:08 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PTvHelper extends IHelper<PTvHelper,PTextView> {

    //<editor-fold desc="Public Interface API">

    public interface IPTv {

        PTextView setGone(boolean visible);

    }
    //</editor-fold>

    @Override
    public PTvHelper initAttrs(PTextView view, @Nullable AttributeSet attrs) {
        return this;
    }

}
