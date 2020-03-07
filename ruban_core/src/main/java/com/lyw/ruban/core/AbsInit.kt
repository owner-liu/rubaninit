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
    var hasInit: Boolean = false

    abstract fun getAliasName(): String
}