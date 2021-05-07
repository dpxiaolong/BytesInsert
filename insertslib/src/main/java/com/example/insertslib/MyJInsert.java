package com.example.insertslib;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by Android Studio.
 * User: zhenlong.luo
 * Date: 2021/5/7
 * description:
 */
public class MyJInsert implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("这是插件 MyJInsert ");
    }
}
