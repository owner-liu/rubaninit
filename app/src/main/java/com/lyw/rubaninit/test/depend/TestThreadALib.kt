package com.lyw.rubaninit.test.depend

import android.os.Looper
import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
class TestThreadALib : LibInit(1, ConstantsForCore.THREAD_ASYNC, arrayListOf()) {

    override fun doInit(context: InitContext) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            context.logger.i(msg = "err-线程异常-init:${getAliasName()}")
        }
    }
}