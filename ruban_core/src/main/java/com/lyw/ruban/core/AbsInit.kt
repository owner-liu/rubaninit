package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs init~
 */
abstract class AbsInit {
    /**
     *init completed or not
     */
    var status: Int = ConstantsForCore.INIT_STATUS_DEFAULT

    abstract fun getAliasName(): String
}