package win.zwping.code.review.pi;

import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PProgressBar;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-10 16:33:38 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PProgressBarHelper extends IHelper<PProgressBarHelper, PProgressBar> {

    //<editor-fold desc="公共API">
    public interface IProgressBar {
        ///////////////// 进度动画 /////////////
        PProgressBar setProgressOfAnim(int progress, int duration);

        ///////////////// 横向进度 /////////////

        PProgressBar setHorizontalParams(int backgroundColor, int progressColor);

        PProgressBar setHorizontalParams(int radiusDp, int backgroundColor, int progressColor);

        PProgressBar setHorizontalParams(int radiusDp, int backgroundColor, int progressColor, int secondaryProgressColor);

        ///////////////// 默认圆圈 /////////////
        PProgressBar setCircleColor(int color);
    }
    //</editor-fold>


    @Override
    public PProgressBarHelper initAttrs(PProgressBar view, @Nullable AttributeSet attrs) {
        v = view;
        int hBackgroundColor = 0, hProgressColor = 0, hSecondaryProgressColor = 0, hRadius = 0;
        if (null != attrs) {
            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PProgressBar);
            try {
                setCircleColor(array.getColor(R.styleable.PProgressBar_p_circle_color, 0));
                hBackgroundColor = array.getColor(R.styleable.PProgressBar_p_horizontal_background_color, 0);
                hProgressColor = array.getColor(R.styleable.PProgressBar_p_horizontal_progress_color, 0);
                hSecondaryProgressColor = array.getColor(R.styleable.PProgressBar_p_horizontal_secondary_progress_color, 0);
                hRadius = array.getDimensionPixelSize(R.styleable.PProgressBar_p_horizontal_radius, 0);
            } finally {
                array.recycle();
            }
        }
        initHColor(hBackgroundColor, hProgressColor, hSecondaryProgressColor, hRadius);
        return this;
    }

    public void initHColor(int backgroundColor, int progressColor, int secondaryProgressColor, int radiusPx) {
        List<SmartBean> list = new ArrayList<>();
        if (backgroundColor != 0)
            list.add(new SmartBean(android.R.id.background, getDrawable(backgroundColor, radiusPx)));
        if (progressColor != 0)
            list.add(new SmartBean(android.R.id.progress, new ClipDrawable(getDrawable(progressColor, radiusPx), Gravity.START, ClipDrawable.HORIZONTAL)));
        if (secondaryProgressColor != 0)
            list.add(new SmartBean(android.R.id.secondaryProgress, new ClipDrawable(getDrawable(secondaryProgressColor, radiusPx), Gravity.START, ClipDrawable.HORIZONTAL)));
        if (list.size() != 0) {
            Drawable[] drawables = new Drawable[list.size()];
            for (int i = 0; i < list.size(); i++)
                drawables[i] = list.get(i).drawable;
            LayerDrawable layers = new LayerDrawable(drawables);
            for (int i = 0; i < list.size(); i++)
                layers.setId(i, list.get(i).id);
            v.setProgressDrawable(layers);
        }
    }

    public void setCircleColor(int circleColor) {
        if (circleColor != 0) {
            Drawable drawable = v.getIndeterminateDrawable();
            DrawableCompat.setTint(drawable, circleColor);
        }
    }

    public void setProgressOfAnim(int progress, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(v.getProgress(), progress);
        animator.setDuration(duration);
        animator.start();
        animator.addUpdateListener(valueAnimator -> {
            int value = (int) valueAnimator.getAnimatedValue();
            v.setProgress(value);
        });
    }

    private Drawable getDrawable(int color, int radiusPx) {
        ShapeDrawable drawable = new ShapeDrawable();
        if (radiusPx != 0)
            drawable.setShape(new RoundRectShape(new float[]{radiusPx, radiusPx, radiusPx, radiusPx, radiusPx, radiusPx, radiusPx, radiusPx}, null, null));
        drawable.getPaint().setColor(color);
        return drawable;
    }

    private class SmartBean {
        int id;
        Drawable drawable;

        SmartBean(int id, Drawable drawable) {
            this.id = id;
            this.drawable = drawable;
        }
    }
}
