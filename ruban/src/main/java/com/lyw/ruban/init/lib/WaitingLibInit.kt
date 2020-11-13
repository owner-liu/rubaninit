package com.lyw.ruban.init.lib

import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.module.ConstantForModule

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for lib 等待型～
 */
abstract class WaitingLibInit
constructor(
    //不指定，默认
    var waitingLibModuleCode: Int = ConstantForModule.CODE_MODULE_DEFAULT,
    //不指定，默认异步初始化～
    var waitingLibThreadCode: Int = ConstantsForCore.THREAD_ASYNC,
    //不指定，默认无依赖～
    var waitingLibDependAlias: ArrayList<String> = arrayListOf()
) : LibInit(waitingLibModuleCode, waitingLibThreadCode, waitingLibDependAlias) {

    private var startTime: Long = 0L

    lateinit var mObserver: IInitObserver
    lateinit var mInitContext: InitContext

    override fun initialize(context: InitContext, observer: IInitObserver) {
        if (status != ConstantsForCore.INIT_STATUS_DEFAULT) {
            context.logger.i(msg = "init cancel:${getAliasName()} for WaitingLibInit initialize ")
            return
        }

        status = ConstantsForCore.INIT_STATUS_INITING

        mObserver = observer
        mInitContext = context

        startTime = System.currentTimeMillis()

        doInit(context)
    }

    fun notifyFinished() {
        if (status == ConstantsForCore.INIT_STATUS_INITED) {
            mInitContext.logger.i(msg = "init cancel:${getAliasName()} for WaitingLibInit notifyFinished ")
            return
        }

        mInitContext.logger.i(
            "ruban",
            "completeCost-init-waiting:${getAliasName()}-cost:${System.currentTimeMillis() - startTime}"
        )
        status = ConstantsForCore.INIT_STATUS_INITED
        mObserver.onCompleted(mInitContext, getAliasName())
    }
}