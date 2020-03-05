package com.lyw.ruban.core.depend

import com.lyw.ruban.core.thread.BaseThreadInitGroup

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base depend init group~
 */
abstract class BaseDependInitGroup
//记录相关的依赖别名～
constructor(
    var baseDependGroupAlias: ArrayList<String>,
    var baseDependGroupThreadCode: Int
) : BaseThreadInitGroup(baseDependGroupThreadCode)
    , IDepend {

    override fun getAlias(): String {
        return this.javaClass.simpleName
    }
}