package com.azathorpe.cci.impls;

import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.Debugger;
import com.azathorpe.cci.utils.Template;

import java.io.File;

public class CImpl implements Impls{
    @Override
    public String saveProblem(Question question, String path) {
        String targetFolder = path + "/src/" + question.getGroup(), fileName = question.getName() + ".c";
        File file = new File(targetFolder);
        if (!file.exists())
            file.mkdirs();

        Template.copyTemplate(targetFolder + "/" + fileName, question);
        Debugger.log("Problem saved to: ", file.getAbsolutePath());

        return targetFolder + "/" + fileName;
    }

    @Override
    public String getQuestionDatas(String currentFilePath) {
        return currentFilePath.replace("src", ".cci_questions").replace(".c", ".json");
    }
}
