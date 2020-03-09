package com.lyw.rubaninit.test

import android.util.Log
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.init.module.ModuleInit
import com.lyw.ruban.init.module.ThreadListExternalDependModuleInit
import com.lyw.ruban.init.module.ThreadListInternalDependModuleInit
import com.lyw.ruban.init.widgets.DependLibInit
import com.lyw.rubaninit.test.comm.TestLib
import com.lyw.rubaninit.test.comm.TestLibCopy
import com.lyw.rubaninit.test.depend.TestDependLib
import com.lyw.rubaninit.test.depend.TestDependLibCopy
import com.lyw.rubaninit.test.depend.TestExternalDependLib
import com.lyw.rubaninit.test.depend.TestExternalDependLibCopy

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
    }

    /**
     * 无任何依赖关系～
     */
    private fun testWithoutDepend() {
        var initContext = InitContext(null, true)
        ModuleInit(1).apply {
            addInit(TestLibCopy())
            addInit(TestLib())
        }.initialize(initContext, object : IInitObserver {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }
        })
    }

    /**
     * 依赖数据类型，但不存在相关依赖～
     */
    private fun testThreadDependWithoutAlias() {
        var initContext = InitContext(null, true)
        ThreadListInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf(), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, object : IDependInitObserver<IInitObserver> {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }

            override fun onWaitToInit(
                context: InitContext,
                init: AbsDependInit<IInitObserver>,
                dependAliasName: String
            ) {
                Log.i("ruban_test", "onWaitToInit-$dependAliasName-${System.currentTimeMillis()}")
            }
        })
    }

    /**
     * 线程集合内部存在依赖～
     */
    private fun testThreadDependWithAlias() {
        var initContext = InitContext(null, true)
        ThreadListInternalDependModuleInit(2).apply {
            addInit(DependLibInit(arrayListOf("TestDependLib-1-0"), TestDependLibCopy()))
            addInit(DependLibInit(arrayListOf(), TestDependLib()))
        }.initialize(initContext, object : IDependInitObserver<IInitObserver> {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }

            override fun onWaitToInit(
                context: InitContext,
                init: AbsDependInit<IInitObserver>,
                dependAliasName: String
            ) {
                Log.i("ruban_test", "onWaitToInit-$dependAliasName-${System.currentTimeMillis()}")
            }
        })
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
        }.initialize(initContext, object : IDependInitObserver<IInitObserver> {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "onCompleted-$aliasName-${System.currentTimeMillis()}")
            }

            override fun onWaitToInit(
                context: InitContext,
                init: AbsDependInit<IInitObserver>,
                dependAliasName: String
            ) {
                Log.i("ruban_test", "onWaitToInit-$dependAliasName-${System.currentTimeMillis()}")
            }
        })
    }
}