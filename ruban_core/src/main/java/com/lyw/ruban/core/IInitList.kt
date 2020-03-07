package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for i abs init list~
 */
interface IInitList<T, U : IInitObserver> : IInitGroup,
    IInit<U> {
    var mData: List<T>

    fun add(init: T)

    fun getData(): List<T> {
        return mData
    }

    override fun isEmpty(): Boolean {
        return mData.isEmpty()
    }

    override fun isNotEmpty(): Boolean {
        return mData.isNotEmpty()
    }

    override fun initialize(context: InitContext, observer: U) {
        mData.forEach {
            doInit(context, it, observer)
        }
    }

    fun doInit(context: InitContext, init: T, observer: U)

}