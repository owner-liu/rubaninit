package com.lyw.ruban.core.depend

import com.lyw.ruban.core.thread.BaseThreadInit

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base depend init~
 */
open class BaseDependInit
constructor(
    var baseDependAliases: ArrayList<String>
    , var baseDependThreadCode: Int
) : BaseThreadInit(baseDependThreadCode)
    , IDepend {

    override fun getAlias(): String {
        return this.javaClass.simpleName
    }
}