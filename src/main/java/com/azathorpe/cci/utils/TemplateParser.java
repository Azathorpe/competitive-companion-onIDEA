package com.azathorpe.cci.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TemplateParser {
    public static final String PACKAGE_NAME_TEMPLATE = "PACKAGE_NAME";
    public static final String CLASS_NAME_TEMPLATE = "CLASS_NAME";
    public static final String METHOD_NAME_TEMPLATE = "METHOD_NAME";
    public static final String FILE_NAME_TEMPLATE = "FILE_NAME";
    public static final String FILE_PATH_TEMPLATE = "FILE_PATH";
    public static final String PROJECT_NAME_TEMPLATE = "PROJECT_NAME";
    public static final String PROJECT_PATH_TEMPLATE = "PROJECT_PATH";

    public static void parseTemplate(File template, File targetPath){
//        System.out.println("Parsing template: " + template.getPath() + " to target path: " + targetPath.getPath());
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

        try{
            cfg.setDirectoryForTemplateLoading(new File(template.getPath()));
            cfg.setDefaultEncoding("UTF-8");

            //TODO: implement template diff language support
//            Template temp = cfg.getTemplate(new File(template,"Java.template").getName());
            Template temp = cfg.getTemplate("Java.template");

            //model
            Map<String, Object> root = new HashMap<>();
            root.put(PACKAGE_NAME_TEMPLATE, TemplateConstructor.constructProjectName(InfosUtils.getEnv(FILE_PATH_TEMPLATE)));
            root.put(CLASS_NAME_TEMPLATE, TemplateConstructor.constructClassName(InfosUtils.getEnv(FILE_NAME_TEMPLATE)));
//            root.put(METHOD_NAME_TEMPLATE, temp);
//            root.put(FILE_NAME_TEMPLATE, temp);
//            root.put(FILE_PATH_TEMPLATE, temp);
//            root.put(PROJECT_NAME_TEMPLATE, temp);
//            root.put(PROJECT_PATH_TEMPLATE, temp);

            //output
            FileWriter out = new FileWriter(targetPath);
            temp.process(root,out);
            out.close();


        } catch (IOException | TemplateException e) {
            e.getStackTrace();
        }
    }
}
