package win.zwping.code.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;

import androidx.annotation.Nullable;
import win.zwping.code.basic.IUtil;

/**
 * <p>describe：Animator动画工具类
 * <p>    note：
 * <p>  author：zwp on 2017/6/22 0022 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */

public final class AnimatorUtil implements IUtil.INativeUtil {

    private AnimatorUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final float ALPHA_MIN = 0.0f;
    public static final float ALPHA_MAX = 1.0f;

    //<editor-fold desc="Alpha">

    /**
     * 渐变
     *
     * @param v
     * @param fromAlpha 取值范围(0.0f ~ 1.0f)
     * @param toAlpha   取值范围(0.0f ~ 1.0f)
     * @param duration
     */

    public static void alpha(View v, float fromAlpha, float toAlpha, int duration) {
        alpha(v, fromAlpha, toAlpha, duration, null);
    }

    /**
     * 渐变
     *
     * @param v
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     * @param animatorListener
     */
    public static void alpha(@Nullable View v, float fromAlpha, float toAlpha, int duration, Animator.AnimatorListener
            animatorListener) {
        if (v == null) return;
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, fromAlpha, toAlpha);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }
    //</editor-fold>
    //<editor-fold desc="Translation">

    /**
     * 左上角为锚点的X轴位移动画
     *
     * @param v
     * @param fromX    左上角为锚点，动画开始时离锚点的位置(X轴)(px)
     * @param toX      左上角为锚点，动画结束时离锚点的位置(X轴)(px)
     * @param duration
     */
    public static void translationX(View v, float fromX, float toX, int duration) {
        translationX(v, fromX, toX, duration, null);
    }

    /**
     * 左上角为锚点的X轴位移动画
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void translationX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * 左上角为锚点的Y轴位移动画
     *
     * @param v
     * @param fromY    左上角为锚点，动画开始时离锚点的位置(X轴)(px)
     * @param toY      左上角为锚点，动画结束时离锚点的位置(X轴)(px)
     * @param duration
     */
    public static void translationY(View v, float fromY, float toY, int duration) {
        translationY(v, fromY, toY, duration, null, null);
    }

    public static void translationY(View v, float fromY, float toY, int duration, TimeInterpolator interpolator) {
        translationY(v, fromY, toY, duration, interpolator, null);
    }

    /**
     * translation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void translationY(View v, float fromY, float toY, int duration, @Nullable TimeInterpolator interpolator, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, fromY, toY);
        animator.setDuration(duration);
        if (interpolator != null)
            animator.setInterpolator(interpolator);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    //</editor-fold>
    //<editor-fold desc="Rotation">

    /*** 中心点为锚点的旋转动画 ***/
    public static void rotation(View v, float fromX, float toX, int duration) {
        rotation(v, fromX, toX, duration, null);
    }

    public static void rotation(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * 中心点为锚点的X轴旋转动画
     *
     * @param v
     * @param fromX    动画开始时view相对X轴的角度(0~360)
     * @param toX      动画结束时view相对X轴的角度(0~360)
     * @param duration
     */
    public static void rotationX(View v, float fromX, float toX, int duration) {
        rotationX(v, fromX, toX, duration, null);
    }

    /**
     * rotation x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void rotationX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * 中心点为锚点的Y轴旋转动画
     *
     * @param v
     * @param fromY    动画开始时view相对Y轴的角度(0~360)
     * @param toY      动画结束时view相对Y轴的角度(0~360)
     * @param duration
     */
    public static void rotationY(View v, float fromY, float toY, int duration) {
        rotationY(v, fromY, toY, duration, null);
    }

    /**
     * rotation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void rotationY(View v, float fromY, float toY, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION_Y, fromY, toY);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    //</editor-fold>
    //<editor-fold desc="Scale">

    /**
     * 中心点为锚点的X轴缩放动画
     *
     * @param v
     * @param fromX    动画开始时view展现的缩放倍数（1~view的大小倍数）
     * @param toX      动画结束时view展现的缩放倍数（1~view的大小倍数）
     * @param duration
     */
    public static void scaleX(View v, float fromX, float toX, int duration) {
        scaleX(v, fromX, toX, duration, null);
    }

    /**
     * scale x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void scaleX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.SCALE_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * 中心点为锚点的X轴缩放动画
     *
     * @param v
     * @param fromY    动画开始时view展现的缩放倍数（1~view的大小倍数）
     * @param toY      动画结束时view展现的缩放倍数（1~view的大小倍数）
     * @param duration
     */
    public static void scaleY(View v, float fromY, float toY, int duration) {
        scaleY(v, fromY, toY, duration, null);
    }

    /**
     * scale y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void scaleY(View v, float fromY, float toY, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.SCALE_Y, fromY, toY);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    //</editor-fold>
    //<editor-fold desc="Shake">

    /**
     * 中心点为锚点的X轴摇晃动画
     *
     * @param v
     */
    public static void shakeX(View v) {
        shakeX(v, 100, 1000, 5.0f);
    }

    /**
     * shake x
     *
     * @param v
     * @param offset        针对锚点的相对摇晃距离(px)
     * @param duration
     * @param numberOfTimes 插值器中设置动画循环次数
     */
    public static void shakeX(View v, float offset, long duration, float numberOfTimes) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_X, 0, offset);
        animator.setDuration(duration);
        // http://blog.csdn.net/jason0539/article/details/16370405 插值器详解
        animator.setInterpolator(new CycleInterpolator(numberOfTimes)); //动画循环播放特定的次数，速率改变沿着正弦曲线
        animator.start();
    }

    /**
     * 中心点为锚点的Y轴摇晃动画
     *
     * @param v
     */
    public static void shakeY(View v) {
        shakeY(v, 10, 1000, 5.0f);
    }

    /**
     * shake y
     *
     * @param v
     * @param offset        针对锚点的相对摇晃距离(px)
     * @param duration
     * @param numberOfTimes 插值器中设置动画循环次数
     */
    public static void shakeY(View v, float offset, long duration, float numberOfTimes) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, 0, offset);
        animator.setDuration(duration);
        animator.setInterpolator(new CycleInterpolator(numberOfTimes));
        animator.start();
    }

    //</editor-fold>
    //<editor-fold desc="呼吸灯">

    /**
     * 呼吸灯效果
     *
     * @param v
     */
    public static void breath(View v) {
        breath(v, 0.0f, 1.0f, 1000);
    }

    /**
     * 呼吸灯效果(透明动画无限循环模式，针对其它动画也可以做成类似于呼吸灯的模式)
     *
     * @param v
     * @param fromRange
     * @param toRange
     * @param duration
     */
    public static void breath(View v, float fromRange, float toRange, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, fromRange, toRange);
        animator.setDuration(duration);
        //设置循环模式 value取值有reverse(相反)/restart
        animator.setRepeatMode(ValueAnimator.REVERSE);
        //设置循环次数 value取值有infinite(无限循环) / 1 2 3...
        animator.setRepeatCount(ValueAnimator.INFINITE);
        // http://blog.csdn.net/jason0539/article/details/16370405 插值器详解
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); //在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        animator.start();
    }

    //</editor-fold>
    //<editor-fold desc="组合动画">


    //设置缩放动画，淡入和淡出
    public static void setScale(View view) {
        //创建一个AnimatorSet对象
        AnimatorSet set = new AnimatorSet();
        //设置动画的时间和效果
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1.2f, 1f, 0.5f, 0.7f, 1f);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1.2f, 1f, 0.5f, 0.7f, 1f);
        set.play(animator_x).with(animator_y);
        //设置他的继续时间
        set.setDuration(500);
        //启动动画
        set.start();
    }

    public static void setScale(View view, Animator.AnimatorListener listener) {
        //创建一个AnimatorSet对象
        AnimatorSet set = new AnimatorSet();
        //设置动画的时间和效果
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1.2f, 1f, 0.5f, 0.7f, 1f);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1.2f, 1f, 0.5f, 0.7f, 1f);
        set.play(animator_x).with(animator_y);
        //设置他的继续时间
        set.setDuration(500);
        set.addListener(listener);
        //启动动画
        set.start();
    }

    //</editor-fold>
}
