package win.zwping.code.cview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import win.zwping.code.R;
import win.zwping.code.cview.pi.ISupBgLayout;

import static win.zwping.code.utils.ConversionUtil.dp2px;

/**
 * <p>describe：超级背景布局
 * <p>    note：
 * <p>    note：基于ConstraintLayout，在xml就可以控制 边界 角度
 * <p>    note：架构描述{@link ISupBgLayout}
 * <p>    note：
 * <p> @author：zwp on 2018/3/20 0020 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class SupBgLayout extends ConstraintLayout implements ISupBgLayout {

    private int mBorderColor;
    private float mBorderW;
    private float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left   m-index = 1
    private float mRadius;   // m-index = 2
    private Boolean mCircle; // m-index = 3

    private Path mClipPath;   // 剪裁区域路径
    private Paint mPaint;     // 画笔
    private RectF mLayer;     // 图层大小

    public SupBgLayout(Context context) {
        this(context, null);
    }

    public SupBgLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SupBgLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SupBgLayout);
            try {
                int tl = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_top_left_radius, 0);
                int tr = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_top_right_radius, 0);
                int br = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_bottom_right_radius, 0);
                int bl = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_bottom_left_radius, 0);
                radii[0] = radii[1] = tl;
                radii[2] = radii[3] = tr;
                radii[4] = radii[5] = br;
                radii[6] = radii[7] = bl;
                mRadius = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_radius, 0);
                mCircle = array.getBoolean(R.styleable.SupBgLayout_p_circle, false);
                mBorderColor = array.getColor(R.styleable.SupBgLayout_p_border_color, 0X00000000);
                mBorderW = array.getDimensionPixelSize(R.styleable.SupBgLayout_p_border_width, mBorderColor == 0X00000000 ? 0 : dp2px(getContext(), 1F));
            } finally {
                array.recycle();
            }
        }
        if (getBackground() == null) setBackgroundColor(Color.TRANSPARENT); // 触发**？？ 进而在as中实现预览
        // setWillNotDraw(false); // will not onDraw, prepare rewrite onDraw
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mClipPath = new Path();
        mLayer = new RectF();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLayer.set(0, 0, w, h);
        refreshPath();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        refreshPath();
        canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        cusDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipPath(mClipPath);
        } else {
            canvas.clipPath(mClipPath, Region.Op.REPLACE);
        }
        super.draw(canvas);
        canvas.restore();
    }


    private void refreshPath() {
        if (mLayer.left == 0 && mLayer.bottom == 0 && mLayer.right == 0 && mLayer.top == 0)
            mLayer.set(0, 0, getWidth(), getHeight());
        int w = (int) mLayer.width();
        int h = (int) mLayer.height();
        mLayer.set(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());
        mClipPath.reset();
        if (mCircle) { // highest level
            final float r = Math.min(mLayer.height(), mLayer.height()) / 2;
            mClipPath.addCircle(w / 2, h / 2, r, Path.Direction.CW);
        } else if (mRadius > 0) {
            mClipPath.addRoundRect(mLayer, mRadius, mRadius, Path.Direction.CW);
        } else {
            mClipPath.addRoundRect(mLayer, radii, Path.Direction.CW);
        }
    }

    private void cusDraw(Canvas canvas) {
        if (mBorderW > 0) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mBorderW);
            mPaint.setColor(Color.WHITE);
            canvas.drawPath(mClipPath, mPaint); // 裁掉描边区域
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mBorderColor);
            canvas.drawPath(mClipPath, mPaint); // 描边颜色
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(mClipPath, mPaint);
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            final Path path = new Path();
            path.addRect(0, 0, (int) mLayer.width(), (int) mLayer.height(), Path.Direction.CW);
            path.op(mClipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, mPaint);
        }
    }

    @Override
    public SupBgLayout setAloneRadius(int topLeftRadius, int trRadius, int blRadius, int brRadius) {
        radii[0] = radii[1] = topLeftRadius;
        radii[2] = radii[3] = trRadius;
        radii[4] = radii[5] = brRadius;
        radii[6] = radii[7] = blRadius;
        return this;
    }

    @Override
    public SupBgLayout setRadius(int radius) {
        mRadius = radius;
        return this;
    }

    @Override
    public SupBgLayout setCircle() {
        mCircle = true;
        return this;
    }

    @Override
    public SupBgLayout setBorderColor(int color) {
        mBorderColor = color;
        return this;
    }

    @Override
    public SupBgLayout setBorderWidth(int widthDp) {
        mBorderW = dp2px(widthDp);
        return this;
    }

    @Override
    public void init() {
        invalidate();
    }
}
