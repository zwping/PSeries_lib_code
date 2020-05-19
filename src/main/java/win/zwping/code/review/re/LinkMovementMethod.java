package win.zwping.code.review.re;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * <p>describe：重写LinkMovementMethod，fix 点击子项textView的OnClickListener也响应
 * <p>    note：
 * <p> @author：zwp on 2017/12/6 0006 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class LinkMovementMethod extends android.text.method.LinkMovementMethod {

    //<editor-fold desc="单例">

    private static LinkMovementMethod sInstance;

    public static LinkMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementMethod();
        }
        return sInstance;
    }
    //</editor-fold>
    //<editor-fold desc="内部参数">

    private OnChildSpanClickListener onChildSpanClickListener;
    //</editor-fold>
    //<editor-fold desc="API">

    /**
     * 设置子项点击监听
     *
     * @param listener
     * @return 链式
     */
    public LinkMovementMethod setOnChildSpanClickListener(OnChildSpanClickListener listener) {
        this.onChildSpanClickListener = listener;
        return this;
    }

    //</editor-fold>
    //<editor-fold desc="重写touchEvent">

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                }

                if (widget instanceof TextView && null != onChildSpanClickListener) { //内部ClickableSpan被点击
                    onChildSpanClickListener.click();
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
                super.onTouchEvent(widget, buffer, event);
                return false;
            }
        }

        return Touch.onTouchEvent(widget, buffer, event);
    }
    //</editor-fold>
    //<editor-fold desc="自定义interface">

    /**
     * 监听spannableStringBuilder的点击
     */
    public interface OnChildSpanClickListener {
        /**
         * 已点击
         */
        void click();
    }
    //</editor-fold>
}
