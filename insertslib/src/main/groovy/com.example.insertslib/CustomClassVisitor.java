package com.example.insertslib;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;

/**
 * @author zhanglulu on 2020/5/27.
 * for
 */
public class CustomClassVisitor extends ClassVisitor {

    private String mClassName;
    private String[] mInterfaces;

    public CustomClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;
        mInterfaces = interfaces;
        System.out.println("mInterfaces: " + Arrays.toString(mInterfaces));
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (isMatchOnClickListener(name)) {
            System.out.println("实现了该接口的类： " + name);
            methodVisitor = new CustomAdviceAdapter(methodVisitor, access, name, descriptor) {
                @Override
                protected void onMethodExit(int opcode) {
                    super.onMethodExit(opcode);
//                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                    mv.visitLdcInsn("点击结束 class： " + mClassName + " interface : "+Arrays.toString(mInterfaces));
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/example/bytesinsert0507/MainActivity", "getApplicationContext", "()Landroid/content/Context;", false);
                    mv.visitLdcInsn("点击页面 class： " + mClassName + " 接口 : "+Arrays.toString(mInterfaces));
                    mv.visitInsn(ICONST_0);
                    mv.visitMethodInsn(INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);
                }

                @Override
                protected void onMethodEnter() {
                    super.onMethodEnter();

//                    mv.visitVarInsn(ALOAD, 0);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "com/example/bytesinsert0507/MainActivity", "getApplicationContext", "()Landroid/content/Context;", false);
//                    mv.visitLdcInsn("点击页面所在 class： " + mClassName + " 接口 : " + Arrays.toString(mInterfaces));
//                    mv.visitMethodInsn(INVOKESTATIC, "com/example/bytesinsert0507/ToastUtils", "ShowToast", "(Landroid/content/Context;Ljava/lang/String;)V", false);
                }
            };
        }
        return methodVisitor;
    }

    private boolean isMatchOnClickListener(String methodName) {
        if (mInterfaces == null) {
            return false;
        }
        for (String mInterface : mInterfaces) {
            if (mInterface.equals("android/view/View$OnClickListener") && "onClick".equals(methodName)) {
                return true;
            }
        }
        return false;
    }
}
