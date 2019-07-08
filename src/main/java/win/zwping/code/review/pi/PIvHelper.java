package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PImageView;

import java.security.MessageDigest;

import static win.zwping.code.utils.ConversionUtil.dp2px;

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

        PImageView setGone(boolean visible);
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
        if (option.roundRect || option.roundRectRadius != 0)
            options.centerCrop().transform(new GlideRoundTransform(option.roundRectRadius == 0 ? dp2px(v.getContext(), 2) : option.roundRectRadius));
        return options;
    }

    public class Option {
        public int loadingId;
        public int errorId;
        public boolean circle = false;
        public boolean roundRect = false;
        public int roundRectRadius;
    }

    //////////////////////////////////////////////////////////////////

    /*** 解决glide 圆角与centerCrop冲突Bug ***/
    public class GlideRoundTransform extends CenterCrop {

        private float radius = 0f;

        public GlideRoundTransform(int dp) {
            radius = dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            //glide4.0+
            Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
            return roundCrop(pool, transform);
            //glide3.0
            //return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        public String getId() {
            return getClass().getName() + Math.round(radius);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }
}
