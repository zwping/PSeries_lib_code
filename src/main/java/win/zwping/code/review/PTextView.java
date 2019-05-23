package win.zwping.code.review;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import win.zwping.code.BuildConfig;
import win.zwping.code.R;
import win.zwping.code.comm.ViewStateColor;
import win.zwping.code.review.pi.ViewStateColorSwitchHelper;
import win.zwping.code.review.re.LinkMovementMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static win.zwping.code.utils.EmptyUtil.isEmpty;
import static win.zwping.code.utils.EmptyUtil.isNotEmpty;

/**
 * <p>describe：
 * <p>    note：控制图片大小及位置
 * <p> @author：zwp on 2017/11/22 0022 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PTextView extends AppCompatTextView implements ViewStateColorSwitchHelper.IVSwitchStatus<PTextView>, LinkMovementMethod.OnChildSpanClickListener {

    private ViewStateColorSwitchHelper helper;

    //<editor-fold desc="内部参数">
    /***内部链接是否被点击 spannableStringBuilder***/
    public boolean mClickableSpanClickEvent;

    //</editor-fold>
    //<editor-fold desc="构造函数">

    public PTextView(Context context) {
        this(context, null);
        initView(null);
    }

    public PTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        initView(attrs);
    }

    public PTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new ViewStateColorSwitchHelper().initAttrs(this, attrs);
        initView(attrs);
    }
    //</editor-fold>
    //<editor-fold desc="内部方法">

    private void initView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PTextView);
            try {
                if (array.getBoolean(R.styleable.PTextView_p_strike_through, false))
                    setStrikeThrough();
            } finally {
                array.recycle();
            }
        }
    }

    public PTextView setStrikeThrough() {
        setPaintFlags(getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        return this;
    }

    public PTextView setBold() {
        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return this;
    }

    @Override
    public boolean performClick() {
        if (mClickableSpanClickEvent) {
            return true;
        }
        return super.performClick();//使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mClickableSpanClickEvent = false;
        return super.onTouchEvent(event);
    }
    //<editor-fold desc="阅读更多模式">
    //</editor-fold>
    //</editor-fold>
    //<editor-fold desc="API">

    /**
     * 获取来自assets文件夹中的字体
     *
     * @param ttfPath assets 中ttf文件的路径
     * @return Typeface
     */
    public Typeface getTypefaceFromAssets(String ttfPath) {
        return Typeface.createFromAsset(getContext().getAssets(), ttfPath);
    }

    /**
     * 设置着色器
     * <p>渐变 / 图片背景</p>
     *
     * @param shader 着色器
     */
    public void setShader(Shader shader) {
        getPaint().setShader(shader);
    }

    /**
     * 加载网页格式的文本
     *
     * @param htmlText           网页格式的文本
     * @param loadLocalResources 是否支持加载本地图片资源 eg： <img src="drawableName">
     */
    public void setHtmlText(String htmlText, boolean loadLocalResources) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(loadLocalResources ?
                    Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY, new ResourcesImageGetter(getContext()), null)
                    :
                    Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            setText(loadLocalResources ?
                    Html.fromHtml(htmlText, new ResourcesImageGetter(getContext()), null)
                    :
                    Html.fromHtml(htmlText));
        }
    }

    public void setHtmlText(String htmlText) {
        if (isNotEmpty(htmlText)) setHtmlText(htmlText, false);
    }

    /**
     * 实现超链接效果
     * <p>不如xml中autoLink随心所欲</p>
     * <P>{@link LinkMovementMethod}使用自定义linkMovementMethod解决spannableStringBuilder点击事件和onClickListener同步问题</P>
     * <p><b>使用baseRecyclerViewAdapterHelper套用recyclerView，事件传递还是有点毛病，使用自己定义adapter或监听就没毛病，等待时机再修改（在这多记录一点：viewGroup< TextView< spannableStringBuilder>>>，三层嵌套关系，子项的点击事件是不可以影响到父层的点击）</b></p>
     * <p>
     * <p>this = {@link #click()}</p>
     */
    public PTextView setMovementMethod() {
        setMovementMethod(LinkMovementMethod.getInstance().setOnChildSpanClickListener(this));
        return this;
    }

    /**
     * 设置高亮文本
     *
     * @param text      原文字
     * @param queryText 需要高亮的文字
     * @param color     高亮的文字颜色 改写setSpan()内容即可自我定制高亮文字颜色的体现方法
     */
    public void setHighlightText(@NonNull String text, String queryText, int color) {
        if (isEmpty(queryText)) {
            setText(text);
            return;
        }
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(queryText.toLowerCase()); //转换为小写比对
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 0);
        }

        setText(spannableString);
    }

    /**
     * 设置最大字符长度
     *
     * @param length
     */
    public void setMaxLength(int length) {
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public String getContent() {
        return getText().toString().trim();
    }

    /*** 简化代码，直接在xml中预填%s ***/
    public PTextView setFormat(Object... args) {
        setText(String.format(getContent(), args));
        return this;
    }
    //</editor-fold>
    //<editor-fold desc="html加载本地图片">

    /**
     * html加载drawable中的资源
     * <p>可以自我定制</p>
     */
    private static class ResourcesImageGetter implements Html.ImageGetter {
        private final Context context;

        public ResourcesImageGetter(Context context) {
            this.context = context;
        }

        @Override
        public Drawable getDrawable(String source) {
            int path = context.getResources().getIdentifier(source, "drawable", BuildConfig.APPLICATION_ID);
            Drawable drawable = ContextCompat.getDrawable(context, path);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;
        }
    }
    //</editor-fold>
    //<editor-fold desc="LinkMovementMethod回执">

    @Override
    public void click() { //内部链接被点击 spannableStringBuilder
        mClickableSpanClickEvent = true;
    }
    //</editor-fold>


    @Override
    public PTextView setVStateBgColor(ViewStateColor stateColor) {
        helper.setVStateBgColor(stateColor);
        return this;
    }

    @Override
    public PTextView setVStateTextColor(ViewStateColor stateColor) {
        helper.setVStateTextColor(stateColor);
        return this;
    }
}
