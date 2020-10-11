# code_lib dev分支

> 功能试验区，文档一定同步




#### StaticLayout
> 页面多状态布局, 将页面 / View增加Loading, Empty, Error三种状态

##### 特性
1. 支持全局配置
2. 支持Activity, Fragment, View直接调用
3. 支持Xml开发

##### 使用

1. 开箱即用
``` kotlin
class Activity {
    ...
    private val stateLayout ly lazy { StateLayout.wrap(this) }

    fun onCreate(savedInstanceState) {
        ...
        stateLayout.init{ loadData() }
    }

    fun loadData() {
        ...
        Http.Requests(
            { success -> 
                stateLayout?.showContent()
            },
            { error -> 
                stateLayout?.showError()
            }
        )
    }
}
```
2. 全局配置 ~~未上仓库, 直接修改源码不建议调用该函数~~
``` kotlin
class App : Application {
    fun onCreate(){
        ....
        // 也可以另寻时机调用
        StateLayout.appInit(loadingLayoutId, emptyLayoutId, errorLayoutId, animDuration)
    }
}
```
3. Activity / Fragment / View调用
``` kotlin
class Activity{
    ...
    private val stateLayout by lazy { 
        // Activity或Fragment调用场景
        StateLayout.wrap(this)
        // View调用场景 Eg: Activity ToolBar以下界面需要状态管理
        // StateLayout.wrap(ly_content /*findViewById(R.id.ly_content)*/) 
        }

    fun onCreate(savedInstanceState) {
        ...
        stateLayout.init{ loadData() }
        // 部分界面需要显示不同的状态布局
        // stateLayout.init({ loadData() }, customLoadingLayoutId...)
    }
    ...
    stateLayout.showContent()
    stateLayout.showContent()
    stateLayout.showContent()
    stateLayout.showContent()
}

```
4. Xml调用
> _default_show_loading 开启不支持xml内容布局编辑预览 !!!
``` xml
<LinearLayout>
    <ToolBar />
    <StateLayout
        app:_loadingLayoutId=""
        app:_emptyLayoutId=""
        app:_errorLayoutId=""
        app:_default_show_loading="false"> <!-- 默认显示loading界面 默认为false -->
        <ContentLayout>
    </StateLayout>
</LinearLayout>
```

