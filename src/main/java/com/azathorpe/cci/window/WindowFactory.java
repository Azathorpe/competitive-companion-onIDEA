package com.azathorpe.cci.window;

import com.azathorpe.cci.service.SocketService;
import com.azathorpe.cci.utils.Infos;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
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
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel titlePanel = new JPanel();
    private final JPanel questionPanel = new JPanel();
    private final JPanel settingsPanel = new JPanel();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
//        initialization(project);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(settingsPanel, BorderLayout.SOUTH);

        //设置标题面板
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        //添加主面板到工具窗口
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);

    }

//    public static void initialization(Project project) {
//        PersistentStorage.setBasePath(project.getBasePath());
//        //设置自动监听端口
//        Infos.updateSettings();
//        if (Infos.settings.getAutoFetchProblems().equals("true")) {
//            SocketService.startServer();
//        } else {
//            SocketService.stopServer();
//        }
//    }

}
