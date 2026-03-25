package com.azathorpe.cci.utils;

import com.azathorpe.cci.impls.Impls;
import com.azathorpe.cci.impls.JavaImpl;
import com.azathorpe.cci.model.Question;

/**
 * 持久化存储
 */
public class PersistentStorage {

    /**
     * 基本的配置文件路径，存储在用户的home目录下的.cci文件夹中
     */
    static String settingsPath = System.getProperty("user.home") + "/.cci/";

    static String basePath = "NOT_DEFINED";

    /**
     * 测试用例的位置
     */
    static String testsPath = "/.cci_question/testData/";

    /**
     * 已经解决过的问题存储的位置
     */
    static String solvedPath = "/.cci_question/solved/";

    /**
     * 保存题目信息和测试数据的实现类，根据不同的编程语言来实现这个接口
     */
    public static Impls saveImpls = null;

    /**
     * 新建一个题目文件，内容为题目的基本信息（题目名称、题目链接、时间限制、内存限制等）
     */
    public static void createProblemFile(Question question) {
        //清除当前文件的内容，写入题目的基本信息
        if(saveImpls == null){
            throw new RuntimeException("Save implementation is not defined, please call WindowFactory.projectNeeded(project) first");
        }
        saveImpls.saveProblem(question,basePath);
    }

    /**
     * 新建一个测试数据文件，内容为题目的测试数据
     */
    public static void createTestDataFile(Question question) {
        if (saveImpls == null){
            throw new RuntimeException("Save implementation is not defined, please call WindowFactory.projectNeeded(project) first");
        }
        saveImpls.saveTests(question,basePath);
    }

    public static String getSettingsPath() {
        if(settingsPath.equals("NOT_DEFINED")){
            throw new RuntimeException("Settings path is not defined, please call WindowFactory.projectNeeded(project) first");
        }
        return settingsPath;
    }

    public static void setSettingsPath(String settingsPath) {
        PersistentStorage.settingsPath = settingsPath;
    }

    public static String getBasePath() {
        if(basePath.equals("NOT_DEFINED")){
            throw new RuntimeException("Base path is not defined, please call WindowFactory.projectNeeded(project) first");
        }
        return basePath;
    }

    public static void setBasePath(String basePath) {
        PersistentStorage.basePath = basePath;
    }

    static {
        //TODO: 从配置文件中读取使用的代码(用的环境 Pycharm or IDEA)，并且根据环境设置不同的Impls 暂时先默认使用JavaImpl
        saveImpls = new JavaImpl();
    }
}
