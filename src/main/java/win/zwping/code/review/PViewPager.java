package win.zwping.code.review;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import win.zwping.code.review.pi.PViewPagerHelper;

import java.util.List;

/**
 * <p>describe：
 * <p>    note：
 * <p>    note：禁止滑动 / 循环滚动
 * <p>    note：
 * <p> @author：zwp on 2019-02-14 11:17:31 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PViewPager extends ViewPager implements PViewPagerHelper.IPViewPager {

    private PViewPagerHelper helper;

    public PViewPager(@NonNull Context context) {
        this(context, null);
    }

    public PViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        helper = new PViewPagerHelper().initAttrs(this, attrs);
    }

    @Override
    public PViewPager setAdapterView(List<View> list) {
        helper.setAdapterView(list);
        return this;
    }

    @Override
    public PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull List<Fragment> fms) {
        helper.setAdapterFm(fm, fms, null, null);
        return this;
    }

    @Override
    public PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull List<Fragment> fms, @Nullable TabLayout tabLayout) {
        helper.setAdapterFm(fm, fms, tabLayout, null);
        return this;
    }

    @Override
    public PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable TabLayout tabLayout, @Nullable final List<CharSequence> list) {
        helper.setAdapterFm(fm, fms, tabLayout, list);
        return this;
    }

    @Override
    public PViewPager setAdapterFmOfBanner(@NonNull FragmentManager fm, @NonNull List<Fragment> fms, @Nullable OnBannerListener listener) {
        helper.setAdapterFmOfBanner(fm, fms, listener);
        return null;
    }

    public void startAutoPlay(Lifecycle lifecycle, int timeOfSeconds) {
        helper.startAutoPlay(lifecycle, timeOfSeconds);
    }


    @Override
    public FragmentStatePagerAdapter getAdapterFm() {
        return (FragmentStatePagerAdapter) getAdapter();
    }

    @Override
    public PViewPager addOnPageChangeListener(OnPageSelected onPageSelected) {
        addOnPageChangeListener(onPageSelected, null, null);
        return this;
    }

    @Override
    public PViewPager addOnPageChangeListener(OnPageSelected onPageSelected, @Nullable OnPageScrolled onPageScrolled, @Nullable OnPageScrollStateChanged onPageScrollStateChanged) {
        helper.addOnPageChangeListener(onPageSelected, onPageScrolled, onPageScrollStateChanged);
        return this;
    }

    ///////////////////////////////////////

    public interface OnBannerListener {
        void onPageSelected(int position, int total);
    }

    ////////////////// 分解OnPageChangeListener ///////////////////
    public interface OnPageScrolled {
        void onPageScrolled(OnPageScrolledEn en);
    }

    // 舔一下kt的lambda  '{ it -> }'
    public static class OnPageScrolledEn {
        private int position;
        private float positionOffset;
        private int positionOffsetPixels;

        public OnPageScrolledEn() {
        }

        public OnPageScrolledEn(int position, float positionOffset, int positionOffsetPixels) {
            this.position = position;
            this.positionOffset = positionOffset;
            this.positionOffsetPixels = positionOffsetPixels;
        }
    }

    public interface OnPageSelected {
        void onPageSelected(int position);
    }

    public interface OnPageScrollStateChanged {
        void onPageScrollStateChanged(int state);
    }
}
