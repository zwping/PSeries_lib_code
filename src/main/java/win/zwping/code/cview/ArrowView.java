package win.zwping.code.cview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import win.zwping.code.R;
import win.zwping.code.constant.Direction;
import win.zwping.code.cview.pi.IArrowView;

import static win.zwping.code.utils.ConversionUtil.dp2px;

/**
 * <p>describe：箭头控件
 * <p>    note：
 * <p>    note：架构描述{@link IArrowView}
 * <p>    note：
 * <p> @author：zwp on 2019-03-25 10:57:25 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class ArrowView extends View implements IArrowView {

    private Paint paint;
    private int color;
    @Direction
    private int direction;
    private int width;

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ArrowView);
            try {
                color = array.getColor(R.styleable.ArrowView_p_arrow_color, Color.GRAY);
                direction = array.getInt(R.styleable.ArrowView_p_direction, Direction.Right);
                width = array.getDimensionPixelSize(R.styleable.ArrowView_p_arrow_width, dp2px(getContext(), 1));
            } finally {
                array.recycle();
            }
        }

        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(wSize, hSize);
        setMeasuredDimension(min, min); // 设置其为正方形
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setStrokeWidth(width);
        int w5 = getWidth() / 5; // 宽度的5/1
        int sx1, sy1, sx2, sy2, ex, ey;
        switch (direction) {
            case Direction.Up:
                sx1 = 0;
                sy1 = w5 * 4;
                ex = getWidth() / 2;
                ey = w5;
                sx2 = getWidth();
                sy2 = w5 * 4;
                canvas.drawLine(sx1, sy1, ex, ey, paint);
                canvas.drawLine(ex, ey, sx2, sy2, paint);
                break;
            case Direction.Right:
                sx1 = sx2 = w5;
                sy1 = 0;
                sy2 = getHeight();
                ex = (w5 * 4);
                ey = getHeight() / 2;
                canvas.drawLine(sx1, sy1, ex, ey, paint);
                canvas.drawLine(sx2, sy2, ex, ey, paint);
                break;
            case Direction.Down:
                sx1 = 0;
                sy1 = w5;
                ex = getWidth() / 2;
                ey = w5 * 4;
                sx2 = getWidth();
                sy2 = w5;
                canvas.drawLine(sx1, sy1, ex, ey, paint);
                canvas.drawLine(ex, ey, sx2, sy2, paint);
                break;
            case Direction.Left:
                ex = w5;
                ey = getHeight() / 2;
                sx1 = sx2 = w5 * 4;
                sy1 = 0;
                sy2 = getHeight();
                canvas.drawLine(ex, ey, sx1, sy1, paint);
                canvas.drawLine(ex, ey, sx2, sy2, paint);
                break;
        }
    }

    @Override
    public ArrowView setArrowColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public ArrowView setArrowWidth(int moreWidthDp) {
        this.width = dp2px(moreWidthDp);
        return this;
    }

    @Override
    public ArrowView setArrowDirection(@Direction int direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public void init() {
        invalidate();
    }
}
