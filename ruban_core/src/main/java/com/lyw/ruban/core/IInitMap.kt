package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for i abs init list~
 */
interface IInitMap<T, U, Z : IInitObserver> : IInitGroup<Z> {

    var mData: Map<T, U>

    fun put(key: T, value: U)

    fun get(key: T): U? {
        return mData[key]
    }

    fun getData(): Map<T, U> {
        return mData
    }

    override fun isEmpty(): Boolean {
        return !isNotEmpty()
    }

    override fun isNotEmpty(): Boolean {
        return mData.isNotEmpty()
    }

    fun getValue(key: T): U? {
        return mData[key]
    }

    override fun initialize(context: InitContext, observer: Z) {
        mData.forEach {
            doInit(context, it.key, it.value, observer)
        }
    }

    fun doInit(context: InitContext, key: T, value: U?, observer: Z)
}