package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for abs init~
 */
abstract class AbsInit {

    abstract fun getAliasName(): String

    /**
     *init completed or not
     */
    private var status: Int = ConstantsForCore.INIT_STATUS_DEFAULT


    fun startInit() {
        status = ConstantsForCore.INIT_STATUS_INITING
    }

    fun finishInit() {
        status = ConstantsForCore.INIT_STATUS_INITED
    }

    fun initInit() {
        status = ConstantsForCore.INIT_STATUS_DEFAULT
    }

    fun checkInitStart(): Boolean {
        return status != ConstantsForCore.INIT_STATUS_DEFAULT
    }

    fun checkInitFinished(): Boolean {
        return status == ConstantsForCore.INIT_STATUS_INITED
    }
}