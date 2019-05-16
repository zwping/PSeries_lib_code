package win.zwping.code.review;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.widget.NestedScrollView;
import win.zwping.code.R;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2018/3/23 0023 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PScrollView extends NestedScrollView {

    private boolean banSliding = false;

    private int downY, touchSlop;

    public PScrollView(Context context) {
        super(context);
    }

    public PScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PScrollView);
            try {
                banSliding = array.getBoolean(R.styleable.PScrollView_p_ban_sliding, false);
            } finally {
                array.recycle();
            }
        }
        if (banSliding) touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setHorizontalFadingEdgeEnabled(false); // ScrollView配OverScrollView使用，所以默认禁用这个衰变阴影
        setFillViewport(true); // 默认开启，能够规避一些莫名的Bug
    }

    public void scrollDown() {
        post(new Runnable() {
            @Override
            public void run() {
                fullScroll(FOCUS_DOWN);
            }
        });
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (banSliding) {
            int action = e.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downY = (int) e.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) e.getRawY();
                    if (Math.abs(moveY - downY) > touchSlop) {
                        return true;
                    }
            }
        }
        return super.onInterceptTouchEvent(e);
    }
}
