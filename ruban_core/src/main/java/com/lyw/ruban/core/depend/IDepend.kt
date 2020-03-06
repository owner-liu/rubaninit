package com.lyw.ruban.core.depend

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for depend interface～
 */
interface IDepend {

    fun getDependAliased(): ArrayList<String>

    fun refreshDependComplete(aliasName: String)

    fun hasDepend(): Boolean

    fun getFirstDependAlias(): String
}