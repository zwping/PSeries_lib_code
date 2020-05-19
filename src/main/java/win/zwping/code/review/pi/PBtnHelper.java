package win.zwping.code.review.pi;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.comm.CommCallback;
import win.zwping.code.review.re.TimerSup;
import win.zwping.code.review.PButton;
import win.zwping.code.utils.LogUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-27 16:55:39 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class PBtnHelper extends IHelper<PBtnHelper, PButton> {

    //<editor-fold desc="公共API">
    public interface IPBtn {
        PButton startCountDown(@Nullable LifecycleOwner owner);

        PButton stopCountDown();

        PButton setGone(boolean visible);
    }
    //</editor-fold>

    @Override
    public PBtnHelper initAttrs(PButton view, @Nullable AttributeSet attrs) {
        v = view;
        if (null != attrs) {
            TypedArray array = v.getContext().obtainStyledAttributes(attrs, R.styleable.PButton);
            try {
                cdTime = array.getInt(R.styleable.PButton_p_cd_time, 60);
                cdDefaultTxt = getAttrsString(array, R.styleable.PButton_p_cd_default_txt, "获取验证码");
                cdHintTxt = getAttrsString(array, R.styleable.PButton_p_cd_hint_txt, "%sS");
                cdReGetTxt = getAttrsString(array, R.styleable.PButton_p_cd_re_get_txt, "重新获取验证码");
                model = array.getInt(R.styleable.PButton_p_model, 0);
                if (model == 1) initCountDown(); // 倒计时模式
            } finally {
                array.recycle();
            }
        }
        return this;
    }

    // 当前Btn模式，0 -> non 1 -> 倒计时cd
    private int model = 0;
    ///////////////// 倒计时功能 //////////////////////
    private int cdTime; // 60
    private String cdDefaultTxt; // "获取验证码"
    private String cdHintTxt; // "剩余%sS"
    private String cdReGetTxt; // "重新获取验证码"

    private TimerSup timer;

    private void initCountDown() {
        if (model == 1) {
            v.setText(cdDefaultTxt, TextView.BufferType.EDITABLE);
            stopCountDown();
        }
    }

    public void startCountDown(@Nullable LifecycleOwner owner) {
        if (model == 1) {
            v.setEnabled(false);
            timer = new TimerSup(timerSup -> {
                if(timerSup == null) return;
                if (timerSup.count >= cdTime) {
                    stopCountDown();
                    v.setText(cdReGetTxt);
                } else
                    v.setText(String.format(cdHintTxt, cdTime - timerSup.count));
            }, 0, 1000).schedule(owner);
        }
    }


    public void stopCountDown() {
        v.setEnabled(true);
        if(null != timer) timer.cancel();
    }

    ///////////////////////////////////////
    private String getAttrsString(TypedArray array, @StyleableRes int index, String defaultTxt) {
        String txt = array.getString(index);
        return isEmpty(txt) ? defaultTxt : txt;
    }

}
