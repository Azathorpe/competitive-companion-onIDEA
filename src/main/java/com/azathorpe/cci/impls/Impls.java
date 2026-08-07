package com.azathorpe.cci.impls;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 存储文件的接口，定义了保存题目信息和测试数据的方法
 * 具体的实现类需要根据不同的编程语言来实现这些方法
 * @author Azathorpe
 * @version 1.0
 */
public interface Impls {
    /**
     * 保存题目信息到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     * @param question
     * @param path
     */
    String saveProblem(Question question,String path);

    /**
     * 保存测试数据到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     * @param question
     * @param path
     */
    default String saveTests(Question question,String path){
        String targetFolder = path + "/.cci_questions/" + question.getGroup(),fileName = question.getName() + ".json";
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

    String getQuestionDatas(String currentFilePath);


}
