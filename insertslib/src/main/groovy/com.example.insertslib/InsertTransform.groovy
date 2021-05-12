package com.example.insertslib

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils

import java.util.function.Consumer


public class InsertTransform extends Transform {

    InsertTransform() {

    }

    /**
     * Returns the unique name of the transform.
     *
     * <p>This is associated with the type of work that the transform does. It does not have to be
     * unique per variant.
     */
    @Override
    String getName() {
        return "InsertTransform"
    }

    /**
     * Returns the type(s) of data that is consumed by the Transform. This may be more than
     * one type.
     *
     * <strong>This must be of type {@link QualifiedContent.DefaultContentType}</strong>
     */
   /**
     CLASSES 代表处理的 java 的 class 文件，返回TransformManager.CONTENT_CLASS
     RESOURCES 代表要处理 java 的资源，返回TransformManager.CONTENT_RESOURCES
     *
     */

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指 Transform 要操作内容的范围，官方文档 Scope 有 7 种类型：
     *
     * EXTERNAL_LIBRARIES ： 只有外部库
     * PROJECT ： 只有项目内容
     * PROJECT_LOCAL_DEPS ： 只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY ： 只提供本地或远程依赖项
     * SUB_PROJECTS ： 只有子项目
     * SUB_PROJECTS_LOCAL_DEPS： 只有子项目的本地依赖项(本地jar)
     * TESTED_CODE ：由当前变量(包括依赖项)测试的代码
     *
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY  //PROVIDED_ONLY
    }

    /**
     * Returns whether the Transform can perform incremental work.
     *
     * <p>If it does, then the TransformInput may contain a list of changed/removed/added files, unless
     * something else triggers a non incremental run.
     */
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) {
        def outputProvider = transformInvocation.outputProvider
        def transformInputs = transformInvocation.inputs
        def temporaryDir = transformInvocation.context.temporaryDir

        //class文件操作位置
        System.out.println("InsertTransform transformInputs.directoryInputs.forEach")

        transformInputs.forEach(new Consumer<TransformInput>() {
            @Override
            void accept(TransformInput transformInput) {

                transformInput.directoryInputs.forEach(new Consumer<DirectoryInput>() {
                    @Override
                    void accept(DirectoryInput directoryInput) {
                        System.out.println("InsertTransform directoryInputs accept")
                        def inputDir = directoryInput.file
                        def destDir = outputProvider.getContentLocation(
                                directoryInput.name,
                                directoryInput.contentTypes,
                                directoryInput.scopes,
                                Format.DIRECTORY
                        )
                        FileUtils.copyDirectory(inputDir, destDir)
                        InjectUtils.processClassFile(inputDir, destDir, temporaryDir)
                    }
                })
            }
        })

        //jar码操作位置
//        transformInputs.jarInputs.forEach(new Consumer<Collection<JarInput>>() {
//            @Override
//            void accept(Collection<JarInput> jarInputs) {
//                //这里识别并操作jar文件
//
//                // below no process
//                def jarInputFile = jarInput.file
//                def destJar = outputProvider.getContentLocation(
//                        jarInput.name,
//                        jarInput.contentTypes,
//                        jarInput.scopes,
//                        Format.JAR
//                )
//                FileUtils.copyFile(jarInputFile, destJar)
//
//            }
//        })
    }
}