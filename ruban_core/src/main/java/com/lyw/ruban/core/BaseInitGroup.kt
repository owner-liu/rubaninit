package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base init group～
 */
abstract class BaseInitGroup<T : IInitObserver> : AbsInit<T>() {

    abstract fun isEmpty(): Boolean

    abstract fun isNotEmpty(): Boolean
}