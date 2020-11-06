package com.lyw.ruban.core.depend

import com.lyw.ruban.core.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend init container ~
 */
open class DependInitContainer<T : IInitObserver>
constructor(
    aliasList: ArrayList<String> = arrayListOf(),
    var init: AbsBaseInit<T>
) : AbsDependInit<IDependInitObserver>(aliasList) {

    private val mContainerObserver =
        DependProxyObserver<T>(this)

    override fun getAliasName(): String {
        return init.getAliasName()
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        if (hasInitComplete) {
            return
        }

        init.let {
            mContainerObserver.mObserver = observer
            //判断是否还有 依赖~
            if (hasDepend()) {
                context.logger.i(msg = "initialize-dependContainer-hasDepend-init:${init.getAliasName()}")
                //抛出自己～
                mContainerObserver.mObserver?.onWaitToInit(
                    context,
                    this,
                    getFirstDependAlias()
                )
                return
            }

            context.logger.i(msg = "initialize-dependContainer-init:${init.getAliasName()}")

            it.initialize(context, mContainerObserver as T)
        }
    }
}