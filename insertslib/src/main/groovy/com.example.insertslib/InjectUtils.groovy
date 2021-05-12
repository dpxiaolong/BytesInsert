package com.example.insertslib

import groovy.io.FileType
import groovyjarjarasm.asm.ClassWriter
import org.apache.commons.io.FileUtils
import proguard.io.ClassReader


public class InjectUtils {
    static processClassFile(File inputDir, File destDir, File temporaryDir){

        def sourcePath = inputDir.absolutePath
        def destPath = destDir.absolutePath

        inputDir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
//            FileOutputStream outputStream = null
            try {
                def className = file.name
                System.out.println("processClassFile className: "+className)
            } catch (Exception exception) {
                println("---MockPlugin: file handle error = " + exception.toString())
            } finally {
//                outputStream.close()
            }

        }

    }

//    private static byte[] modifyClass(inputStream) {
//        final ClassReader classReader = new ClassReader(inputStream)
//        final ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
//        final ClassVisitor classVisitor = new MockClassVisitor(classWriter)
//        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
//        return classWriter.toByteArray()
//    }
}