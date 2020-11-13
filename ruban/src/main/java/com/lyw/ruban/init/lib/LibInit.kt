package com.lyw.ruban.init.lib

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.module.ConstantForModule

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for lib ，只涉及线程，module～
 */
abstract class LibInit
constructor(
    //不指定，默认
    var libModuleCode: Int = ConstantForModule.CODE_MODULE_DEFAULT,
    //不指定，默认异步初始化～
    var libThreadCode: Int = ConstantsForCore.THREAD_ASYNC,
    //不指定，默认无依赖～
    var libDependAlias: ArrayList<String> = arrayListOf()
) : AbsBaseInit<IInitObserver>() {

    private var startTime: Long = 0L

    override fun initialize(context: InitContext, observer: IInitObserver) {
        if (checkInitStart()) {
            context.logger.i(msg = "init cancel:${getAliasName()} for LibInit initialize ")
            return
        }
        startInit()

        startTime = System.currentTimeMillis()

        doInit(context)

        context.logger.i(
            "ruban",
            "completeCost-init:${getAliasName()}-cost:${System.currentTimeMillis() - startTime}"
        )
        finishInit()
        observer.onCompleted(context, getAliasName())
    }

    abstract fun doInit(context: InitContext)

    final override fun getAliasName(): String {
        return this.javaClass.simpleName
    }
}