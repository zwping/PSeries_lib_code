package win.zwping.code.basic.helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;

import win.zwping.code.basic.lifecycle.BasicLifeCycleFm;
import win.zwping.code.basic.pi.IFm;


/**
 * 懒加载 fm
 *
 * @deprecated androidx 1.1.0对于FragmentTransaction/FragmentManagerImpl引入了setMaxLifecycle方法，
 * 未对fm的setUserVisibleHint懒加载方法向下兼容(体现在FragmentStatePagerAdapter中)
 */
@Deprecated
public abstract class BasicLazyFm extends BasicLifeCycleFm implements IFm.IBasicLazy {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected AppCompatActivity mActivity;
    protected Bundle savedInstanceState;
    protected LayoutInflater mInflater;
    protected ViewGroup mContainer;
    protected View mContentView;

    protected boolean mIsVisibleToUser;
    protected boolean mIsBusinessDone;
    protected boolean mIsInPager;

    @Override
    public boolean setIsLazy() {
        return true; // 默认懒加载
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsInPager = true;
        if (isVisibleToUser) mIsVisibleToUser = true;
        if (setIsLazy()) {
            if (!mIsBusinessDone && isVisibleToUser && mContentView != null) {
                mIsBusinessDone = true;
                onCreateViewLazy(savedInstanceState);
            }
        }
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.mActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getFragmentManager();
        if (fm == null) return;
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = fm.beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
        initData(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mContainer = container;
        this.mInflater = inflater;
        mContentView = setContentView(mInflater);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (!mIsInPager || !setIsLazy() || mIsVisibleToUser) {
            mIsBusinessDone = true;
            onCreateViewLazy(savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsVisibleToUser = false;
        mIsBusinessDone = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
