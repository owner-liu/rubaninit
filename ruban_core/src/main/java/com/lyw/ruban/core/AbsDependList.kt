package com.lyw.ruban.core

import com.lyw.ruban.core.depend.BaseDependInitGroup
import com.lyw.ruban.core.depend.IDependInitObserver
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs depend list~
 */
abstract class AbsDependList<T>
constructor(
    var absMapAliases: ArrayList<String>
) : BaseDependInitGroup(absMapAliases) {

    var mData: List<T>? = null

    override fun isEmpty(): Boolean {
        return !isNotEmpty()
    }

    override fun isNotEmpty(): Boolean {
        return mData?.isNotEmpty() == true
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver?) {
        mData?.forEach {
            doInit(context, it, observer)
        }
    }

    abstract fun doInit(context: InitContext, value: T?, observer: IInitObserver?)

    abstract fun insertFirstInit(value: T?)
}