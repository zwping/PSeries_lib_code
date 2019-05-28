package win.zwping.code.review.pi;

import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PSeekBar;

import java.util.ArrayList;
import java.util.List;

public class PSeekBarHelper extends IHelper<PSeekBarHelper, PSeekBar> {

    //<editor-fold desc="公共API">
    public interface ISeekBar {


        PSeekBar setProgressColor(int backgroundColor, int progressColor);

        PSeekBar setProgressColor(int radiusDp, int backgroundColor, int progressColor);

        PSeekBar setProgressColor(int radiusDp, int backgroundColor, int progressColor, int secondaryProgressColor);

    }
    //</editor-fold>


    @Override
    public PSeekBarHelper initAttrs(PSeekBar view, @Nullable AttributeSet attrs) {
        v = view;
        int backC = 0, proColor = 0, secondProColor = 0, radius = 0;
//        if (null != attrs) {
//            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PSeekBar);
//            try {
//                backC = array.getColor(R.styleable.PSeekBar_p_progress_background_color, 0);
//                proColor = array.getColor(R.styleable.PSeekBar_p_progress_color, 0);
//                secondProColor = array.getColor(R.styleable.PSeekBar_p_second_progress_color, 0);
//                radius = array.getDimensionPixelSize(R.styleable.PSeekBar_p_progress_radius, 0);
//                setSlidingBlockPureColor(array.getColor(R.styleable.PSeekBar_p_sliding_block_pure_color, 0));
//            } finally {
//                array.recycle();
//            }
//        }
//        initHColor(backC, proColor, secondProColor, radius);
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

    public void setSlidingBlockPureColor(int color) {
        //获取seerbar层次drawable对象
//        LayerDrawable layerDrawable = (LayerDrawable) v.getProgressDrawable();
//因为画背景图时候第二进度背景图没有画,所以直接为1
//        Drawable drawable = layerDrawable.getDrawable(1);
//        drawable.setColorFilter(progressColor, PorterDuff.Mode.SRC);
//获取滑块背景
        Drawable thumb = v.getThumb();
        thumb.setColorFilter(color, PorterDuff.Mode.SRC);
        v.invalidate();
    }


    //////////////////////

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
