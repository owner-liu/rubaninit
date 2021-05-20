package com.lyw.rubaninit.test

import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.provider.RubanInitializationProvider
import com.lyw.rubaninit.test.depend.TestThreadALib

/**
 * Created on  2021/5/19
 * Created by  lyw
 * Created for
 */
class RubanInitializationProviderImpl : RubanInitializationProvider() {

    override fun initLib(): ArrayList<LibInit> {
        return arrayListOf(TestThreadALib())
    }

    override fun isLazy(): Boolean {
        return false
    }

    override fun getDepends(): ArrayList<String> {
        return arrayListOf("2")
    }
}