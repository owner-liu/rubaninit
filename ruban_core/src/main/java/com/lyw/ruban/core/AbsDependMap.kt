package com.lyw.ruban.core

import com.lyw.ruban.core.depend.BaseDependInitGroup
import com.lyw.ruban.core.depend.IDependInitObserver
import kotlin.collections.ArrayList

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs depend map~
 */
abstract class AbsDependMap<T, U>
constructor(
    var absMapAliases: ArrayList<String>
) : BaseDependInitGroup(absMapAliases) {

    var mData: Map<T, U>? = null

    override fun isEmpty(): Boolean {
        return !isNotEmpty()
    }

    override fun isNotEmpty(): Boolean {
        return mData?.isNotEmpty() == true
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver?) {
        mData?.forEach {
            doInit(context, it.key, it.value, observer)
        }
    }

    abstract fun doInit(context: InitContext, key: T, value: U?, observer: IInitObserver?)

    abstract fun insertFirstInit(value: U?)
}