package win.zwping.code.review.pi;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PEditText;
import win.zwping.code.utils.ImageUtil;
import win.zwping.code.utils.RegexUtil;
import win.zwping.code.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.EmptyUtil.*;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-04-01 14:30:37 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PEtHelper extends IHelper<PEtHelper, PEditText> {

    //<editor-fold desc="Public Interface API">

    public interface IPEt {

        PEditText setMaxLength(int length);

        int getMaxLength();

        String getContent();

        /*** 0 phone / 1 psw / 2 verify code / 3 英文键盘 ***/
        PEditText setRegexType(int type);

        boolean getCommRegex();

        PEditText clearContent();

        PEditText showHideTextOfPsw(Boolean show);

        Boolean isPswShowing();

        // 清空 / 显示密码 (icon显示存在bug(只允许单行后一直输入文字可现))
        PEditText setClearTextEnable(boolean enable);

        PEditText setPswToggleEnable(boolean enable);

        PEditText setClearIconResId(@DrawableRes int id);

        PEditText setPswToggleIconResId(@DrawableRes int showId, @DrawableRes int hideId);

        PEditText setClearIconPadding(int dp);

        PEditText setPswToggleIconPadding(int dp);

        PEditText setClearIconTint(int tintColor);

        PEditText setPswToggleIconTint(int tintColor);

        void setSuperPadding(int left, int top, int right, int bottom);

        PEditText setOnClearListener(OnClearListener listener);

        PEditText setOnPswToggleListener(OnPswToggleListener listener);

        //////////////
        PEditText setBold();

        PEditText setGone(boolean visible);

    }
    //</editor-fold>

    /*** 一些常用的内容判断 ***/
    private int regexType;

    /*** 常用的判断中，验证码的长度 -> 默认4 ***/
    private int verifyCodeLength;


    public Boolean clearTextEnable = false, pswToggleEnable = false;
    private int clearDrawable, showPswDrawable, hidePswDrawable;
    private Bitmap clearBitmap, showPswBitmap, hidePswBitmap;
    public int clearIconPadding, pswIconPadding; // margin_right 5dp
    public int clearIconTint, pswIconTint;
    public OnClearListener onClearListener;
    public OnPswToggleListener onPswToggleListener;
    private int initialPr; // 初始的paddingRight  规避onMeasure中setPadding问题

    @Override
    public PEtHelper initAttrs(PEditText view, @Nullable AttributeSet attrs) {
        v = view;
        if (null != attrs) {
            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PEditText);
            try {
                setRegexType(array.getInt(R.styleable.PEditText_p_regexType, -1));
                verifyCodeLength = array.getInt(R.styleable.PEditText_p_verify_code_length, 4);

                clearTextEnable = array.getBoolean(R.styleable.PEditText_p_clear_text_enable, false);
                pswToggleEnable = array.getBoolean(R.styleable.PEditText_p_psw_toggle_enable, false);
                clearDrawable = array.getResourceId(R.styleable.PEditText_p_clear_drawable, R.drawable.ic_clear_24dp);
                showPswDrawable = array.getResourceId(R.styleable.PEditText_p_show_psw_drawable, R.drawable.ic_show_password_24dp);
                hidePswDrawable = array.getResourceId(R.styleable.PEditText_p_hide_psw_drawable, R.drawable.ic_hide_password_24dp);
                int right = dp2px(v.getContext(), 5);
                clearIconPadding = array.getDimensionPixelSize(R.styleable.PEditText_p_clear_icon_padding, right);
                pswIconPadding = array.getDimensionPixelSize(R.styleable.PEditText_p_psw_icon_padding, right);
                int c = v.getCurrentHintTextColor();
                clearIconTint = array.getColor(R.styleable.PEditText_p_clear_icon_tint, c);
                pswIconTint = array.getColor(R.styleable.PEditText_p_psw_icon_tint, c);
            } finally {
                array.recycle();
            }
        }
        if (clearTextEnable) setClearIconResId(clearDrawable);
        if (pswToggleEnable) setPswToggleIconResId(showPswDrawable, hidePswDrawable);
        if (clearTextEnable || pswToggleEnable) initialPr = v.getPaddingRight();
        return this;
    }

    public void setRegexType(int type) {
        regexType = type;
        switch (regexType) {
            case 0: // Phone
            case 2: // Verify Code
                v.setMaxLines(1);
                v.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 1: // Psw
                v.setMaxLines(1);
                v.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                v.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case 3: // 英文键盘
                 v.setMaxLines(1);
                 v.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }
    }


    /*** 托管 - 记住hint显示错误提示语 ***/
    public boolean getCommRegex() {
        if (-1 == regexType) {
            return true;
        } else {
            if (isEmpty(v.getContent())) {
                ToastUtil.showShort(v.getHint());
                return false;
            }
            switch (regexType) {
                case 0: // Phone
                    if (!RegexUtil.isMobileSimple(v.getContent())) {
                        ToastUtil.showShort("请输入正确的手机号");
                        return false;
                    }
                    break;
                case 1: // Simple Psw
                    if (v.getContent().length() < 6) {
                        ToastUtil.showShort("请输入不低于6位的密码");
                        return false;
                    }
                    if (v.getContent().length() > 18) {
                        ToastUtil.showShort("请输入不大于18位的密码");
                        return false;
                    }
                    break;
                case 2: // Simple Verify Code
                    if (v.getContent().length() != verifyCodeLength) {
                        ToastUtil.showShort("请输入正确的验证码");
                        return false;
                    }
                    break;
                case 3: // Non Null
                    if (isEmpty(v.getContent())) {
                        ToastUtil.showShort(v.getHint());
                        return false;
                    }
                    break;
            }
            return true;
        }
    }

    public boolean getDefCommRegex(){
        if (isEmpty(v.getContent())) {
            ToastUtil.showShort(v.getHint());
            return false;
        }
        return true;
    }

    public void setMaxLength(int length) {
        if (length < 1) return;
        v.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    @SuppressLint("CheckResult")
    public int getMaxLength() {
        final AtomicReference<Integer> length = new AtomicReference<>(0);
        Observable.fromArray(v.getFilters())
                .filter(new Predicate<InputFilter>() {
                    @Override
                    public boolean test(InputFilter inputFilter) throws Exception {
                        return inputFilter.getClass().getName().equals("android.text.InputFilter$LengthFilter");
                    }
                })
                .subscribe(new Consumer<InputFilter>() {
                               @Override
                               public void accept(final InputFilter inputFilter) throws Exception {
                                   Observable.fromArray(inputFilter.getClass().getDeclaredFields())
                                           .filter(new Predicate<Field>() {
                                               @Override
                                               public boolean test(Field field) throws Exception {
                                                   return field.getName().equals("mMax");
                                               }
                                           })
                                           .subscribe(new Consumer<Field>() {
                                               @Override
                                               public void accept(Field field) throws Exception {
                                                   field.setAccessible(true);
                                                   length.set((Integer) field.get(inputFilter));
                                               }
                                           });
                               }
                           }
                );
        return length.get();
    }

    public String getContent() {
        Editable s = v.getText();
        return isEmpty(s) ? null : s.toString().trim();
    }

    //<editor-fold desc="清空 / 显示密码">


    public void onDraw(Canvas canvas) {
        if (clearTextEnable && isNotEmptys(clearBitmap, getContent()))
            canvas.drawBitmap(clearBitmap, clearIconRight() - clearBitmap.getWidth(), clearIconTop(), null);
        if (pswToggleEnable && isNotEmptys(showPswBitmap, hidePswBitmap))
            canvas.drawBitmap(isPswShowing() ? hidePswBitmap : showPswBitmap, pswIconRight() - maxPswW(), pswIconTop(), null);
    }

    public void onTouchEvent(MotionEvent event) {
        if (v.isEnabled() && event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            boolean isAreaX, isAreaY = true; // 所有的y轴均有效
            if (clearTextEnable && isNotEmpty(getContent())) {
                int r = clearIconRight(), t = clearIconTop();
                isAreaX = x >= r - clearBitmap.getWidth() && x <= r;
                isAreaY = true;
                if (isAreaX && isAreaY) {
                    clearContent();
                    if (null != onClearListener) onClearListener.onClear(v);
                }
            }
            if (pswToggleEnable) {
                int r = pswIconRight(), t = pswIconTop();
                isAreaX = x >= r - maxPswW() && x <= r;
                isAreaY = true;
                if (isAreaX && isAreaY) {
                    togglePswShow();
                    if (null != onPswToggleListener)
                        onPswToggleListener.onShowHidePsw(v, !isPswShowing());
                }
            }
        }
    }

    public void setPadding(int right) {
        if (clearTextEnable || pswToggleEnable) initialPr = right; // java调用了setPadding()
    }

    public void onMeasure() {
        v.setSuperPadding(v.getPaddingLeft(),
                v.getPaddingTop(),
                (clearTextEnable || pswToggleEnable) ?
                        (clearTextEnable ? clearBitmap.getWidth() + clearIconPadding : 0) + (pswToggleEnable ? maxPswW() + pswIconPadding : 0) + initialPr
                        : v.getPaddingRight(),
                v.getPaddingBottom());

    }

    public void clearContent() {
        v.setText(null);
    }

    public void showHidePsw(Boolean show) {
        v.setTransformationMethod(show ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
    }

    public void togglePswShow() {
        showHidePsw(!isPswShowing());
    }

    public Boolean isPswShowing() {
        return v.getTransformationMethod() == PasswordTransformationMethod.getInstance();
    }

    public void setClearIconResId(@DrawableRes int id) {
        clearBitmap = ImageUtil.getBitmap(v.getContext(), id, clearIconTint);
    }

    public void setPswToggleIconResId(@DrawableRes int showId, @DrawableRes int hideId) {
        showPswBitmap = ImageUtil.getBitmap(v.getContext(), showId, pswIconTint);
        hidePswBitmap = ImageUtil.getBitmap(v.getContext(), hideId, pswIconTint);
    }


    public int clearIconTop() {
        return clearTextEnable ? (v.getMeasuredHeight() - clearBitmap.getHeight()) >> 1 : 0;
    }

    private int clearIconRight() {
        return clearTextEnable ? v.getMeasuredWidth() - clearIconPadding - (pswToggleEnable ? maxPswW() + pswIconPadding : 0) : 0;
    }

    private int pswIconTop() {
        return pswToggleEnable ? (v.getMeasuredHeight() - maxPswH()) >> 1 : 0;
    }

    private int pswIconRight() {
        return pswToggleEnable ? v.getMeasuredWidth() - pswIconPadding : 0;
    }

    private int maxPswH() {
        return Math.max(showPswBitmap.getHeight(), hidePswBitmap.getHeight());
    }

    private int maxPswW() {
        return Math.max(showPswBitmap.getWidth(), hidePswBitmap.getWidth());
    }

    //</editor-fold>

}
