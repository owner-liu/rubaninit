package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base init ～
 */
open class BaseInit<T : IInitObserver> : AbsInit<T>() {
    override fun initialize(context: InitContext, observer: T?) {

    }
}