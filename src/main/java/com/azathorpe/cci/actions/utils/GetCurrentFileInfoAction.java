package com.azathorpe.cci.actions.utils;

import com.intellij.ide.actions.CreateFileAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author Azathorpe
 * @version 1.0
 */
public class GetCurrentFileInfoAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(CommonDataKeys.PSI_FILE);
        if (psiFile != null) {
            // 获取虚拟文件对象
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile != null) {
                // 获取文件的路径
                String filePath = virtualFile.getName();
                System.out.println("当前打开的文件路径: " + filePath);
            }
        }
    }
}
