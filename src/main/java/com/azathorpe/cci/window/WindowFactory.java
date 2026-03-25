package com.azathorpe.cci.window;

import com.azathorpe.cci.service.SocketService;
import com.azathorpe.cci.utils.Infos;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * 调整单独的Panel的地方
 *
 * @author Azathorpe
 * @version 1.0
 */
public class WindowFactory implements ToolWindowFactory, DumbAware {
    private JPanel mainPanel = new JPanel(new GridLayout(2, 1));
    private JButton buttonCreateProblem = new JButton("Create Problem");
    private JButton buttonHelp = new JButton("How to use this plugin?");

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        initialization(project);
    }

    public static void initialization(Project project) {
        PersistentStorage.setBasePath(project.getBasePath());
        //设置自动监听端口
        Infos.updateSettings();
        if (Infos.settings.getAutoFetchProblems().equals("true")) {
            SocketService.startServer();
        } else {
            SocketService.stopServer();
        }
    }

}
