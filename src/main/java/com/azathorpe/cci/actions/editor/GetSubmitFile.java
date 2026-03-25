package com.azathorpe.cci.actions.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * 把解决文件转换为可以直接运行的Main类文件，这样就可以直接提交了
 */
public class GetSubmitFile extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
        String filePath = null;
        if (psiFile != null)
            filePath = psiFile.getVirtualFile().getPath();
        else
            return;

        String targetPath = anActionEvent.getProject().getBasePath() + "/src/Main.java";

        //copy file to Main
        //读取这个文件 去除package语句 替换类名为Main
        //写入到Main.java中

        StringBuilder sb = getStringBuilder(filePath);

        try {
            FileWriter fw = new FileWriter(targetPath);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static @NotNull StringBuilder getStringBuilder(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("package "))
                    continue;
                if(line.contains("public class ")){
                    sb.append("public class Main {");
                    sb.append("\n");
                    continue;
                }
                sb.append(line);
                sb.append("\n");
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }
}
