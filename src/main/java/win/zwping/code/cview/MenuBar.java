package win.zwping.code.cview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import win.zwping.code.R;
import win.zwping.code.cview.pi.IMenuBar;
import win.zwping.code.review.PEditText;
import win.zwping.code.review.PImageView;
import win.zwping.code.review.PTextView;
import win.zwping.code.utils.ViewUtil;

import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.ConversionUtil.px2dp;

/**
 * <p>describe：自定义菜单栏
 * <p>    note：
 * <p>    note：架构描述{@link IMenuBar}
 * <p>    note：
 * <p> @author：zwp on 2019-03-22 16:59:07 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class MenuBar extends ConstraintLayout implements IMenuBar {
    private PImageView titleIconPiv;
    private PTextView titlePtv, contentPtv; // 兼容BasicKeyBoardAc#setAutoHideKB()
    private PEditText contentPet;
    private ArrowView arrowV;
    private View topLineV, bottomLineV;

    public MenuBar(Context context) {
        this(context, null);
    }

    public MenuBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.cview_menu_bar, this);
        titleIconPiv = findViewById(R.id.title_icon_piv);
        titlePtv = findViewById(R.id.title_ptv);
        contentPtv = findViewById(R.id.content_ptv);
        contentPet = findViewById(R.id.content_input_ptv);
        arrowV = findViewById(R.id.arrow_v);
        topLineV = findViewById(R.id.top_line_v);
        bottomLineV = findViewById(R.id.bottom_line_v);

        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MenuBar);
            try {
                setTitleIconPivResId(array.getResourceId(R.styleable.MenuBar_p_title_icon, 0));
                setTitleTxt(array.getString(R.styleable.MenuBar_p_title_txt));
                setContentTxt(array.getString(R.styleable.MenuBar_p_content_txt));
                setContentInputTxt(array.getString(R.styleable.MenuBar_p_content_input_txt));
                setContentInputHintTxt(array.getString(R.styleable.MenuBar_p_content_input_hint_txt));
                setTitleIconMarginLeft(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_title_icon_margin_left, dp2px(getContext(), 12))));
                setTitleMarginLeft(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_title_margin_left, dp2px(getContext(), 6))));
                setArrowMarginRight(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_arrow_margin_right, dp2px(getContext(), 12))));
                setContentMarginRight(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_content_margin_right, dp2px(getContext(), 6))));
                setTitleIconWH(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_title_icon_wh, 0)));
                setArrowWH(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_arrow_wh, dp2px(getContext(), 15)))); // 默认18
                setTitleColor(array.getColor(R.styleable.MenuBar_p_title_color, Color.BLACK)); // 默认黑色
                setTitleSize(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_title_size, dp2px(getContext(), 15)))); // 默认13dp
                setContentHintColor(array.getColor(R.styleable.MenuBar_p_content_hint_color, Color.parseColor("#dedede")));
                setContentColor(array.getColor(R.styleable.MenuBar_p_content_color, Color.GRAY));
                setContentSize(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_content_size, dp2px(getContext(), 12))));
                setArrowColor(array.getColor(R.styleable.MenuBar_p_arrow_color, Color.parseColor("#dedede")));
                setTopLineColor(array.getColor(R.styleable.MenuBar_p_top_line_color, Color.parseColor("#dedede")));
                setBottomLineColor(array.getColor(R.styleable.MenuBar_p_bottom_line_color, Color.parseColor("#dedede")));
                setContentIsTextOrInput(array.getBoolean(R.styleable.MenuBar_p_content_is_text_or_input, true)); // 默认为本
                setTitleIconVisibility(array.getInt(R.styleable.MenuBar_p_title_icon_visibility, 0) == 1); // 默认隐藏
                setArrowVisibility(array.getInt(R.styleable.MenuBar_p_arrow_visibility, 1) == 1); // 默认显示
                setTopLineVisibility(array.getInt(R.styleable.MenuBar_p_top_line_visibility, 0) == 1); // 默认隐藏
                setBottomLineVisibility(array.getInt(R.styleable.MenuBar_p_bottom_line_visibility, 1) == 1); // 默认显示
                setBottomLineMargin(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_bottom_line_margin_left, 0)), px2dp(getContext(), array.getDimensionPixelSize(R.styleable.MenuBar_p_bottom_line_margin_right, 0)));
            } finally {
                array.recycle();
            }
        }
    }

    @Override
    public PImageView getTitleIconPiv() {
        return titleIconPiv;
    }

    @Override
    public PTextView getTitlePtv() {
        return titlePtv;
    }

    @Override
    public PTextView getContentPtv() {
        return contentPtv;
    }

    @Override
    public PEditText getContentInputPet() {
        return contentPet;
    }

    @Override
    public ArrowView getArrowV() {
        return arrowV;
    }

    @Override
    public View getTopLineV() {
        return topLineV;
    }

    @Override
    public View getBottomLineV() {
        return bottomLineV;
    }

    @Override
    public MenuBar setTitleIconPivResId(@DrawableRes int resId) {
        if (resId == 0) return this;
        titleIconPiv.setImageResource(resId);
        return this;
    }

    @Override
    public MenuBar setTitleIconPivPath(String path) {
        titleIconPiv.displayImage(path);
        return this;
    }

    @Override
    public MenuBar setTitleTxt(@Nullable CharSequence txt) {
        titlePtv.setText(txt);
        return this;
    }

    @Override
    public MenuBar setContentTxt(CharSequence txt) {
        contentPtv.setText(txt);
        return this;
    }

    @Override
    public MenuBar setContentInputHintTxt(CharSequence txt) {
        contentPet.setHint(txt);
        return this;
    }

    @Override
    public MenuBar setContentInputTxt(CharSequence hint) {
        contentPet.setText(hint);
        return this;
    }

    @Override
    public MenuBar setTitleIconMarginLeft(int dp) {
        ViewUtil.setMargins(titleIconPiv, dp, 0, 0, 0);
        return this;
    }

    @Override
    public MenuBar setTitleMarginLeft(int dp) {
        ViewUtil.setMargins(titlePtv, dp, 0, 0, 0);
        return this;
    }

    @Override
    public MenuBar setContentMarginRight(int dp) {
        ViewUtil.setMargins(contentPtv, 0, 0, dp, 0);
        ViewUtil.setMargins(contentPet, 0, 0, dp, 0);
        return this;
    }

    @Override
    public MenuBar setArrowMarginRight(int dp) {
        ViewUtil.setMargins(arrowV, 0, 0, dp, 0);
        return this;
    }

    @Override
    public MenuBar setTitleIconWH(int whDp) {
        if (whDp == 0) return this;
        ViewUtil.setViewWH(titleIconPiv, whDp, whDp);
        return this;
    }

    @Override
    public MenuBar setArrowWH(int whDp) {
        if (whDp == 0) return this;
        ViewUtil.setViewWH(arrowV, whDp, whDp);
        return this;
    }

    @Override
    public MenuBar setBottomLineMargin(int leftDp, int rightDp) {
        if (leftDp != 0 || rightDp != 0)
            ViewUtil.setMargins(bottomLineV, leftDp, 0, rightDp, 0);
        return this;
    }

    @Override
    public MenuBar setTitleColor(int color) {
        titlePtv.setTextColor(color);
        return this;
    }

    @Override
    public MenuBar setTitleSize(int sizeDp) {
        titlePtv.setTextSize(sizeDp);
        return this;
    }

    @Override
    public MenuBar setContentColor(int color) {
        contentPtv.setTextColor(color);
        contentPet.setTextColor(color);
        return this;
    }

    @Override
    public MenuBar setContentHintColor(int color) {
        contentPet.setHintTextColor(color);
        return this;
    }

    @Override
    public MenuBar setContentSize(int sizeDp) {
        contentPet.setTextSize(sizeDp);
        contentPtv.setTextSize(sizeDp);
        return this;
    }

    @Override
    public MenuBar setArrowColor(int color) {
        arrowV.setArrowColor(color).init();
        return this;
    }

    @Override
    public MenuBar setTopLineColor(int color) {
        topLineV.setBackgroundColor(color);
        return this;
    }

    @Override
    public MenuBar setBottomLineColor(int color) {
        bottomLineV.setBackgroundColor(color);
        return this;
    }

    @Override
    public MenuBar setContentIsTextOrInput(boolean text) {
        contentPtv.setVisibility(text ? View.VISIBLE : View.GONE);
        contentPet.setVisibility(!text ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public MenuBar setTitleIconVisibility(boolean visibility) {
        titleIconPiv.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public MenuBar setArrowVisibility(boolean visibility) {
        arrowV.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public MenuBar setTopLineVisibility(boolean visibility) {
        topLineV.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public MenuBar setBottomLineVisibility(boolean visibility) {
        bottomLineV.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public String getContent() {
        return contentPtv.getVisibility() == GONE ? contentPet.getContent() : contentPtv.getContent();
    }
}
