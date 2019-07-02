package win.zwping.code.basic.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import win.zwping.code.basic.pi.IFm;

/**
 * <p>describe：Fragment生命周期
 * <p>    note：
 * <p>    note：isChangeUi / fastClick
 * <p>    note：整个basicFm的架构{@link IFm}
 * <p>    note：
 * <p> @author：zwp on 2019-02-21 16:30:51 mail：1101558280@qq.com web: http://www.zwping.win </p>
 */
public class BasicLifeCycleFm extends Fragment implements IFm.IBasicLifeCycle {

    //<editor-fold desc="保险做法">

    private Boolean isChangeUi = false; // 所有更新UI的方法最好依据@param isCanChangeUi二次封装

    private long lastClick = 0;

    @Override
    public boolean normalClick() {
        if (System.currentTimeMillis() - lastClick <= 500) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean isChangeUi() {
        return isChangeUi;
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    //</editor-fold>
    //<editor-fold desc="生命周期">


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // 判断其是否可见
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) { //触发 onCreateView 后触发该方法
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        isChangeUi = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isChangeUi = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //</editor-fold>
}
