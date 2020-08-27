package com.lyw.ruban.init.widgets.depend

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.core.depend.DependProxyObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内部相关依赖和完成不进行处理~ 直接外抛~
 */
class ThreadDependLibList :
    AbsInitArrayList<DependThreadLibInit, IDependInitObserver>() {

    override fun add(init: DependThreadLibInit) {
        super.add(init)
        // TODO by LYW: 2020/8/27  进行拦截，针对于 查询历史库的执行时间，分配不同的优先级～
        //  优先级：
        //  策略：
        //  1。不存在任何依赖，耗时越长，优先级越高～
        //  2。被依赖越多，优先级越高～
        //  3。同等依赖，耗时越长，优先级越高～
        //  思考：
        //  1。 增加开关，关闭自动分析功能。
        //  1。 第一次执行，按照用户添加的规则。
        //  2。 添加时，记录是否存在依赖 标记～
        //  3。 执行完成后，增加 空闲时 分析任务～ 记录 是否已进行分析～
        //  4。 增加全部标记，判断是否已分析，是否需要根据分析结果来添加新规则～
        //  5。 增加lazy，增加每次启动后，使用的优先级，增加空闲时，自动初始化 对应的lazy～
    }

    override var mData: List<DependThreadLibInit> = arrayListOf()

    private val mObserverProxy =
        DependProxyObserver<IInitObserver>()

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        mObserverProxy.mObserver = observer
        super.initialize(context, mObserverProxy)
    }

    override fun doInit(
        context: InitContext,
        init: DependThreadLibInit,
        observer: IDependInitObserver
    ) {
        init.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return this.javaClass.simpleName
    }
}