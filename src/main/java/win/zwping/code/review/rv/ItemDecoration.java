package win.zwping.code.review.rv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-20 08:58:05 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int mOrientation; // 1 == vertically  2 == horizontally
    private int mDividerWidth = 0;
    private int mDividerColor = Color.GRAY;
    private Drawable mDividerDrawable;
    private Paint mPaint;

    public ItemDecoration(int orientation, int dividerWidth, int dividerColor, Drawable dividerDrawable) {
        mOrientation = orientation;
        mDividerWidth = dividerWidth;
        mDividerColor = dividerColor;
        mDividerDrawable = dividerDrawable;

        //绘制纯色分割线
        if (dividerDrawable == null) {
            //初始化画笔(抗锯齿)并设置画笔颜色和画笔样式为填充
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mDividerColor);
            mPaint.setStyle(Paint.Style.FILL);
            //绘制图片分割线
        } else {
            //如果没有指定分割线的size，则默认是图片的厚度
            if (mDividerWidth == 0) {
                if (mOrientation == 1) {
                    mDividerWidth = dividerDrawable.getIntrinsicHeight();
                } else {
                    mDividerWidth = dividerDrawable.getIntrinsicWidth();
                }
            }
        }
    }

    // 画线
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 纵向列表画横线，横向列表画竖线
        if (mOrientation == 1) {
            drawHorizontalDivider(c, parent, state);
        } else {
            // todo 横向的列表
            if(parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager gridLm = (GridLayoutManager) parent.getLayoutManager();
                int count = gridLm.getSpanCount();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    if ((i + 1) % count != 0) // 最右侧的竖行分割线不需要绘制 // todo 这样会导致最后一个item宽度 = 本身宽度+竖线宽度
                        drawVerticalDivider(c, parent, state);
                }
            }
        }
    }

    // 设置条目周边的偏移量
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == 1) {
            outRect.set(0, 0, 0, mDividerWidth);
        } else {
            if(parent.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager gridLm = (GridLayoutManager) parent.getLayoutManager();
                int count = gridLm.getSpanCount();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    outRect.set(0, 0, (i + 1) % count != 0 ? mDividerWidth : 0, 0); // 最右侧的竖行分割线不需要绘制
                }
            }
        }
    }

    /**
     * 画横线
     */
    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //得到分割线的四个点：左、上、右、下
        //画横线时左右可以根据parent得到
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();

        //上下需要根据每个孩子控件计算
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerWidth;
            //得到四个点后开始画
            if (mDividerDrawable == null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
            }
        }
    }

    /**
     * 画竖线
     */
    private void drawVerticalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //画竖线时上下可以根据parent得到
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();

        //左右需要根据孩子控件计算
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDividerWidth;
            //得到四个点后开始画
            if (mDividerDrawable == null) {
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
            }
        }
    }
}
