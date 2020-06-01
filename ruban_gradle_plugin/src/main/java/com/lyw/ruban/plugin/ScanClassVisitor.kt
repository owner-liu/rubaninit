package com.lyw.ruban.plugin

import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes


/**
 * Created on  2020/5/7
 * Created by  lyw
 * Created for scan class visitor~
 *
 * 执行顺序：
 * visit
visitSource?
visitOuterClass?
( visitAnnotation | visitAttribute )*
( visitInnerClass | visitField | visitMethod )*
visitEnd
 */
class ScanClassVisitor
constructor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    //是否需要扫描方法～
    private var needScanMethod = false
    private var classSimpleName: String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        //对比 class～
        super.visit(version, access, name, signature, superName, interfaces)
        // TODO by LYW: 2020/5/15 通过接口 区别～
        if (superName?.equals("com/lyw/ruban/init/lib/LibInit") == true) {
            //过滤对应的class～
            val index = name?.lastIndexOf("/")
            index?.let {
                classSimpleName = name.substring(index + 1)
            }
            needScanMethod = true
        }
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        var methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        if (needScanMethod && name?.equals("getAliasName") == true) {
            methodVisitor = ScanMethodVisitor(api, methodVisitor, classSimpleName)
        }
        return methodVisitor
    }
}

/**
 * 1.L全限定名
 * 2.无返回值void用“V”代替
 * 3.class文件中类型 boolean用“Z”描述
 * 4.每一个线程内部都有一个栈结构（即通常所说的“堆栈”中的虚拟机栈），栈中的每一个元素（一帧）称为一个栈帧（stack frame）。栈帧与我们写的方法一一对应，每个方法的调用／return对应线程中的一个栈帧的入栈／出栈。
 * 5.局部变量表的第一个槽位存放的是this
 * 6.visitAnnotationDefault?
( visitAnnotation | visitParameterAnnotation | visitAttribute )*
( visitCode
( visitTryCatchBlock | visitLabel | visitFrame | visitXxxInsn |
visitLocalVariable | visitLineNumber )*
visitMaxs )?
visitEnd
 */
class ScanMethodVisitor
constructor(
    api: Int, mv: MethodVisitor,
    var className: String?
) : MethodVisitor(api, mv) {

    override fun visitCode() {
        //开始访问方法体内的代码
        super.visitCode()
    }

    override fun visitTryCatchBlock(p0: Label?, p1: Label?, p2: Label?, p3: String?) {
        //访问方法的try catch block
        super.visitTryCatchBlock(p0, p1, p2, p3)
    }

    override fun visitLocalVariable(
        p0: String?,
        p1: String?,
        p2: String?,
        p3: Label?,
        p4: Label?,
        p5: Int
    ) {
        //指令，访问局部变量表里面的某个局部变量（关于局部变量表后面会有介绍）
        super.visitLocalVariable(p0, p1, p2, p3, p4, p5)
    }

    override fun visitLabel(p0: Label?) {
        //如果方法体中有跳转指令，字节码指令中会出现label，所谓label可以近似看成行号的标记（并不是），指示跳转指令将要跳转到哪里
        super.visitLabel(p0)
    }

    override fun visitFrame(p0: Int, p1: Int, p2: Array<out Any>?, p3: Int, p4: Array<out Any>?) {
        //记录当前栈帧（栈帧结构将在后面有介绍）状态，用于Class文件加载时的校验
        super.visitFrame(p0, p1, p2, p3, p4)
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        //指定当前方法的栈帧中，局部变量表和操作数栈的大小。（java栈大小是javac之后就确定了的）
        super.visitMaxs(maxStack, maxLocals)
    }

    override fun visitInsn(opcode: Int) {
        if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
            || opcode == Opcodes.ATHROW
        ) {
            println("RubanPlugin insert code into $className-getAliasName()")
            mv.visitLdcInsn(className)
            mv.visitInsn(groovyjarjarasm.asm.Opcodes.ARETURN)
            println("RubanPlugin insert end~")
        }
        mv.visitInsn(opcode);
    }

}