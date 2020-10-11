package win.zwping.code.cview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import win.zwping.code.R

/**
 * <pre>
 * describe : 状态布局
 * note     : 支持全局配置
 * note     : 支持Activity, Fragment, View直接调用[wrap]
 * note     : 支持xml开发
 * note     :
 * note     :
 * author   : zwping
 * date     : 2020/10/9 5:04 PM
 * email    : 1101558280@qq.com
 * </pre>
 */
class StateLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    //<editor-fold desc="Init">

    @Deprecated("开启不支持xml内容布局编辑预览")
    private var defaultShowLoading = false // 默认展示loading
    private var retryClickListener: (() -> Unit)? = null
    private var loadingLayoutId = LOADING_LAYOUT_ID // 优先级普通
    private var emptyLayoutId = EMPTY_LAYOUT_ID
    private var errorLayoutId = ERROR_LAYOUT_ID

    private var contentViews: MutableList<View>? = null // 内容布局, 支持直接使用FrameLayout特效
    private val loadingView by lazy { inflater.inflate(loadingLayoutId, null).also { it.visibility = INVISIBLE;addView(it) } } // 使用lazy加载的时间巧妙
    private val emptyView by lazy { inflater.inflate(emptyLayoutId, null).also { it.visibility = INVISIBLE;addView(it) } }
    private val errorView by lazy { inflater.inflate(errorLayoutId, null).also { it.visibility = INVISIBLE;addView(it) } }

    @State
    private var curState = CONTENT // 默认展示内容，就是普通的FrameLayout
    private val inflater by lazy { LayoutInflater.from(getContext()) }
    private var switchTask: SwitchTask? = null

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        try {
            defaultShowLoading = array.getBoolean(R.styleable.StateLayout__default_show_loading, false)
            loadingLayoutId = array.getResourceId(R.styleable.StateLayout__loadingLayoutId, LOADING_LAYOUT_ID) // 优先级中等
            emptyLayoutId = array.getResourceId(R.styleable.StateLayout__emptyLayoutId, EMPTY_LAYOUT_ID)
            errorLayoutId = array.getResourceId(R.styleable.StateLayout__errorLayoutId, ERROR_LAYOUT_ID)
        } finally {
            array.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        contentViews = mutableListOf()
        for (i in 0 until childCount) {
            getChildAt(i)?.also {
                contentViews?.add(it)
                if (defaultShowLoading) it.visibility = View.INVISIBLE // 区分出xml和runtime才可去掉deprecated
            }
        }
    }
    //</editor-fold>
    //<editor-fold desc="API">

    fun showLoadingView(): StateLayout {
        showView(LOADING);return this
    }

    fun showContentView(): StateLayout {
        showView(CONTENT);return this
    }

    fun showEmptyView(txt: CharSequence? = null): StateLayout {
        showView(EMPTY, txt);return this
    }

    fun showErrorView(txt: CharSequence? = null): StateLayout {
        showView(ERROR, txt);return this
    }

    fun init(retryClickListener: () -> Unit): ViewAdjustBuilder {
        return init(retryClickListener, 0, 0, 0)
    }

    fun init(retryClickListener: () -> Unit, @LayoutRes loadingLayoutId: Int = 0, @LayoutRes emptyLayoutId: Int = 0, @LayoutRes errorLayoutId: Int = 0): ViewAdjustBuilder {
        this.retryClickListener = retryClickListener
        if (loadingLayoutId != 0) this.loadingLayoutId = loadingLayoutId // 优先级最高
        if (emptyLayoutId != 0) this.emptyLayoutId = emptyLayoutId
        if (errorLayoutId != 0) this.errorLayoutId = errorLayoutId
        if (defaultShowLoading) showLoadingView() // 兼顾loadingView lazy初始化时机
        return ViewAdjustBuilder(loadingView, emptyView, errorView)
    }

    class ViewAdjustBuilder(private val loadingView: View, private val emptyView: View, private val errorView: View) {
        /**
         * 视图调整  没有过多细化的API, 直接将整个View暴露, 随意怎么整
         */
        fun viewAdjust(lis: (loadingView: View?, emptyView: View?, errorView: View?) -> Unit) {
            lis.invoke(loadingView, emptyView, errorView)
        }
    }
    //</editor-fold>

    //<editor-fold desc="Fun">

    /**
     * 切换对应状态view
     * @param txt 简单修改empty/error状态内的文字, 其对应布局内id必须相同; 如需更多定制修改, [ViewAdjustBuilder.viewAdjust]提供了暴露了更丰富的对象
     */
    private fun showView(@State state: Int, txt: CharSequence? = null) {
        if (curState == state) return
        curState = state
        if (null != switchTask) removeCallbacks(switchTask)
        when (state) {
            LOADING -> switchTask = SwitchTask(loadingView)
            EMPTY -> {
                switchTask = SwitchTask(emptyView)
                if (null != txt) emptyView.findViewById<TextView>(R.id.tv_state_empty_txt)?.text = txt
            }
            ERROR -> {
                switchTask = SwitchTask(errorView)
                if (null != txt) errorView.findViewById<TextView>(R.id.tv_state_error_txt)?.text = txt
                errorView.findViewById<View>(R.id.tv_page_state_retry)?.setOnClickListener { retryClickListener?.invoke() }
            }
            CONTENT -> {
                if (null == retryClickListener) throw RuntimeException("如使用该功能请设置[setRetryListener]完成逻辑闭环")
                switchTask = SwitchTask(views = contentViews)
            }
        }
        post(switchTask)
    }


    private fun showAnim(v: View?) {
        try {
            v?.apply {
                animate().cancel()
                animate().alpha(1f).setDuration(ANIM_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator?) {
                                visibility = View.VISIBLE
                            }
                        }).start()
            }
        } catch (e: Exception) {
            v?.visibility = View.VISIBLE
        }
    }

    private fun hideAnim(v: View?) {
        try {
            v?.apply {
                animate().cancel()
                animate().alpha(0f).setDuration(ANIM_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                visibility = View.INVISIBLE
                            }
                        }).start()
            }
        } catch (e: Exception) {
            v?.visibility = View.INVISIBLE
        }
    }


    inner class SwitchTask(private var view: View? = null, private var views: MutableList<View>? = null) : Runnable {

        override fun run() {
            if (null == views) views = mutableListOf()
            views?.also {
                if (null != view) it.add(view!!)
                if (it.size == 0) return
                for (i in 0 until childCount) {
                    val v = getChildAt(i)
                    if (v !in it) hideAnim(v)
                }
                it.forEach { showAnim(it) }
            }
        }
    }

    @IntDef(LOADING, EMPTY, ERROR, CONTENT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State
    //</editor-fold>

    //<editor-fold desc="Static 全局配置">

    companion object {
        // 布局状态值
        private const val LOADING = 0x10002
        private const val EMPTY = 0x10003
        private const val ERROR = 0x10004
        private const val CONTENT = 0x10005

        // 全局可配置的值
        private var LOADING_LAYOUT_ID = R.layout.child_page_state_loading_layout
        private var EMPTY_LAYOUT_ID = R.layout.child_page_state_empty_layout
        private var ERROR_LAYOUT_ID = R.layout.child_page_state_error_layout
        private var ANIM_DURATION = 200L // 状态布局切换的动画时间

        @JvmStatic
        fun appInit(@LayoutRes loadingLayoutId: Int = 0, @LayoutRes emptyLayoutId: Int = 0, @LayoutRes errorLayoutId: Int = 0, animDuration: Long = 200L) {
            if (loadingLayoutId != 0) LOADING_LAYOUT_ID = loadingLayoutId // 优先级普通
            if (emptyLayoutId != 0) EMPTY_LAYOUT_ID = emptyLayoutId
            if (errorLayoutId != 0) ERROR_LAYOUT_ID = errorLayoutId
            if (animDuration >= 0) ANIM_DURATION = animDuration
        }


        @JvmStatic
        fun wrap(ac: Activity): StateLayout {
            return wrap(ac.findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
        }

        @JvmStatic
        fun wrap(fm: Fragment?): StateLayout {
            return wrap(fm?.view)
        }

        @JvmStatic
        fun wrap(view: View?): StateLayout {
            if (null == view) throw RuntimeException("Content布局为空")
            val parentView = view.parent as ViewGroup?
                    ?: throw RuntimeException("必须要有一个父布局才可以使用StateLayout")
            parentView.removeView(view)
            val stateLayout = StateLayout(view.context).apply {
                view.visibility = View.INVISIBLE;addView(view, 0, ViewGroup.LayoutParams(view.layoutParams.width, view.layoutParams.height))
                contentViews = mutableListOf();contentViews?.add(view)
                defaultShowLoading = true
            }
            parentView.addView(stateLayout, view.layoutParams)
            return stateLayout
        }
    }
    //</editor-fold>
}
