package com.lyw.rubaninit.test

import android.util.Log
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.init.app.ICompleteListener
import com.lyw.ruban.init.app.LazyAppDependInit
import com.lyw.ruban.init.module.comm.ModuleInit
import com.lyw.ruban.init.module.depend.ThreadListExternalDependModuleInit
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.rubaninit.test.comm.TestLib
import com.lyw.rubaninit.test.comm.TestLibCopy
import com.lyw.rubaninit.test.depend.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
object TestManager {
    fun test() {
//        testWithoutDepend() //LibCopy->Lib
//        testModuleDependWithAlias() //Lib->LibCopy
        testAppDependInit() //3->2->1
    }

    private val mDependObserver = object : IDependInitObserver {
        override fun onCompleted(context: InitContext, aliasName: String) {
            Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
        }

        override fun onWaitToInit(
            context: InitContext,
            init: AbsDependInit<IDependInitObserver>,
            dependAliasName: String
        ) {
            Log.i("ruban_test", "onWaitToInit-$dependAliasName-${System.currentTimeMillis()}")
        }

    }


    private val mObserver = object : IInitObserver {
        override fun onCompleted(context: InitContext, aliasName: String) {
            Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
        }

    }

    /**
     * 无任何依赖关系～
     */
    private fun testWithoutDepend() {
        var initContext = InitContext(null, true)
        ModuleInit(1).apply {
            addInit(TestLibCopy())
            addInit(TestLib())
        }.initialize(initContext, mObserver)
    }

    /**
     * module内，线程集合间存在依赖～
     */
    private fun testModuleDependWithAlias() {
        var initContext = InitContext(null, true)
        ThreadListExternalDependModuleInit(2).apply {
            addInit(
                DependThreadLibInit(
                    TestExternalDependLibCopy()
                )
            )
            addInit(DependThreadLibInit(TestExternalDependLib()))
        }.initialize(initContext, mDependObserver)
    }

    private fun testAppDependInit() {
        var initContext = InitContext(null, true)
        val appDependInit = LazyAppDependInit().apply {
            addLibInit(TestModuleADependLibCopy())
            addLibInit(TestModuleBDependLibCopy())
            addLibInit(TestModuleCDependLibCopy())
            addModuleDependAlias(1, arrayListOf("2"))
            addModuleDependAlias(2, arrayListOf("3"))
        }
        appDependInit.addModuleCompletedListener(
            hashSetOf(3),
            object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban_test", "监听到相关module 已完成～")
                }

            })
        appDependInit.addAppCompletedListener(object : ICompleteListener {
            override fun onCompleted() {
                Log.i("ruban_test", "全部初始化完毕！")
            }
        })
        appDependInit.initialize(initContext, mDependObserver)

        appDependInit.addAppCompletedListener(object : ICompleteListener {
            override fun onCompleted() {
                Log.i("ruban_test", "全部初始化完毕～")
            }
        })
    }
}