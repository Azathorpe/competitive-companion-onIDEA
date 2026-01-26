package com.azathorpe.cci.window;

import com.azathorpe.cci.service.SocketService;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class WindowFactory implements ToolWindowFactory, DumbAware {
    private JPanel mainPanel = new JPanel(new GridLayout(2,1));
    private JButton buttonCreateProblem = new JButton("Create Problem");
    private JButton buttonHelp = new JButton("How to use this plugin?");

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        mainPanel.add(buttonCreateProblem);
        mainPanel.add(buttonHelp);

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);

        toolWindow.getContentManager().addContent(content);
    }
}
