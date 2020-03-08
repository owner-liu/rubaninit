package com.lyw.rubaninit.test.comm

import android.util.Log
import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
class TestLib : LibInit(1, ConstantsForCore.THREAD_ASYNC, arrayListOf()) {
    override fun getAliasName(): String {
        return "TestLib-$libModuleCode-$libThreadCode"
    }

    override fun doInit(context: InitContext, observer: IInitObserver) {
    }
}