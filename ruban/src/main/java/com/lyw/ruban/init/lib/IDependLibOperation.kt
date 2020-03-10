package com.lyw.ruban.init.lib

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for
 */
interface IDependLibOperation : ILibOperation {
    fun addModuleDependAlias(moduleCode: Int, list: ArrayList<String>)
}