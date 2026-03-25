package com.azathorpe.cci.utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.cci.model.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 存放用户配置信息的类，包含用户的编程语言、代码模板等信息
 */
public class Infos {
    public static final String userConfigPath = System.getProperty("user.home") + "/.cci/properties.json";
    public static final String userTemplateFolder = System.getProperty("user.home") + "/.cci/templates";

    public static Settings settings;

    static {
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
    }
}
