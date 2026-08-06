package com.azathorpe.cci.listener;

import com.azathorpe.cci.utils.PersistentStorage;
import com.azathorpe.cci.window.WindowFactory;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 当文件被切换时，我们刷新侧边栏的数据
 * @author Azathorpe
 * @version 1.0
 */
public class FileSwitchListener implements FileEditorManagerListener {

    private static final Logger LOGGER = Logger.getInstance(FileSwitchListener.class);

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        SwingUtilities.invokeLater(() -> {
            if (event.getNewFile() != null) {
                WindowFactory.flashToolWindow(event.getNewFile().getPath());
                System.out.println("File switched to: " + event.getNewFile().getPath());
                PersistentStorage.setLastChangedFilePath(event.getNewFile().getPath());
            }else{
                LOGGER.info("No file is currently selected.");
                //是Null 我就不处理了
            }
        });
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
