package win.zwping.code.basic.helper;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import win.zwping.code.basic.lifecycle.BasicLifeCycleFm;
import win.zwping.code.basic.pi.IFm;

public abstract class BasicXLazyFm extends BasicLifeCycleFm implements IFm.IBasicLazy {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected AppCompatActivity mActivity;
    protected Bundle savedInstanceState;
    protected LayoutInflater mInflater;
    protected ViewGroup mContainer;
    protected View mContentView;

    private boolean isFirstLoad = true;

    @Override
    public boolean setIsLazy() {
        return true;
    }

    @Override
    public void initData(Bundle bundle) {

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mContainer = container;
        this.mInflater = inflater;
        mContentView = setContentView(mInflater);
        if (!setIsLazy() && isFirstLoad) {
            isFirstLoad = false;
            onCreateViewLazy(savedInstanceState);
        }
        return mContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (setIsLazy() && isFirstLoad) {
            isFirstLoad = false;
            onCreateViewLazy(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }
}
