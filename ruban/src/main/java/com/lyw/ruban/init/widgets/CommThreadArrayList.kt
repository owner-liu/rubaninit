package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.thread.ThreadInitContainer

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 普通的线程执行列表～
 */
class CommThreadArrayList
constructor(
    threadCode: Int,
    var commThreadArrayList: LibInitArrayList = LibInitArrayList()
) : ThreadInitContainer<IInitObserver>(threadCode, commThreadArrayList) {
    override fun getAliasName(): String {
        return commThreadArrayList.getAliasName()
    }
}