package com.example.insertslib

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project;


class MyGInsert implements Plugin<Project>{
//    public static final String EXTENSION_NAME = "MyExtension"

    @Override
    void apply(Project project) {
//        project.extensions.create(EXTENSION_NAME, MyExtension)
        System.out.println("这是插件 groovy MyJInsert ");
        def android = project.extensions.getByType(BaseExtension)
        System.out.println("这是插件 groovy getByType android: "+android);

        android.registerTransform(new InsertTransform())
        System.out.println("这是插件 groovy MyJInsert InsertTransform android ");
    }
}