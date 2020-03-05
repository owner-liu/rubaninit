package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs init~
 */
abstract class AbsInit<T : IInitObserver> {
    abstract fun initialize(context: InitContext, observer: T? = null)
}