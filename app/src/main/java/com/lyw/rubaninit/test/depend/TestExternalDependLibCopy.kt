package com.lyw.rubaninit.test.depend

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
class TestExternalDependLibCopy : LibInit(1, ConstantsForCore.THREAD_SYNC, arrayListOf()) {
    override fun getAliasName(): String {
        return "TestExternalDependLibCopy-$libModuleCode-$libThreadCode"
    }

    override fun doInit(context: InitContext, observer: IInitObserver) {
    }
}