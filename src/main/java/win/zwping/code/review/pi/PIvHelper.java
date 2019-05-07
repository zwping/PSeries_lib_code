package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PImageView;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-11 13:57:53 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PIvHelper extends IHelper<PIvHelper, PImageView> {

    //<editor-fold desc="公共API">

    public interface IPIv {
        PImageView displayImage(String url);

        PImageView displayResourceImage(@DrawableRes int resId);

        PImageView setOptions(@DrawableRes int loadingId, @DrawableRes int errorId);

        PImageView setCircle();

        PImageView setRoundRect(int radiusDp);
    }

    //</editor-fold>

    public Option option;

    @Override
    public PIvHelper initAttrs(PImageView view, @Nullable AttributeSet attrs) {
        v = view;
        option = new Option();
        if (null != attrs) {
            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PImageView);
            try {
                option.loadingId = array.getResourceId(R.styleable.PImageView_p_loadingImg, 0);
                option.errorId = array.getResourceId(R.styleable.PImageView_p_errorImg, 0);
                int type = array.getInt(R.styleable.PImageView_p_processorType, -1);
                option.circle = type == 0;
                option.roundRect = type == 1;
                option.roundRectRadius = array.getDimensionPixelSize(R.styleable.PImageView_p_radius, 0);
                if (array.getBoolean(R.styleable.PImageView_p_default_show_loading, false) && 0 != option.loadingId)
                    display(option.loadingId);
            } finally {
                array.recycle();
            }
        }
        return this;
    }

    public void display(String url) {
        Glide.with(v.getContext()).load(url).apply(getOptions()).into(v);
    }

    public void display(int id) {
        Glide.with(v.getContext()).load(id).apply(getOptions()).into(v);
    }

    private RequestOptions getOptions() {
        RequestOptions options = new RequestOptions();
        if (0 != option.loadingId) options.placeholder(option.loadingId);
        if (0 != option.errorId) options.error(option.errorId);
        if (option.circle) options.circleCrop();
        if (option.roundRect) options.centerCrop().transform(new RoundedCorners(option.roundRectRadius));
        return options;
    }

    public class Option {
        public int loadingId;
        public int errorId;
        public boolean circle = false;
        public boolean roundRect = false;
        public int roundRectRadius;
    }
}
