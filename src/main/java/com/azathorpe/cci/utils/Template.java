package com.azathorpe.cci.utils;

import com.azathorpe.cci.model.Question;

import java.io.*;

/**
 * 模板文件,用于生成题目的代码文件
 * @author Azathorpe
 * @version 1.1
 * 更改日志：
 * 1.1 - 整合了读取文件的方法 直接使用FileUtils.getFileContent()方法读取模板文件内容，减少了代码冗余
 * 1.0 - 初始版本
 */
public class Template {
    public static final String CLASS_NAME = "${CLASS_NAME}";
    public static final String PACKAGE_NAME = "${PACKAGE_NAME}";
//    public static final String METHOD_NAME = "${METHOD_NAME}";
    public static final String QUESTION_NAME = "${QUESTION_NAME}";
    public static final String GROUP_NAME = "${GROUP_NAME}";
    public static final String URL = "${URL}";
    public static final String INTERACTIVE = "${INTERACTIVE}";

    /**
     * 将模板文件复制到目标路径，并替换其中的变量
     * @param targetPath 目标路径
     * @param question 题目信息，用于替换模板中的变量
     */
    public static void copyTemplate(String targetPath, Question question) {
        //确保模板文件夹存在
        FilesUtils.folderInitialize(Infos.userTemplateFolder);

        //替换变量
        //获取所有替换的变量
        String template = FilesUtils.getFileContent(Infos.userTemplateFolder + "\\" + Infos.settings.getLanguage() + ".template");
        template = template.replace(CLASS_NAME, question.getName());
        template = template.replace(PACKAGE_NAME, question.getGroup());
        template = template.replace(QUESTION_NAME, question.getName());
        template = template.replace(GROUP_NAME, question.getGroup());
        template = template.replace(URL, question.getUrl());
        template = template.replace(INTERACTIVE, question.getInteractive());

        try {
            FileWriter fw = new FileWriter(targetPath);
            fw.write(template);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
