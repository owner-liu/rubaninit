package com.lyw.rubaninit.test.depend

import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.WaitingLibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
class TestThreadELib :
    WaitingLibInit(4, ConstantsForCore.THREAD_ASYNC) {
    override fun doInit(context: InitContext) {

        // TODO by LYW: 2020/11/13 ~进行 异步耗时任务～

        notifyFinished()
    }
}