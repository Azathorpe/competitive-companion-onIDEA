package com.azathorpe.cci.actions.editor;

import com.azathorpe.cci.utils.PersistenceStorage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SetCurrentLanguageTemplate extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(PlatformDataKeys.PSI_FILE);
        if (psiFile != null) {
            // 获取虚拟文件对象
            VirtualFile virtualFile = psiFile.getVirtualFile();
            if (virtualFile != null) {
                // 获取文件的路径
                String filePath = virtualFile.getPath();
                System.out.println("当前打开的文件路径: " + filePath);
                PersistenceStorage.changeTemplateFile(new File(filePath));
            }
        }
    }
}
