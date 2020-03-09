package com.lyw.ruban.init.app

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.IDependLibOperation
import com.lyw.ruban.init.lib.ILibOperation
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleInit
import com.lyw.ruban.init.widgets.DependLibInit
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app 相关库存在依赖，支持线程集合内，跨线程集合～
 */
class DependAppInit