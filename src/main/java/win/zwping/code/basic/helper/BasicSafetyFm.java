package win.zwping.code.basic.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import win.zwping.code.basic.lifecycle.BasicLifeCycleFm;
import win.zwping.code.basic.pi.IFm;

/**
 * <p>describe：安全的Fragment
 * <p>    note：
 * <p>    note：getContext / getAc
 * <p>    note：setContentView / getContentView / findViewById
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:57:42 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicSafetyFm extends BasicLifeCycleFm implements IFm.IBasicSafety {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected LayoutInflater inflater;
    private Activity activity;
    private Context context;
    private View contentView;
    private ViewGroup container;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Activity getAc() {
        return activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && null != getFragmentManager()) {  // 系统恢复再恢复FG状态
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) ft.hide(this);
            else ft.show(this);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());  // 系统回收保存FG状态
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null) return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    @Override
    public void setContentView(View view) {
        contentView = view;
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @Nullable
    @Override
    public <V extends View> V findViewById(int id) {
        if (contentView != null) return contentView.findViewById(id);
        return null;
    }

    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
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
