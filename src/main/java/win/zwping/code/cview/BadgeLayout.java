package win.zwping.code.cview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import win.zwping.code.R;
import win.zwping.code.cview.pi.IBadgeLayout;
import win.zwping.code.utils.ViewUtil;

public class BadgeLayout extends ConstraintLayout implements IBadgeLayout {

    private View badgeView;
    private ConstraintSet set;

    public BadgeLayout(Context context) {
        this(context, null);
    }

    public BadgeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (set == null) set = new ConstraintSet();
        badgeView = ViewUtil.inflate(getContext(), R.layout.cus_badge_view);

        int c = getChildCount();
        if (c != 0 && badgeView != null) removeView(badgeView);
        addView(badgeView, getChildCount());
        badgeView.setId(R.id.cus_badge_view);

        set.connect(badgeView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(badgeView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.constrainHeight(badgeView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(badgeView.getId(), ConstraintSet.WRAP_CONTENT);
        set.applyTo(this);
    }

    private void initView(AttributeSet attrs){

    }


//    @Override
//    public BadgeLayout setBadgeViewWH(int wDp, int hDp) {
//        return this;
//    }
//
//    @Override
//    public BadgeLayout setBadgeViewWH(int wDp) {
//        return this;
//    }
//
//    @Override
//    public BadgeLayout setBadgeView(View view) {
//        return this;
//    }
//
//    @Override
//    public BadgeLayout setBadgeView(int id) {
//        return this;
//    }
//
//    @Override
//    public View getBadgeView() {
//        return badgeView;
//    }
}
