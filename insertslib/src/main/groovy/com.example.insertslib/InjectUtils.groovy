package com.example.insertslib

import com.android.SdkConstants
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter


public class InjectUtils {
    static String TAG = "InjectUtils"
    static processClassFile(File inputDir, File destDir, File temporaryDir){

        def sourcePath = inputDir.absolutePath
        def destPath = destDir.absolutePath

        inputDir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->

            FileOutputStream outputStream = null
            try {
                def className = file.name
                System.out.println("processClassFile className: "+className)
//                if(isNeedClassFile(file)){
                def inputStream = new FileInputStream(file)
                // new modifiedFile
                File modifiedFile = new File(temporaryDir, className)
                System.out.println("new File(temporaryDir, className): " + className)

                if (modifiedFile.exists()) {
                    modifiedFile.delete()
                }
                modifiedFile.createNewFile()
                // write new data to modifiedFile
                outputStream = new FileOutputStream(modifiedFile)
                System.out.println(" before modifyClass " + className)

                outputStream.write(modifyClass(inputStream))
                System.out.println(" after modifyClass " + className)

                // modifiedFile replace destPath file
                def target = new File(file.absolutePath.replace(sourcePath, destPath))
                if (target.exists()) {
                    target.delete()
                }
                FileUtils.copyFile(modifiedFile, target)
                modifiedFile.delete()
//                }
            } catch (Exception exception) {
                println("---MockPlugin: file handle error = " + exception.toString())
            } finally {
                if(null != outputStream){
                    outputStream.close()
                    outputStream = null
                }
            }

        }

    }

    private static byte[] modifyClass(inputStream) {
        System.out.println("modifyClass enter")
        final ClassReader classReader = new ClassReader(inputStream)
        final ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        final ClassVisitor classVisitor = new CustomClassVisitor(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        return classWriter.toByteArray()
    }

    private static boolean isNeedClassFile(File file) {
        def className = file.name

        if (!className.endsWith(SdkConstants.DOT_CLASS)) {
            return false
        }

        if (className.contains("R\$") ||
                className.contains("R2\$") ||
                className.endsWith("R.class") ||
                className.endsWith("R2.class") ||
                className.endsWith("BuildConfig.class")) {
            return false
        }

        def filePackagePath = file.getAbsolutePath().replace(File.separator, ".")
        if (filePackagePath.contains('android.support') ||
                filePackagePath.contains('androidx.') ||
                filePackagePath.contains('android.arch')) {
            return false
        }

        return true
    }
}