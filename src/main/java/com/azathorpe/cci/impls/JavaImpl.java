package com.azathorpe.cci.impls;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.Debugger;
import com.azathorpe.cci.utils.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Java语言的实现类，负责将题目信息和测试数据保存到文件中
 * @author Azathorpe
 * @version 1.0
 */
public class JavaImpl implements Impls {

    @Override
    public String saveProblem(Question question,String path) {
        String targetFolder = path + "/src/" + question.getGroup(),fileName = question.getName() + ".java";
        File file = new File(targetFolder);
        if (!file.exists())
            file.mkdirs();

        Template.copyTemplate(targetFolder + "/" + fileName, question);
        Debugger.log("Problem saved to: ", file.getAbsolutePath());

        return targetFolder + "/" + fileName;
    }

    @Override
    public String saveTests(Question question,String path) {
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
}
