package com.mistong.ruban.analyse

import com.lyw.ruban.AppInitManager
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.init.lib.LibInit
import kotlin.collections.HashMap

/**
 * Created on  2020/10/13
 * Created by  lyw
 * Created for 分析~
 */

/**
 * 1.针对于 添加的库 进行分析，获取到基础信息      LazyAppDependInit 添加方法库的方法中~
 * 2.针对于执行时间，进行记录~ 在module 初始化完成后，进行打印相关数据~ ，添加module 时，针对于module的别名，注册对应的监听~
 * 3.数据分析：
 *          a。打印出 无依赖的库（不依赖于其他三方，无三方依赖，根据耗时 长短，决定 添加的优先级）
 *          b。针对于 有部分依赖的库（不依赖于其他三方，有三方依赖，需要根据 三方依赖库的多少，添加优先级） ****  这个是 重中之重~ ，依赖数 最多的，优先级最高~
 *          c。针对于 有依赖的库（依赖于其他三方，有三方依赖，需要优先初始化其三方依赖（需要重点排查的依赖关系~）） 以第二个为依据， 逐级打印出 相关依赖~
 */

object RubanAnalyse : IRubanAnalyse {

    init {
        AppInitManager.addAppInitiativeCompletedListener(object : ICompleteListener {
            override fun onCompleted() {
                // TODO by LYW: 2020/10/19 进行打印相关数据～
            }
        })
    }

    //记录所有的init～
    private val mAnalyseInfos = HashMap<String, AnalyseInfo>(50)

    /**
     * 只处理 同步～
     */
    override fun addLib(libInit: LibInit) {
        /**
         * 1。获取 基础信息，针对于 module，threadCode 进行添加到不同到集合中～
         * 2。针对于不同到module，添加监听，在module完成时，打印日志
         * 3。同步：无依赖+ 被依赖数越多，优先级越高
         *    异步：耗时越多，优先级越高～ 需要监听 所有的 数据的初始化完成～
         */

        var libAlias = libInit.getAliasName()
        val moduleCode = libInit.libModuleCode
        val libThreadCode = libInit.libThreadCode
        val dependList = libInit.libDependAlias

        // LABEL BY LYW: 根据 添加的 LibInit，记录相关的依赖及被依赖。针对于 所有的 init～
        // 因为设计到 同步 和异步 两种，

        val info = getAnalyseInfo(libAlias)

        libInit.libDependAlias.let {
            info.dependentList.addAll(it)
            it.forEach {
                // LABEL BY LYW: 收集每个库被依赖的别名～
                val dependInfo = getAnalyseInfo(it)
                dependInfo.dependentChildList.add(it)
            }
        }

        // LABEL BY LYW: 根据 moduleCode,threadCode 进行归类～


        // 需要处理所有， 因为同步初始化的库，也会被 异步初始化的库 所依赖～
        // 监听所有的库初始化完成，需要针对于 耗时 进行分析～
    }

    private fun getAnalyseInfo(libAlias: String): AnalyseInfo {
        return mAnalyseInfos[libAlias] ?: let {
            AnalyseInfo().also {
                mAnalyseInfos[libAlias] = it
            }
        }
    }

    /**
     * 针对于 异步分析～
     */
    override fun recordCost(libInit: LibInit, costTime: Long) {
        // TODO by LYW: 2020/10/19 针对于 异步 记录相关耗时～

        var libAlias = libInit.getAliasName()
        val moduleCode = libInit.libModuleCode
        val libThreadCode = libInit.libThreadCode
        val dependList = libInit.libDependAlias

    }

    private fun initMap(): HashMap<Int, Map<Int, String>> {

        // 卡顿优化，内存优化（flutter引入导致的问题）
        //

        return hashMapOf()
    }
}


interface IRubanAnalyse {
    fun addLib(libInit: LibInit)

    fun recordCost(libInit: LibInit, costTime: Long)
}

/**
 * 分析过程中，记录到相关数据～
 */
class AnalyseInfo {
    // 被依赖～
    var dependentChildList = arrayListOf<String>()

    // 依赖～
    var dependentList = arrayListOf<String>()
}