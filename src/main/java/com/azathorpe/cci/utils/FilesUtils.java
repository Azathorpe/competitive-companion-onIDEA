package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;

import java.io.*;

/**
 * 工具类 - 文件相关
 * @author Azathorpe
 * @version 1.0
 */
public class FilesUtils {
    public static String getFileContent(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static Question getCurrentQuestionData(String currentFilePath) {
        if(currentFilePath.equals("NOT_DEFINED")){
            return null;
        }
        String questionDataPath = PersistentStorage.saveImpls.getQuestionDatas(currentFilePath);
        if (!new File(questionDataPath).exists()) {
            return null;
        }
        return JSON.parseObject(getFileContent(questionDataPath), Question.class);
    }
}
