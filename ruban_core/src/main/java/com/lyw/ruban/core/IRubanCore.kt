package com.lyw.ruban.core

import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-30
 * Created by  lyw
 * Created for ruban core interface~
 */

interface IInitObserver {

    fun onCompleted(context: InitContext, aliasName: String)
}

interface IDependInitObserver :
    IInitObserver {

    fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<IDependInitObserver>,
        dependAliasName: String
    )
}

interface IInit<T : IInitObserver> {
    fun initialize(context: InitContext, observer: T)
}

interface IInitGroup<T : IInitObserver> : IInit<T> {
    fun isEmpty(): Boolean

    fun isNotEmpty(): Boolean
}

interface IInitList<T : AbsInit, U : IInitObserver> : IInitGroup<U> {
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

interface IThread {
    fun getCurrentThreadCode(): Int
}

interface ILazyInit<T : IInitObserver> {
    fun initializeLazy(context: InitContext, observer: T)
}

interface IDepend {

    fun getDependAliased(): ArrayList<String>

    fun refreshDependComplete(aliasName: String)

    fun hasDepend(): Boolean

    fun getFirstDependAlias(): String

    fun addAliasList(list: ArrayList<String>)
}