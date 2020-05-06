package com.lyw.ruban.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import java.io.File

/**
 * Created on  2020/4/30
 * Created by  lyw
 * Created for alias transform~
 */
class AliasTransform
constructor(project: Project) : Transform() {
    override fun getName(): String {
        return "Alias"
    }

    /**
     * TransformManager.CONTENT_CLASS 只检索 .class～
     * TransformManager.CONTENT_JARS
     * TransformManager.CONTENT_RESOURCES 只检索 java 标准资源文件～
     * TransformManager.CONTENT_NATIVE_LIBS
     * TransformManager.CONTENT_DEX
     * TransformManager.CONTENT_DEX_WITH_RESOURCES
     */
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        //只检索 .class～
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        // 是否支持增量编译～
        return false
    }

    /**
     * PROJECT_ONLY
     * SCOPE_FULL_PROJECT
     * SCOPE_FULL_WITH_IR_FOR_DEXING
     * SCOPE_FULL_WITH_FEATURES
     * SCOPE_FULL_WITH_IR_AND_FEATURES
     * SCOPE_FEATURES
     * SCOPE_FULL_LIBRARY_WITH_LOCAL_JARS
     * SCOPE_FULL_PROJECT_WITH_LOCAL_JARS
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        //只有项目内容～
        return TransformManager.PROJECT_ONLY
    }

    override fun transform(transformInvocation: TransformInvocation) {
        println("RubanPlugin transform")

        val inputs = transformInvocation.inputs
        val outputProvider = transformInvocation.outputProvider

        val isIncremental = transformInvocation.isIncremental

        if (isIncremental) {
            outputProvider.deleteAll()
        }

        inputs.forEach {
            println("RubanPlugin inputs")
            it.jarInputs.forEach {
                println("RubanPlugin jarInputs")
                // TODO by LYW: 2020/5/6  修改代码～

                val dest = outputProvider.getContentLocation(
                    it.file.absolutePath,
                    it.contentTypes,
                    it.scopes,
                    Format.JAR
                )

                FileUtils.copyFile(it.file, dest)
            }

            it.directoryInputs.forEach {
                println("RubanPlugin directoryInputs")
                // TODO by LYW: 2020/5/6  修改代码～
                readClassWithFile(it.file)


                val dest = outputProvider.getContentLocation(
                    it.name,
                    it.contentTypes,
                    it.scopes,
                    Format.DIRECTORY
                )
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }

    private fun readClassWithFile(file: File) {
        val root = file.absolutePath
        println("RubanPlugin readClassWithFile root:$root")
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                val filePath = it.absolutePath
                println("RubanPlugin readClassWithFile filePath:$filePath")
                if (!filePath.endsWith(".class")) return
                val className = getClassName(root, filePath)
                println("RubanPlugin class name is:$className")
            }
        } else {
            println("RubanPlugin current file is not directory")
        }
    }

    private fun getClassName(root: String, classPath: String): String {
        return classPath.substring(root.length + 1, classPath.length - 6)
            .also { it.replace("/", ".") }
    }
}