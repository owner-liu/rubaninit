package com.lyw.rubaninit.test

import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.provider.RubanInitializationProvider
import com.lyw.rubaninit.test.depend.TestThreadELib

/**
 * Created on  2021/5/19
 * Created by  lyw
 * Created for
 */
class RubanInitializationProviderImpl2 : RubanInitializationProvider() {

    override fun initLib(): ArrayList<LibInit> {
        return arrayListOf(TestThreadELib())
    }

    override fun isLazy(): Boolean {
        return false
    }

    override fun getDepends(): ArrayList<String> {
        return arrayListOf()
    }
}