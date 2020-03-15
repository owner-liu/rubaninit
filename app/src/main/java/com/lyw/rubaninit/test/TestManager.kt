package com.lyw.rubaninit.test

import android.util.Log
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.DependManagerObserver
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.init.app.AppDependInit
import com.lyw.ruban.init.app.IModulesInitCompleteListener
import com.lyw.ruban.init.module.ModuleInit
import com.lyw.ruban.init.module.ThreadListExternalDependModuleInit
import com.lyw.ruban.init.module.ThreadListInternalDependModuleInit
import com.lyw.ruban.init.widgets.DependLibInit
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
//        testThreadDependWithoutAlias()  //Lib->LibCopy,由于完成通知的回调都是插入队首的，故异步队列中的onComplete会有先后顺序～
//        testThreadDependWithAlias() //LibCopy-Lib>,由于完成通知的回调都是插入队首的，故异步队列中的onComplete会有先后顺序～
//        testModuleDependWithAlias() //Lib->LibCopy
//        testAppDependInit() //3->2->1
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
     * 依赖数据类型，但不存在相关依赖～
     */
    private fun testThreadDependWithoutAlias() {
        var initContext = InitContext(null, true)
        ThreadListInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf(), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, mDependObserver)
    }

    /**
     * 线程集合内部存在依赖～
     */
    private fun testThreadDependWithAlias() {
        var initContext = InitContext(null, true)
        ThreadListInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf("TestDependLib-1-0"), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, mDependObserver)
    }

    /**
     * module内，线程集合间存在依赖～
     */
    private fun testModuleDependWithAlias() {
        var initContext = InitContext(null, true)
        ThreadListExternalDependModuleInit(2).apply {
            addInit(
                DependLibInit(
                    arrayListOf("TestExternalDependLib-1-0"),
                    TestExternalDependLibCopy()
                )
            )
            addInit(DependLibInit(arrayListOf(), TestExternalDependLib()))
        }.initialize(initContext, mDependObserver)
    }

    private fun testAppDependInit() {
        var initContext = InitContext(null, true)
        val appDependInit = AppDependInit().apply {
            addLibInit(TestModuleADependLibCopy())
            addLibInit(TestModuleBDependLibCopy())
            addLibInit(TestModuleCDependLibCopy())
            addModuleDependAlias(1, arrayListOf("2"))
            addModuleDependAlias(2, arrayListOf("3"))
        }
        appDependInit.addCompletedListener(
            hashSetOf(1),
            object : IModulesInitCompleteListener {
                override fun onCompleted() {
                    Log.i("ruban_test", "监听到相关module 已完成～")
                }

            })
        appDependInit.initialize(initContext, mDependObserver)
    }
}