package win.zwping.code.review.pi;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import win.zwping.code.review.tab_layout.*;

/**
 * <p>describe：
 * <p>    note：
 * <p> @author：zwp on 2019-03-18 14:45:59 mail：1101558280@qq.com web: https://www.zwping.com </p>
 */
public interface IPTabL {

    // 托管 ( TC )：一般都是将简单的逻辑关系封装至一个以TC后缀的类中，通过链式调用可很容易的实现所需的简单功能
    //              但TC类一般为泛型类，获取TC类需要 *强转泛型*

    /*** 简单的功能使用托管( TC )机制 , CViewTC可通过Java代码更改Tab样式 ***/
    <B> CViewTC setCViewTC(B statementBean, @LayoutRes int resId, @NonNull OnSupTabUnSelected<B> unselectedListener);

    <B> CViewTC setCViewTC(B statementBean, @LayoutRes int resId, @NonNull OnSupTabUnSelected<B> unselectedListener, @Nullable OnSupTabSelected<B> selectedListener);

    <B> CViewTC setCViewTC(B statementBean, @LayoutRes int resId, @NonNull OnSupTabUnSelected<B> unselectedListener, @Nullable OnSupTabSelected<B> selectedListener, @Nullable OnSupTabReselected<B> reselectedListener);

    <B> CViewTC getCViewTC();

    interface ICViewTC<B> {
        @Nullable
        List<TabLayout.Tab> getTabs();

        CViewTC<B> setData(@Nullable List<B> list);

        @Nullable
        List<B> getData();

    }
}
