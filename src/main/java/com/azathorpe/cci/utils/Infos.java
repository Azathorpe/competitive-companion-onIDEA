package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Settings;
import com.azathorpe.cci.service.SocketService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import runner.compiler.CPPCompiler;
import runner.compiler.Compiler;
import runner.compiler.JavaCompiler;

import java.io.*;

/**
 * 存放用户配置信息的类，包含用户的编程语言、代码模板等信息
 * 1.1:添加了获取Project
 * @version 1.1
 */
public class Infos {
    public static final String userConfigPath = System.getProperty("user.home") + File.separator + ".cci" + File.separator + "properties.json";
    public static final String userTemplateFolder = System.getProperty("user.home") + File.separator + ".cci" + File.separator + "templates";
    public static final String JAVA = "Java";
    public static final String C = "C";
    public static final String CPP = "C++";
    public static final String PYTHON = "Python";
    private static final Log log = LogFactory.getLog(Infos.class);

    public static Settings settings = new Settings();
    public static Compiler compiler = null;

    /**
     * 更新用户的配置信息，重新读取配置文件并解析成Settings对象
     */
    public static void updateSettings(){
        //检查文件是否存在
        try {
            FilesUtils.fileInitialize(userConfigPath,settings.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //读取用户的配置信息，如果没有找到配置文件或者配置文件中没有语言信息，则使用默认的语言（Java）
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(userConfigPath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null)
                sb.append(line);

            br.close();
            fr.close();

        }catch(FileNotFoundException e) {
            throw new RuntimeException("User config file not found, please create a properties.json file in " + userConfigPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        settings = JSON.parseObject(sb.toString(), Settings.class);

        //更新infos的Compile
        if(settings.getLanguage().equals(JAVA)) {
            compiler = new JavaCompiler();
            log.info("Compiler load in Java");
        }
        else if(settings.getLanguage().equals(CPP)) {
            compiler = new CPPCompiler();
            log.info("Compiler load in CPP");
        }
        else
            compiler = new JavaCompiler();
    }

    /**
     * 保存用户的配置信息，写入配置文件中
     * @param settings 用户的配置信息
     */
    public static void saveSettings(Settings settings) {
        try {
            FileWriter fw = new FileWriter(userConfigPath);
            fw.write(settings.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向上帝祈祷不会出错吧 ...
     * @return ...
     */
    public static Project getProject() {
        return ProjectManager.getInstance().getOpenProjects()[0];
    }

    static {
        //读取用户的配置信息，如果没有找到配置文件或者配置文件中没有语言信息，则使用默认的语言（Java）
        Infos.updateSettings();

        //TODO: 可能存在的BUG 开始的时候用户没有配置文件，导致Infos.settings为null，调用getAutoFetchProblems方法会抛出NullPointerException
        if (Infos.settings.getAutoFetchProblems().equals("true")) {
            SocketService.startServer();
        } else {
            SocketService.stopServer();
        }
    }
}
