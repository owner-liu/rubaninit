package com.lyw.ruban.init.module

import com.lyw.ruban.core.ConstantsForCore

/**
 * Created on  2020-03-23
 * Created by  lyw
 * Created for module config~
 */
class ModuleConfig
constructor(
    val moduleCode: Int,
    val lazy: Boolean = false,
    var dependAlias: ArrayList<String>? = null
)