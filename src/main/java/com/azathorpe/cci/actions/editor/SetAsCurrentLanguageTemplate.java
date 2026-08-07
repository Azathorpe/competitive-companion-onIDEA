package com.azathorpe.cci.actions.editor;

import com.azathorpe.cci.utils.FilesUtils;
import com.azathorpe.cci.utils.Infos;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * 设置当前编程语言
 */
public class SetAsCurrentLanguageTemplate extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //获取当前打开的文件路径
        System.out.println(PersistentStorage.getLastChangedFilePath());

        //复制文件到模板位置
        FilesUtils.copyFileTo(new File(PersistentStorage.getLastChangedFilePath()),new File(Infos.userTemplateFolder + "\\" + Infos.settings.getLanguage() + ".template"));

    }
}
