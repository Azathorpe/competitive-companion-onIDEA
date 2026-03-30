package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.intellij.openapi.diagnostic.Logger;

import java.io.*;

/**
 * 工具类 - 文件相关
 * @author Azathorpe
 * @version 1.0
 */
public class FilesUtils {
    private static final Logger LOG = Logger.getInstance(FilesUtils.class);

    public static String getFileContent(String filePath) {
        try {
            System.out.println(filePath);
            fileInitialize(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    /**
     * 创建一个新的文件，如果文件已经存在则不进行任何操作
     * @param path 文件路径
     */
    public static void fileInitialize(String path) throws IOException {
        fileInitialize(path, "");
    }

    /**
     * 创建一个新的文件，并写入内容，如果文件已经存在则不进行任何操作
     * @param path 文件路径
     * @param content 文件内容
     */
    public static void fileInitialize(String path, String content) throws IOException {
        File file = new File(path);
        if(file.createNewFile())
            LOG.info("File created: " + file.getAbsolutePath());
        else {
            LOG.info("File already exists: " + file.getAbsolutePath());
            return;
        }
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.close();
    }

    /**
     * 创建一个新的文件夹，如果文件夹已经存在则不进行任何操作
     * @param path 文件夹路径
     */
    public static void folderInitialize(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                LOG.info("Folder created: " + file.getAbsolutePath());
            } else {
                LOG.warn("Failed to create folder: " + file.getAbsolutePath());
            }
        } else {
            LOG.info("Folder already exists: " + file.getAbsolutePath());
        }
    }

    /**
     * 复制文件到目标
     * @param ori 源文件
     * @param target 目标位置
     */
    public static void copyFileTo(File ori,File target){
        try {
            FileReader fr = new FileReader(ori);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(target);

            String line = "";
            while((line = br.readLine()) != null) {
                fw.write(line);
                fw.write("\n");
            }

            fw.close();
            br.close();
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
