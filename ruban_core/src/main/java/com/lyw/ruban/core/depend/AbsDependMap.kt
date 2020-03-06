package com.lyw.ruban.core.depend

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.observer.DependGroupObserver
import kotlin.collections.ArrayList

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs depend map~
 */
abstract class AbsDependMap<T, U>
constructor(
    private var absMapAliases: ArrayList<String>
) : AbsDependInitGroup(absMapAliases) {

    var mData: Map<T, U>? = null

    private val mObserver by lazy { DependGroupObserver(this) }

    final override fun isEmpty(): Boolean {
        return !isNotEmpty()
    }

    final override fun isNotEmpty(): Boolean {
        return mData?.isNotEmpty() == true
    }

    fun getValue(key: T): U? {
        return mData?.get(key)
    }

    final override fun initialize(context: InitContext, observer: IDependGroupObserver?) {

        if (hasDepend()) {
            observer?.waitToInit(context, this, getFirstDependAlias())
            return
        }

        mData?.forEach {
            doInit(context, it.key, it.value, mObserver)
        }
    }

    abstract fun doInit(context: InitContext, key: T, value: U?, observer: IInitObserver?)
}