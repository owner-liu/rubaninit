package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for init group interface~
 */
interface IInitGroup<T : IInitObserver> : IInit<T> {
    fun isEmpty(): Boolean

    fun isNotEmpty(): Boolean
}