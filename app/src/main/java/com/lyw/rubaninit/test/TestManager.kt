package com.lyw.rubaninit.test

import android.app.Application
import android.util.Log
import com.lyw.ruban.AppInitManager
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.init.module.ModuleConfig
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.rubaninit.test.depend.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for test manager~
 */
object TestManager {
    fun test(application: Application, isDebug: Boolean) {
        testAppLazyDependThreadInit(application, isDebug)
    }

    private fun testAppLazyDependThreadInit(application: Application, isDebug: Boolean) {
        AppInitManager.apply {
            addLibInit(TestThreadALib())
            addLibInit(TestThreadBLib())
            addLibInit(TestThreadCLib())

            configModule(ModuleConfig(1, false, arrayListOf("2")))

            addModuleCompletedListener(
                hashSetOf(1),
                object : ICompleteListener {
                    override fun onCompleted() {
                        Log.i("ruban", "监听到相关module 已完成～")

                        initializeLazyAll()
                    }
                })

            addInitCompletedListener(1, "TestThreadALib", object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到指定lib初始化完成～")
                }
            })

            addAppCompletedListener(object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到全部初始化完成～")
                }

            })

            initialize(application, isDebug);
        }
    }
}