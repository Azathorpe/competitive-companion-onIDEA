package com.azathorpe.cci.impls;

import com.azathorpe.cci.model.Question;

/**
 * 存储文件的接口，定义了保存题目信息和测试数据的方法
 * 具体的实现类需要根据不同的编程语言来实现这些方法
 * @author Azathorpe
 * @version 1.0
 */
public interface Impls {
    /**
     * 保存题目信息到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     * @param question
     * @param path
     */
    String saveProblem(Question question,String path);

    /**
     * 保存测试数据到文件中，具体的实现类需要根据不同的编程语言来实现这个方法
     * @param question
     * @param path
     */
    String saveTests(Question question,String path);


}
