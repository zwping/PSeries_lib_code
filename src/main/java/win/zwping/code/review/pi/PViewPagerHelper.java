package win.zwping.code.review.pi;

import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import win.zwping.code.basic.IHelper;
import win.zwping.code.review.PViewPager;
import win.zwping.code.utils.LifecycleUtil;
import win.zwping.code.utils.LogUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static win.zwping.code.utils.EmptyUtil.isEmptysII;
import static win.zwping.code.utils.EmptyUtil.isNotEmpty;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-19 10:18:40 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class PViewPagerHelper extends IHelper<PViewPagerHelper, PViewPager> implements LifecycleUtil.OnLifecycleListener {

    public interface IPViewPager {
        PViewPager setAdapterView(List<View> list);

        PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms);

        PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable TabLayout tabLayout);

        PViewPager setAdapterFm(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable TabLayout tabLayout, @Nullable final List<CharSequence> list);

        PViewPager setAdapterFmOfBanner(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable PViewPager.OnBannerListener listener);

        FragmentStatePagerAdapter getAdapterFm();

        PViewPager addOnPageChangeListener(PViewPager.OnPageSelected onPageSelected);

        PViewPager addOnPageChangeListener(PViewPager.OnPageSelected onPageSelected,@Nullable PViewPager.OnPageScrolled onPageScrolled,@Nullable PViewPager.OnPageScrollStateChanged onPageScrollStateChanged);

    }

    @Override
    public PViewPagerHelper initAttrs(PViewPager view, @Nullable AttributeSet attrs) {
        v = view;
        return this;
    }

    public void setAdapterView(final List<View> list) {
        v.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(list.get(position), 0);
                return list.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(list.get(position));
            }
        });
    }

    public void setAdapterFm(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable TabLayout tabLayout, @Nullable final List<CharSequence> list) {
        if (isEmptysII(fm, fms)) {
            LogUtil.i("参数有误");
            return;
        }
        if (isNotEmpty(list) && (isEmptysII(fm, fms, list) || fms.size() != list.size())) {
            LogUtil.i("参数有误");
            return;
        }

        if (null != tabLayout) tabLayout.setupWithViewPager(v);
        v.setAdapter(new FragmentStatePagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fms.get(position);
            }

            @Override
            public int getCount() {
                return fms.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (isNotEmpty(list)) return list.get(position);
                else return super.getPageTitle(position);
            }
        });
    }

    public void setAdapterFmOfBanner(@NonNull FragmentManager fm, @NonNull final List<Fragment> fms, @Nullable final PViewPager.OnBannerListener listener) {
        if (isEmptysII(fm, fms) || fms.size() < 3) { // < 3 必须手动规范外部fm的顺序(0,1,2)包装为(2,0,1,2,0)
            LogUtil.i("参数有误");
            return;
        }
        v.setAdapter(new FragmentStatePagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fms.get(position);
            }

            @Override
            public int getCount() {
                return fms.size();
            }
        });

        final int max = v.getAdapterFm().getCount();
        final int maxIndex = max - 3; // 真实的最大下标

        v.setOffscreenPageLimit(max); // 防止闪屏，且你的fm不建议使用懒加载
        v.setCurrentItem(1);
        v.addOnPageChangeListener(new win.zwping.code.review.re.OnPageChangeListener() {

            @Override
            public void onPageSelected(int p) {
                if (null != listener)
                    listener.onPageSelected(p == 0 ? maxIndex : p == max - 1 ? 0 : p - 1, max - 2);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE: // 空闲状态，没有任何滚动正在进行（表明完成滚动）
                        if (v.getCurrentItem() == 0) {
                            stopPlay();
                            v.setCurrentItem(fms.size() - 2, false);
                        } else if (v.getCurrentItem() == max - 1) {
                            stopPlay();
                            v.setCurrentItem(1, false);
                        } else {
                            if (isAutoPlay) startAutoPlay(lifecycle, time);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING: // 正在拖动page状态
                        stopPlay();
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING: // 手指已离开屏幕，自动完成剩余的动画效果
                        break;
                }
            }
        });
    }

    public void addOnPageChangeListener(final PViewPager.OnPageSelected onPageSelected,@Nullable final PViewPager.OnPageScrolled onPageScrolled,@Nullable final PViewPager.OnPageScrollStateChanged onPageScrollStateChanged) {
        v.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (null != onPageScrolled)
                    onPageScrolled.onPageScrolled(new PViewPager.OnPageScrolledEn(position,positionOffset,positionOffsetPixels));
            }

            @Override
            public void onPageSelected(int position) {
                if (null != onPageSelected) onPageSelected.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (null != onPageScrollStateChanged) onPageScrollStateChanged.onPageScrollStateChanged(state);
            }
        });
    }

    /////////////////////// 定时器 ///////////////////////////

    public void setLifecycle(@Nullable Lifecycle l) {
        if (l != null) LifecycleUtil.setLifecycleListener(l, this);
    }


    private Boolean isAutoPlay = false;
    private Disposable timerRx;
    private int time;
    private Lifecycle lifecycle;

    @SuppressLint("CheckResult")
    public void startAutoPlay(Lifecycle lifecycle, int timeOfSeconds) {
        isAutoPlay = true;
        this.time = timeOfSeconds;
        this.lifecycle = lifecycle;
        if (timerRx == null || timerRx.isDisposed())
            timerRx = Observable.timer(time, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            int next = v.getCurrentItem() + 1;
                            v.setCurrentItem(next >= v.getAdapterFm().getCount() - 3 ? 0 : next);
                            System.out.println(v.getCurrentItem() + "-----" + v.getAdapterFm().getCount() + "----");
                        }
                    });
    }

    public void stopPlay() {
        if (timerRx != null) timerRx.dispose();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        if (isAutoPlay) startAutoPlay(lifecycle, time);
    }

    @Override
    public void onPause() {
        stopPlay();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
