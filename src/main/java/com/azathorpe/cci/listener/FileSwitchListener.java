package com.azathorpe.cci.listener;

import com.azathorpe.cci.utils.InfosUtils;
import com.azathorpe.cci.utils.PersistenceStorage;
import com.azathorpe.cci.utils.TemplateParser;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * 当文件被切换时，我们刷新侧边栏的数据
 * @author Azathorpe
 * @version 1.0
 */
public class FileSwitchListener implements FileEditorManagerListener {

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
//        FileEditorManagerListener.super.selectionChanged(event);

        if (event.getNewFile() != null) {
            InfosUtils.modifyEnv(InfosUtils.PATTERN_FILE_PATH,event.getNewFile().getPath());
            InfosUtils.modifyEnv(InfosUtils.PATTERN_FILE_NAME,event.getNewFile().getName());
        }

//        System.out.println("Paresing template for new file: " + event.getNewFile());
//        TemplateParser.parseTemplate(new File(PersistenceStorage.CODE_TEMPLATE_FILE_PATH), new File(InfosUtils.env.get(InfosUtils.PATTERN_FILE_PATH)));
    }

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        FileEditorManagerListener.super.fileOpened(source, file);
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        FileEditorManagerListener.super.fileClosed(source, file);
    }

}
