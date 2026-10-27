package com.azathorpe.cci.impls;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.Debugger;
import com.azathorpe.cci.utils.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 存储文件的接口，定义了保存题目信息和测试数据的方法
 * 具体的实现类需要根据不同的编程语言来实现这些方法
 *
 * @author Azathorpe
 * @version 1.0
 */
public abstract class Impls {
    String suffix;

    public Impls(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 保存题目信息到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     *
     * @param question
     * @param path
     */
    public String saveProblem(Question question, String path) {
        String targetFolder = path + "/src/" + question.getGroup(), fileName = question.getName() + suffix;
        File file = new File(targetFolder);
        if (!file.exists())
            file.mkdirs();

        Template.copyTemplate(targetFolder + "/" + fileName, question);
        Debugger.log("Problem saved to: ", file.getAbsolutePath());

        return targetFolder + "/" + fileName;
    }

    /**
     * 保存测试数据到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     *
     * @param question
     * @param path
     */
    public String saveTests(Question question, String path) {
        String targetFolder = path + "/.cci_questions/" + question.getGroup(), fileName = question.getName() + ".json";
        File file = new File(targetFolder);
        if (!file.exists())
            file.mkdirs();
        file = new File(targetFolder + "/" + fileName);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(JSON.toJSONString(question));
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return targetFolder + "/" + fileName;
    }

    public String getQuestionDatas(String currentFilePath) {
        return currentFilePath.replace("src", ".cci_questions").replace(suffix, ".json");
    }


}
