package com.lyw.ruban.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

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
        /**
         * 如果是application注册的transform, 通常情况下, 我们一般指定TransformManager.SCOPE_FULL_PROJECT;
         * 如果是library注册的transform, 我们只能指定TransformManager.PROJECT_ONLY
         */
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation) {

        val inputs = transformInvocation.inputs
        val outputProvider = transformInvocation.outputProvider

        val isIncremental = transformInvocation.isIncremental

        if (isIncremental) {
            outputProvider.deleteAll()
        }

        inputs.forEach {
            it.jarInputs.forEach {
                val file = it.file
                val absolutePath = file.absolutePath
                val contentTypes = it.contentTypes
                val scopes = it.scopes
                val dest = outputProvider.getContentLocation(
                    absolutePath,
                    contentTypes,
                    scopes,
                    Format.JAR
                )
                FileUtils.copyFile(file, dest)
            }

            it.directoryInputs.forEach {
                val file = it.file
                handleClass(file)
                val name = it.name
                val contentTypes = it.contentTypes
                val scopes = it.scopes
                val dest = outputProvider.getContentLocation(
                    name,
                    contentTypes,
                    scopes,
                    Format.DIRECTORY
                )
                FileUtils.copyDirectory(file, dest)
            }
        }
    }

    private fun handleClass(file: File) {
        if (file.isFile) {
            if (file.name.endsWith(".class")
                && !file.name.endsWith("R.class")
                && !file.name.endsWith("BuildConfig.class")
                && !file.name.contains("R\$")
            ) {
                val fis = FileInputStream(file)
                val cr = ClassReader(fis)
                val cw = ClassWriter(cr, 0)
                val scv = ScanClassVisitor(Opcodes.ASM5, cw)
                cr.accept(scv, ClassReader.EXPAND_FRAMES)

                val bytes = cw.toByteArray()
                val optFile = File(file.parent, file.name + ".opt")
                val ops = FileOutputStream(optFile)
                ops.write(bytes)

                if (optFile.exists()) {
                    optFile.renameTo(file)
                }

                fis.close()
                ops.close()
            }
        } else if (file.isDirectory) {
            file.listFiles().forEach { file ->
                handleClass(file)
            }
        }
    }
}