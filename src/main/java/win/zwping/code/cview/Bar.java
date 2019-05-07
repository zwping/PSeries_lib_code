package win.zwping.code.cview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import win.zwping.code.R;
import win.zwping.code.cview.pi.IBar;
import win.zwping.code.review.PImageView;
import win.zwping.code.review.PTextView;
import win.zwping.code.utils.ViewUtil;

import static win.zwping.code.utils.ConversionUtil.dp2px;
import static win.zwping.code.utils.ConversionUtil.px2dp;

/**
 * <p>describe：自定义状态栏布局 Toolbar替换类
 * <p>    note：
 * <p>    note：架构描述{@link IBar}
 * <p>    note：
 * <p> @author：zwp on 2019-03-22 16:59:12 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class Bar extends ConstraintLayout implements IBar {

    private RelativeLayout returnRl, menuRl;
    private PTextView titlePTv, returnPtv, menuPtv;
    private PImageView returnPiv, menuPiv;
    private ArrowView returnArrow;
    private View bottomLineV;

    public Bar(Context context) {
        this(context, null);
    }

    public Bar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.cview_bar, this);
        returnRl = findViewById(R.id.return_rl);
        menuRl = findViewById(R.id.menu_rl);
        titlePTv = findViewById(R.id.title_ptv);
        returnPtv = findViewById(R.id.return_txt_ptv);
        menuPtv = findViewById(R.id.menu_txt_ptv);
        returnPiv = findViewById(R.id.return_img_piv);
        menuPiv = findViewById(R.id.menu_img_piv);
        returnArrow = findViewById(R.id.return_arrow);
        bottomLineV = findViewById(R.id.bottom_line_v);

        if (null != attrs) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Bar);
            try {
                setTitle(array.getString(R.styleable.Bar_p_title_txt));
                setReturnTxt(array.getString(R.styleable.Bar_p_return_txt));
                setReturnPivResId(array.getResourceId(R.styleable.Bar_p_return_piv_res_id, 0));
                int menuPivResId = array.getResourceId(R.styleable.Bar_p_menu_piv_res_id, 0);
                if (menuPivResId != 0) setMenuPivResId(menuPivResId);
                setMenuTxt(array.getString(R.styleable.Bar_p_menu_txt));
                setTitlePadding(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_title_padding, dp2px(getContext(), 50)))); // 默认50
                setReturnPadding(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_return_padding, dp2px(getContext(), 12)))); // 默认12
                setMenuPadding(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_menu_padding, dp2px(getContext(), 12)))); // 默认12
                setReturnArrowWH(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_return_arrow_wh, dp2px(getContext(), 18)))); // 默认18
                setReturnPivWH(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_return_piv_wh, 0)));
                setMenuPivWH(px2dp(getContext(), array.getDimensionPixelSize(R.styleable.Bar_p_menu_piv_wh, 0)));
                setTitleColor(array.getColor(R.styleable.Bar_p_title_color, Color.BLACK)); // 默认黑色
                setTitleSize(px2dp(getContext(), array.getColor(R.styleable.Bar_p_title_size, dp2px(getContext(), 15)))); // 默认15dp
                setReturnTxtColor(array.getColor(R.styleable.Bar_p_return_txt_color, Color.BLACK)); // 默认黑色
                setReturnTxtSize(px2dp(getContext(), array.getColor(R.styleable.Bar_p_return_txt_size, dp2px(getContext(), 12)))); // 默认12dp
                setMenuTxtColor(array.getColor(R.styleable.Bar_p_menu_txt_color, Color.BLACK)); // 默认黑色
                setMenuTxtSize(px2dp(getContext(), array.getColor(R.styleable.Bar_p_menu_txt_size, dp2px(getContext(), 12)))); // 默认12dp
                setReturnArrowColor(array.getColor(R.styleable.Bar_p_return_arrow_color, Color.GRAY)); // 默认灰色
                setBottomLineColor(array.getColor(R.styleable.Bar_p_bottom_line_color, Color.parseColor("#dedede"))); // 默认#dedede
                setReturnVisibility(array.getInt(R.styleable.Bar_p_return_visibility, 1) == 1);
                setReturnArrowVisibility(array.getInt(R.styleable.Bar_p_return_arrow_visibility, 1) == 1);
                setMenuVisibility(array.getInt(R.styleable.Bar_p_menu_visibility, 1) == 1);
                setBottomLineVisibility(array.getInt(R.styleable.Bar_p_bottom_line_visibility, 1) == 1);
            } finally {
                array.recycle();
            }
        }

        // setReturnClickListener会自动拦截该方法
        returnRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Activity activity = (Activity) getContext();
                    if (null != activity) activity.finish();
                } catch (ClassCastException e) {
                }
            }
        });
    }

    @Override
    public PTextView getTitlePtv() {
        return titlePTv;
    }

    @Override
    public RelativeLayout getReturnRl() {
        return returnRl;
    }

    @Override
    public PTextView getReturnPtv() {
        return returnPtv;
    }

    @Override
    public PImageView getReturnPiv() {
        return returnPiv;
    }

    @Override
    public ArrowView getReturnArrow() {
        return returnArrow;
    }

    @Override
    public RelativeLayout getMenuRl() {
        return menuRl;
    }

    @Override
    public PTextView getMenuPtv() {
        return menuPtv;
    }

    @Override
    public PImageView getMenuPiv() {
        return menuPiv;
    }

    @Override
    public View getBottomLine() {
        return bottomLineV;
    }

    @Override
    public Bar setTitle(CharSequence title) {
        titlePTv.setText(title);
        return this;
    }

    @Override
    public Bar setReturnTxt(CharSequence returnTxt) {
        returnPtv.setText(returnTxt);
        return this;
    }

    @Override
    public Bar setReturnPivResId(@DrawableRes int resId) {
        if (resId == 0) return this;
        returnPiv.setImageResource(resId);
        return this;
    }

    @Override
    public Bar setReturnPivPath(String path) {
        returnPiv.displayImage(path);
        return this;
    }

    @Override
    public Bar setMenuTxt(CharSequence menuTxt) {
        menuPtv.setText(menuTxt);
        return this;
    }

    @Override
    public Bar setMenuPivResId(@DrawableRes int resId) {
        if (resId == 0) return this;
        menuPiv.setImageResource(resId);
        return this;
    }

    @Override
    public Bar setMenuPivPath(String path) {
        menuPiv.displayImage(path);
        return this;
    }

    @Override
    public Bar setTitlePadding(int paddingDp) {
        int px = dp2px(getContext(), paddingDp);
        titlePTv.setPadding(px, 0, px, 0);
        return this;
    }

    @Override
    public Bar setReturnPadding(int paddingDp) {
        int px = dp2px(getContext(), paddingDp);
        returnRl.setPadding(px, 0, px, 0);
        return this;
    }

    @Override
    public Bar setMenuPadding(int paddingDp) {
        int px = dp2px(getContext(), paddingDp);
        menuRl.setPadding(px, 0, px, 0);
        return this;
    }

    @Override
    public Bar setReturnArrowWH(int whDp) {
        if (whDp == 0) return this;
        ViewUtil.setViewWH(returnArrow, whDp, whDp);
        return this;
    }

    @Override
    public Bar setReturnPivWH(int whDp) {
        if (whDp == 0) return this;
        ViewUtil.setViewWH(returnPiv, whDp, whDp);
        return this;
    }

    @Override
    public Bar setMenuPivWH(int whDp) {
        if (whDp == 0) return this;
        ViewUtil.setViewWH(menuPiv, whDp, whDp);
        return this;
    }

    @Override
    public Bar setTitleColor(int color) {
        titlePTv.setTextColor(color);
        return this;
    }

    @Override
    public Bar setTitleSize(int sizeDp) {
        titlePTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeDp);
        return this;
    }

    @Override
    public Bar setReturnTxtColor(int color) {
        returnPtv.setTextColor(color);
        return this;
    }

    @Override
    public Bar setReturnTxtSize(int sizeDp) {
        returnPtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeDp);
        return this;
    }

    @Override
    public Bar setMenuTxtColor(int color) {
        menuPtv.setTextColor(color);
        return this;
    }

    @Override
    public Bar setMenuTxtSize(int sizeDp) {
        menuPtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeDp);
        return this;
    }

    @Override
    public Bar setReturnArrowColor(int color) {
        returnArrow.setArrowColor(color).init();
        return this;
    }

    @Override
    public Bar setBottomLineColor(int color) {
        bottomLineV.setBackgroundColor(color);
        return this;
    }

    @Override
    public Bar setReturnVisibility(boolean visibility) {
        returnRl.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public Bar setReturnArrowVisibility(boolean visibility) {
        returnArrow.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public Bar setMenuVisibility(boolean visibility) {
        menuRl.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public Bar setBottomLineVisibility(boolean visibility) {
        bottomLineV.setVisibility(visibility ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public Bar setReturnClickListener(OnClickListener listener) {
        returnRl.setOnClickListener(listener);
        return this;
    }

    @Override
    public Bar setMenuClickListener(OnClickListener listener) {
        menuRl.setOnClickListener(listener);
        return this;
    }
}
