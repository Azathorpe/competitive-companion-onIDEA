package com.azathorpe.cci.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JdkPathUtil {
    private static final Logger log = LoggerFactory.getLogger(JdkPathUtil.class);
    public static String JDK_PATH = "";

    /**
     * 获取当前项目使用的 JDK 主目录路径
     */
    public static String getProjectJdkHomePath(Project project) {
        if (project == null) return null;

        // 方法一：通过 ProjectJdkTable 获取项目级别的 SDK
        Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();

        if (projectSdk != null) {
            // getHomePath() 返回的是 JDK 的安装根目录，例如: /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
            JDK_PATH = projectSdk.getHomePath();
            log.info("JDK path has been loaded, PATH: {}", JDK_PATH);
            return projectSdk.getHomePath();
        }

        return null;
    }
}