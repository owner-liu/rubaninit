package com.lyw.rubaninit.test

import android.util.Log
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.module.ModuleInit
import com.lyw.ruban.init.module.ThreadInternalDependModuleInit
import com.lyw.ruban.init.widgets.DependLibInit
import com.lyw.rubaninit.test.comm.TestLib
import com.lyw.rubaninit.test.comm.TestLibCopy
import com.lyw.rubaninit.test.depend.TestDependLib
import com.lyw.rubaninit.test.depend.TestDependLibCopy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
object TestManager {
    fun test() {
        var initContext = InitContext(null, true)
        ModuleInit(1).apply {
            addInit(TestLibCopy())
            addInit(TestLib())
            addInit(TestLibCopy())
            addInit(TestLib())
        }.initialize(initContext, object : IInitObserver {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }
        })
    }

    fun testDependWithoutAlias() {
        var initContext = InitContext(null, true)
        ThreadInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf(), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, object : IInitObserver {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }
        })
    }

    fun testDependWithAlias() {
        var initContext = InitContext(null, true)
        ThreadInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf("TestDependLib-1-0"), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, object : IInitObserver {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }
        })
    }
}