package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs init group～
 */
abstract class AbsInitGroup<T : IInitObserver> : AbsInit() {

    abstract fun isEmpty(): Boolean

    abstract fun isNotEmpty(): Boolean
}