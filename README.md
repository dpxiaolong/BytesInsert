# BytesInsert
android 字节码插桩项目
项目介绍：
本项目演示在编译期，通过批量操作字节码文件（.class）文件，实现插件对整个工程的字节码文件的识别和定向修改过程。

Demo演示效果：
引入插件即可对所有的点击事件监听并显示出所在页面名称，接口名称


原理：
1）android的完整打包流程如下，本次项目hook/修改代码节点如下图箭头


2）通过gradle开放的apk，添加用户自定义transform对字节码文件（.class）进行操作，实现全局点击事件监听




参考资料：
官方gradle插件开发流程：
https://docs.gradle.org/current/userguide/custom_plugins.html
