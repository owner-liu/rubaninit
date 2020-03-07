package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for init interface~
 */
interface IInit<T : IInitObserver> {
    fun initialize(context: InitContext, observer: T)
}