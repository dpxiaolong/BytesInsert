package com.example.insertslib

import org.gradle.api.Plugin
import org.gradle.api.Project;


class MyGInsert implements Plugin<Project>{

    @Override
    void apply(Project project) {
        System.out.println("这是插件 groovy MyJInsert ");
    }
}