package com.azathorpe.cci.listener;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * 当文件被切换时，我们刷新侧边栏的数据
 * @author Azathorpe
 * @version 1.0
 */
public class FileSwitchListener implements FileEditorManagerListener {

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
//        FileEditorManagerListener.super.selectionChanged(event);
        System.out.println("File was changed");
        if (event.getNewFile() != null) {
            System.out.println("new " + event.getNewFile().getName());
        }
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
