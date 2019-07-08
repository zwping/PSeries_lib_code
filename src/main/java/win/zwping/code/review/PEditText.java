package win.zwping.code.review;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.pi.OnClearListener;
import win.zwping.code.review.pi.OnPswToggleListener;
import win.zwping.code.review.pi.PEtHelper;
import win.zwping.code.review.pi.ViewStateColorSwitchHelper;
import win.zwping.code.utils.AcUtil;
import win.zwping.code.utils.KeyboardUtil;

import static win.zwping.code.utils.ConversionUtil.dp2px;

/**
 * <p>describe：
 * <p>    note：右下角计数控件{@link win.zwping.code.cview.EtWordCountLayout}
 * <p>    note：
 * <p> @author：zwp on 2017/11/20 0020 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PEditText extends AppCompatEditText implements ViewStateColorSwitchHelper.IVSwitchStatus<PEditText>, PEtHelper.IPEt {

    private ViewStateColorSwitchHelper helper;
    private PEtHelper helper1;

    public PEditText(Context context) {
        this(context, null);
    }

    public PEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new ViewStateColorSwitchHelper().initAttrs(this, attrs);
        helper1 = new PEtHelper().initAttrs(this, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        helper1.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        helper1.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        helper1.setPadding(right);
    }

    @Override
    public void setSuperPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        helper1.onMeasure();
    }

    @Override
    public PEditText setMaxLength(int length) {
        helper1.setMaxLength(length);
        return this;
    }

    @Override
    public int getMaxLength() {
        return helper1.getMaxLength();
    }

    public String getContent() {
        return helper1.getContent();
    }

    /*** 0 phone / 1 psw / 2 verify code / 3 英文键盘 ***/
    @Override
    public PEditText setRegexType(int type) {
        helper1.setRegexType(type);
        return this;
    }

    @Override
    public boolean getCommRegex() {
        return helper1.getCommRegex();
    }

    @Override
    public PEditText clearContent() {
        helper1.clearContent();
        return this;
    }

    @Override
    public PEditText showHideTextOfPsw(Boolean show) {
        helper1.showHidePsw(show);
        return this;
    }

    @Override
    public Boolean isPswShowing() {
        return helper1.isPswShowing();
    }

    @Override
    public PEditText setClearTextEnable(boolean enable) {
        helper1.clearTextEnable = enable;
        return this;
    }

    @Override
    public PEditText setPswToggleEnable(boolean enable) {
        helper1.pswToggleEnable = enable;
        return this;
    }

    @Override
    public PEditText setClearIconResId(int id) {
        helper1.setClearIconResId(id);
        return this;
    }

    @Override
    public PEditText setPswToggleIconResId(int showId, int hideId) {
        helper1.setPswToggleIconResId(showId, hideId);
        return this;
    }

    @Override
    public PEditText setClearIconPadding(int dp) {
        helper1.clearIconPadding = dp2px(dp);
        return this;
    }

    @Override
    public PEditText setPswToggleIconPadding(int dp) {
        helper1.pswIconPadding = dp2px(dp);
        return this;
    }

    @Override
    public PEditText setClearIconTint(int tintColor) {
        helper1.clearIconTint = tintColor;
        return this;
    }

    @Override
    public PEditText setPswToggleIconTint(int tintColor) {
        helper1.pswIconTint = tintColor;
        return this;
    }

    @Override
    public PEditText setOnClearListener(OnClearListener listener) {
        helper1.onClearListener = listener;
        return this;
    }

    @Override
    public PEditText setOnPswToggleListener(OnPswToggleListener listener) {
        helper1.onPswToggleListener = listener;
        return this;
    }

    @Override
    public PEditText setBold() {
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return this;
    }

    @Override
    public PEditText setGone(boolean visible) {
        setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    @Override
    public PEditText setVStateBgColor(ViewStateColor stateColor) {
        helper.setVStateBgColor(stateColor);
        return this;
    }

    @Override
    public PEditText setVStateTextColor(ViewStateColor stateColor) {
        helper.setVStateTextColor(stateColor);
        return this;
    }

    public PEditText setOnEditorActionSearchListener(final OnEditorActionSearchListener lis) {
        setMaxLines(1);
        setInputType(InputType.TYPE_CLASS_TEXT);
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyboardUtil.hideSoftInput(AcUtil.getTopActivity());
                    lis.onSearch(getContent());
                }
                return false;
            }
        });
        return this;
    }

    ///////////////////////////////////////
    public interface OnEditorActionSearchListener {
        void onSearch(String txt);
    }

}
