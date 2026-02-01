package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.model.Settings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Azathorpe
 * @version 1.0
 */
@SuppressWarnings("unused")
public class PersistenceStorage {
    public static final String PROPERTIES_FILE_PATH = System.getProperty("user.home") + "/.cci/properties.json";
    /**
     * 初始化前禁止使用
     */
    public static String CODE_TEMPLATE_FILE_PATH = System.getProperty("user.home") + "/.cci/";

    public static final String AUTO_FETCH_PROBLEM = "auto_fetch_problem";

    public static Settings settings = new Settings();

    /**
     * 存放问题集的目录
     */
    public static String QUESTION_FOLDER_PATH = "";
    /**
     * 存放实际代码的目录
     */
    public static String QUESTION_SRC_FOLDER_PATH = "";

    /**
     * Save question file to local storage
     * @param question Question object
     */
    public static void saveQuestionFile(Question question){
        String filePath = QUESTION_FOLDER_PATH + "/" + question.getGroup() + "/" + question.getName() + ".json";
        File file = checkFileExist(filePath);

        //Write JSON to file
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(question.getTestCases());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        saveSolvedFile(question.getName(), question.getGroup());
    }

    /**
     * Save solved file template to local storage
     * @param questionName Question name
     * @param questionGroup Question group
     */
    public static void saveSolvedFile(String questionName,String questionGroup){
        System.out.println("Saving solved file for " + questionName);
        //TODO: 根据不同语言选择不同模板
        String filePath = QUESTION_SRC_FOLDER_PATH + questionGroup + "/" + questionName + ".java";
        File file = checkFileExist(filePath);
        //复制Template文件内容到Solved文件
        try {
            Files.copy(Path.of(get_CODE_TEMPLATE_FILE_PATH()), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied template to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get code template file as InputStream
     * @param questionName question name
     * @param questionGroup question group
     * @return InputStream of code template file
     */
    public static void saveSolvedFileInTemplate(String questionName,String questionGroup) {
        System.out.println("Loading code template file for " + questionName);
        //TODO: 根据不同语言选择不同模板
        String output_path = QUESTION_SRC_FOLDER_PATH + questionGroup + "/" + questionName + ".java";
        File file = checkFileExist(output_path);
        //读取Solved文件内容到InputStream

        try(InputStream is = new FileInputStream(new File(get_CODE_TEMPLATE_FILE_PATH()))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                if(PatternUtils.containsTemplateVariable(line)){

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 储存设置文件
     */
    public static void savePropertiesFile(){
        checkFileExist(PROPERTIES_FILE_PATH);
        try(FileWriter fw = new FileWriter(PROPERTIES_FILE_PATH)){
            fw.write(JSON.toJSONString(settings));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载设置文件
     */
    public static void loadPropertiesFile(){
        checkFileExist(PROPERTIES_FILE_PATH);
        StringBuilder sb = new StringBuilder();
        try(InputStream is = new FileInputStream(PROPERTIES_FILE_PATH)){
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        settings = JSON.parseObject(sb.toString(), Settings.class);

        // 初始化CODE_TEMPLATE路径
//        CODE_TEMPLATE_FILE_PATH += settings.getLanguage()+".template";
    }

    /**
     * Check if file exists, if not create it
     * @param filePath filepath
     * @return File object
     */
    private static File checkFileExist(String filePath) {
        File file = new File(filePath);

        if(!file.exists()){
            try {
                Files.createDirectories(Paths.get(file.getParent()));
                Files.createFile(Paths.get(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return file;
    }

    public static void initCodeTemplateFile(String language) {
        checkFileExist(CODE_TEMPLATE_FILE_PATH + language + ".template");
    }

    public static String get_CODE_TEMPLATE_FILE_PATH(){
        return CODE_TEMPLATE_FILE_PATH + settings.getLanguage()+".template";
    }

    /**
     * 通过语言然后更改对应的模板文件
     */
    public static void changeTemplateFile(String content){
        String language = settings.getLanguage();
        String templateFilePath = CODE_TEMPLATE_FILE_PATH + language + ".template";
        File file = checkFileExist(templateFilePath);
        try(FileWriter fw = new FileWriter(file)){
            fw.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过文件更改对应的模板文件(直接复制文件)
     * @param file Files path
     */
    public static void changeTemplateFile(File file){
        String language = settings.getLanguage();
        String templateFilePath = CODE_TEMPLATE_FILE_PATH + language + ".template";
        File targetFile = checkFileExist(templateFilePath);
        try {
            Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
