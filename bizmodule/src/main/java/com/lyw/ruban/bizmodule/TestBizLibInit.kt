package com.lyw.ruban.bizmodule

import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020/6/12
 * Created by  lyw
 * Created for test biz lib init~
 */
class TestBizLibInit : LibInit(2, ConstantsForCore.THREAD_SYNC) {
    override fun doInit(context: InitContext, observer: IInitObserver) {
        context.logger.i(msg = "init:${getAliasName()}")
    }

    override fun getAliasName(): String {
        return "biz"
    }
}