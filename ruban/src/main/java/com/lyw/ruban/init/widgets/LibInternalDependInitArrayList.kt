package com.lyw.ruban.init.widgets

import android.util.Log
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内部 存在依赖～
 */
class LibInternalDependInitArrayList :
    AbsInitArrayList<DependLibInit, IInitObserver>() {

    override var mData: List<DependLibInit> = arrayListOf()

    private var mObserver: IInitObserver? = null


    private var mInitCompletedAliases = arrayListOf<String>()
    private var mWaitToInits = hashMapOf<String, ArrayList<AbsDependInit<IInitObserver>>>()

    private val mObserverPoxy by lazy {
        object : IDependInitObserver<IInitObserver> {
            override fun onCompleted(context: InitContext, aliasName: String) {
                Log.i("ruban_test", "aliasName:$aliasName")
                mInitCompletedAliases.add(aliasName)
                // TODO by LYW: 2020-03-08 外抛～
                mObserver?.onCompleted(context, aliasName)
                // TODO by LYW: 2020-03-08 筛选出 依赖与 aliasName 的 ，进行处理～
                val waitToInits = mWaitToInits.remove(aliasName)
                waitToInits?.forEach {
                    it.refreshDependComplete(aliasName)
                    it.initialize(context, this)
                }
            }

            override fun onWaitToInit(
                context: InitContext,
                init: AbsDependInit<IInitObserver>,
                dependAliasName: String
            ) {
                Log.i("ruban_test", "waitToInit:${init.getAliasName()}-depend:$dependAliasName")
                var list = mWaitToInits.get(dependAliasName) ?: let {
                    arrayListOf<AbsDependInit<IInitObserver>>().also {
                        mWaitToInits[dependAliasName] = it
                    }
                }
                list.add(init)
            }
        }
    }

    override fun doInit(context: InitContext, init: DependLibInit, observer: IInitObserver) {
        mObserver = observer
        init.initialize(context, mObserverPoxy)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }
}
