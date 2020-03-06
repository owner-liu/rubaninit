package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs base init ～
 */
abstract class AbsBaseInit<U : IInitObserver> : AbsInit<U>() {

    final override fun initialize(context: InitContext, observer: U?) {
        doInit(context, observer)
    }

    //具体 执行对象～
    abstract fun doInit(context: InitContext, observer: U?)

}