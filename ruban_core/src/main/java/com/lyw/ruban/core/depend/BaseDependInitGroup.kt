package com.lyw.ruban.core.depend

import com.lyw.ruban.core.BaseInitGroup

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base depend init group~
 */
abstract class BaseDependInitGroup
//记录相关的依赖别名～
constructor(
    var treeMapDependGroupAliases: ArrayList<String>
) : BaseInitGroup<IDependInitObserver>()
    , IDepend {

    override fun getAlias(): String {
        return this.javaClass.simpleName
    }
}