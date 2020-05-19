package win.zwping.code.cview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import win.zwping.code.R;
import win.zwping.code.review.PEditText;
import win.zwping.code.review.PTextView;
import win.zwping.code.review.re.TextWatcher;

import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.ConversionUtil.sp2px;
import static win.zwping.code.utils.EmptyUtil.isNotEmpty;

/**
 * <p>describe：文本框计数控件
 * <pre>
 *     <EdWordCountLayout layout_height="match_layout" ...>
 *         <PEditText maxLength = "200" ... />
 *     </EdWordCountLayout>
 * </pre>
 * <p>    note：
 * <p>    note：建议：固定高度
 * <p> @author：zwp on 2017/11/24 0024 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public class EtWordCountLayout extends RelativeLayout {

    //<editor-fold desc="内部参数">

    /*** 容器中唯一的子控件 ***/
    private PEditText mPEt;

    /*** 计数的TextView （永远在右下角） ***/
    private PTextView mTvWordCount;
    private RelativeLayout.LayoutParams layoutParams;
    /**
     * attrs
     * 是否启用计数控件 仅仅计数 距离底部的距离 距离右侧的距离 文字的大小 文字的颜色
     */
    private boolean mInputCountEnable, mSingleInputCountEnable;
    private float mCountOffBottom, mCountOffRight, mCountTextSize;
    private int mCountTextColor;
    //</editor-fold>
    //<editor-fold desc="构造函数">

    public EtWordCountLayout(Context context) {
        this(context, null);
    }

    public EtWordCountLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EtWordCountLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    //</editor-fold>
    //<editor-fold desc="内部方法">

    private void initView(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.EtWordCountLayout);
        try {
            mInputCountEnable = array.getBoolean(R.styleable.EtWordCountLayout_p_inputCountEnable, true);
            mSingleInputCountEnable = array.getBoolean(R.styleable.EtWordCountLayout_p_singleInputCountEnable, false);
            mCountOffBottom = array.getDimension(R.styleable.EtWordCountLayout_p_countOffBottom, dp2px(getContext(), 5));
            mCountOffRight = array.getDimension(R.styleable.EtWordCountLayout_p_countOffRight, dp2px(getContext(), 5));
            mCountTextSize = array.getDimension(R.styleable.EtWordCountLayout_p_countTextSize, sp2px(getContext(), 10));
            mCountTextColor = array.getColor(R.styleable.EtWordCountLayout_p_countTextColor, Color.DKGRAY);
        } finally {
            array.recycle();
        }

        mTvWordCount = new PTextView(getContext());
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 判断条件卡的太死，理论上不会导致在measure中重复创建对象
        if (getWordCountEnable()) {
            layoutParams.addRule(ALIGN_PARENT_RIGHT);
            layoutParams.addRule(ALIGN_PARENT_BOTTOM);
            mTvWordCount.setLayoutParams(layoutParams);
            mTvWordCount.setGravity(Gravity.END | Gravity.BOTTOM);
            mTvWordCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCountTextSize);
            mTvWordCount.setTextColor(mCountTextColor);
            mTvWordCount.setPadding(mTvWordCount.getPaddingLeft(), mTvWordCount.getTop(), mTvWordCount.getRight() + (int) mCountOffRight, mTvWordCount.getBottom() + (int) mCountOffBottom);

            addView(mTvWordCount);

            mTvWordCount.setText(
                    mPEt.getText().length() +
                            (mSingleInputCountEnable ? "" : "/" + mPEt.getMaxLength())
            );

            mPEt.addTextChangedListener(textWatcher);
        }
    }

    /*** 是否可进行使用计数控件 ***/
    private boolean getWordCountEnable() {
        return mInputCountEnable &&
                getChildCount() == 1 && // 容器中子控件只允许一个
                getChildAt(0) instanceof PEditText &&  // 并且只能是PEditText
                isNotEmpty(mPEt = (PEditText) getChildAt(0)) &&  // 偷懒的写法
                mPEt.getMaxLength() != 0;  // 计算输入字数 / 子控件必须限制其长度
    }

    /*** et输入监听 ***/
    TextWatcher textWatcher = new TextWatcher() {
        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable s) {
            if (mInputCountEnable)
                mTvWordCount.setText(
                        s.length() +
                                (mSingleInputCountEnable ? "" : "/" + mPEt.getMaxLength())
                );
        }
    };

    //</editor-fold>
}
