package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.intellij.openapi.diagnostic.Logger;

import java.io.*;

/**
 * 工具类 - 文件相关
 *
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
        if (currentFilePath.equals("NOT_DEFINED")) {
            return null;
        }
        String questionDataPath = Infos.saveImpls.getQuestionDatas(currentFilePath);
        if (!new File(questionDataPath).exists()) {
            return null;
        }
        return JSON.parseObject(getFileContent(questionDataPath), Question.class);
    }

    /**
     * 创建一个新的文件，如果文件已经存在则不进行任何操作
     *
     * @param path 文件路径
     */
    public static void fileInitialize(String path) throws IOException {
        fileInitialize(path, "");
    }

    /**
     * 创建一个新的文件，并写入内容，如果文件已经存在则不进行任何操作
     *
     * @param path    文件路径
     * @param content 文件内容
     */
    public static void fileInitialize(String path, String content) throws IOException {
        File file = new File(path);
        if (file.createNewFile())
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
     *
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
     *
     * @param ori    源文件
     * @param target 目标位置
     */
    public static void copyFileTo(File ori, File target) {
        try {
            FileReader fr = new FileReader(ori);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(target);

            String line = "";
            while ((line = br.readLine()) != null) {
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

    public static String exactFQCN(String sourceFilePath) {
        // 1. 从源文件解析 package 声明
        String packageName = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("package ")) {
                    // "package com.example;" → "com.example"
                    packageName = line.substring(8, line.indexOf(';')).trim();
                    break;
                }
                // 跳过注释和空行之后还没找到 package 就可以停了
                if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*")) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2. 从文件路径提取类名
        String fileName = new File(sourceFilePath).getName();  // "Solution.java"
        String className = fileName.substring(0, fileName.lastIndexOf('.')); // "Solution"

        // 3. 拼接全限定类名
        // → "com.example.Solution"
        return packageName.isEmpty() ? className : packageName + "." + className;
    }
}
