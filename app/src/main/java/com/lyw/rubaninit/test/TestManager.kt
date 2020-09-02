package com.lyw.rubaninit.test

import android.app.Application
import android.util.Log
import com.lyw.ruban.AppInitManager
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.core.ILogger
import com.lyw.ruban.init.module.ModuleConfig
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
            addLibInit(TestThreadDLib())

            configModule(
                ModuleConfig(
                    1,
                    false,
                    arrayListOf("2")
                )
            )
            configModule(ModuleConfig(3, true))

            addModuleCompletedListener(
                hashSetOf(1),
                object : ICompleteListener {
                    override fun onCompleted() {
                        Log.i("ruban", "监听到相关module 1已完成～")
                    }
                })
            addModuleCompletedListener(
                hashSetOf(2),
                object : ICompleteListener {
                    override fun onCompleted() {
                        Log.i("ruban", "监听到相关module 2已完成～")
                    }
                })
            addModuleCompletedListener(
                hashSetOf(3),
                object : ICompleteListener {
                    override fun onCompleted() {
                        Log.i("ruban", "监听到相关module 3已完成～")
                    }
                })

            addInitCompletedListener(2, "TestThreadBLib", object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到指定libB初始化完成～")
                }
            })
            addAppInitiativeCompletedListener(object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到全部初始化完成～")
                }
            })

            setLogger(CustomizationLogger())

            initialize(application, isDebug);
        }
    }
}

class CustomizationLogger : ILogger {
    override fun d(tag: String, msg: String) {

    }

    override fun e(tag: String, msg: String) {
    }

    override fun i(tag: String, msg: String) {
    }

    override fun v(tag: String, msg: String) {
    }

    override fun w(tag: String, msg: String) {
    }

}