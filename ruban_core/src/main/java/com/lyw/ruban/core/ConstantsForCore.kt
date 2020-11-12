package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for core constants~
 */
class ConstantsForCore {
    companion object {
        //异步～
        const val THREAD_ASYNC = 0

        //同步～
        const val THREAD_SYNC = 1

        //当前的初始化状态～
        const val INIT_STATUS_DEFAULT = 0
        //已调用初始化逻辑～
        const val INIT_STATUS_INITING = 1
        //已初始化完成～
        const val INIT_STATUS_INITED = 2
    }
}