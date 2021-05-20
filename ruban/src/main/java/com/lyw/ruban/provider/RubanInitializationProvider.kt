package com.lyw.ruban.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.lyw.ruban.AppInitManager
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleConfig

/**
 * Created on  2021/5/19
 * Created by  lyw
 * Created for ruban contentProvider
 */
abstract class RubanInitializationProvider : ContentProvider() {

    init {

    }

    var moduleCode = 0
    override fun onCreate(): Boolean {

        Log.i("ruban", "ContentProvider-${this.javaClass.simpleName}-init")

        initLib().forEach {
            moduleCode = it.libModuleCode
            AppInitManager.addLibInit(it)
        }

        AppInitManager.configModule(ModuleConfig(moduleCode, isLazy(), getDepends()))

        if (AppInitManager.hasStartInit()) {
            Log.i("ruban", "ContentProvider-${this.javaClass.simpleName} initializeLazy")
            AppInitManager.initializeLazy(moduleCode)
        } else {
            Log.i("ruban", "ContentProvider-${this.javaClass.simpleName} wait for initialization ")
        }

        return true
    }

    abstract fun initLib(): ArrayList<LibInit>

    abstract fun isLazy(): Boolean

    abstract fun getDepends(): ArrayList<String>

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}