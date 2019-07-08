package win.zwping.code.review.pi;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import win.zwping.code.R;
import win.zwping.code.basic.IHelper;
import win.zwping.code.utils.frame.AutoDisposeUtil;
import win.zwping.code.review.PButton;
import win.zwping.code.utils.LogUtil;

import static win.zwping.code.utils.EmptyUtil.isEmpty;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-27 16:55:39 mail：1101558280@qq.com web: http://www.zwping.win </p>
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
    private Disposable countDownRx;
    private int cdTime; // 60
    private String cdDefaultTxt; // "获取验证码"
    private String cdHintTxt; // "剩余%sS"
    private String cdReGetTxt; // "重新获取验证码"

    private void initCountDown() {
        if (model == 1) {
            v.setText(cdDefaultTxt, TextView.BufferType.EDITABLE);
            stopCountDown();
        }
    }

    public void startCountDown(@Nullable LifecycleOwner owner) {
        if (model == 1 && (null == countDownRx || countDownRx.isDisposed())) {
            Observable<Long> ob = Observable.intervalRange(0, cdTime + 1, 0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread());
            if (null != owner) ob.as(AutoDisposeUtil.bindLifecycle(owner));
            countDownRx = ob.subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    v.setText(String.format(cdHintTxt, cdTime - aLong));
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    LogUtil.i("倒计时模式下定时器的错误：" + throwable.getMessage());
                    initCountDown();
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    stopCountDown();
                    v.setText(cdReGetTxt);
                }
            }, new Consumer<Disposable>() {
                @Override
                public void accept(Disposable disposable) throws Exception {
                    v.setEnabled(false);
                }
            });
        }
    }


    public void stopCountDown() {
        v.setEnabled(true);
        if (null != countDownRx) countDownRx.dispose();
    }

    ///////////////////////////////////////
    private String getAttrsString(TypedArray array, @StyleableRes int index, String defaultTxt) {
        String txt = array.getString(index);
        return isEmpty(txt) ? defaultTxt : txt;
    }

}
