package win.zwping.code.review.re;

import android.text.Editable;

/**
 * <p>describe：TextWatcher 文本变化监听拦截，只爆发常用的“文本变化后”监听{@link #afterTextChanged(Editable)}
 * <p>    note：
 * <p> @author：zwp on 2017/11/24 0024 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public abstract class TextWatcher implements android.text.TextWatcher {

    /**
     * 文本改变之前
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * 文本内容正在改变
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
