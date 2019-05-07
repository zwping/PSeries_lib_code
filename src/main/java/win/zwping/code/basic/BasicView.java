package win.zwping.code.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * <p>describe：View主要方法
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:57:51 mail：1101558280@qq.com web: http://www.zwping.win </p>
 * @deprecated 描述功能的作用，Widget的最上级，派生了无数Widget，在这记录一些主要的方法
 */
@Deprecated
public class BasicView  extends View {

    /*** 构造器 ***/
    public BasicView(Context context) {
        super(context);
    }

    public BasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BasicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*** 从XML中加载该组件并利用它构界面的回调 ***/
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /*** <b>检测</b>该组件及所包含的所有子组件的大小 ***/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*** 该组件需要分配其子组件的位置、大小时的回调 ***/
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /*** 该组件大小被改变的回调 ***/
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*** 绘制本身 ***/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /*** 绘制子控件 ***/
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /*** 全部绘制完成后进行总绘制调整 ***/
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /*** 物理键盘按下 ***/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /*** 物理键盘按下抬起 ***/
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    /*** 触摸事件 ***/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 接触屏幕
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                break;
            case MotionEvent.ACTION_UP: // 移开屏幕
                break;
        }
        return super.onTouchEvent(event);
    }

    /*** 该组件焦点发生变化时的回调 ***/
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    /*** 该组件的窗口失去或得到焦点时的回调 ***/
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    /*** 该组件加入窗口时的回调 ***/
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /*** 该组件从窗口分离时的回调 ***/
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /*** 窗口显示隐藏状态改变的回调 ***/
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }
}
