package com.azathorpe.cci.window;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

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
        mainPanel.add(new JLabel(CommonDataKeys.PSI_FILE.getName()));

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);

        toolWindow.getContentManager().addContent(content);
    }


    private void clearPanel(){
        mainPanel.removeAll();
    }

}
