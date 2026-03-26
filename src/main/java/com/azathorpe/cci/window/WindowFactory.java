package com.azathorpe.cci.window;

import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.FilesUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
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
    private static final JPanel mainPanel = new JPanel(new BorderLayout());
    private static final JPanel titlePanel = new JPanel();
    private static final JPanel questionPanel = new JPanel();
    private static final JPanel settingsPanel = new JPanel();
    private static ToolWindow toolWindow;

    private static final Logger LOGGER = Logger.getInstance(WindowFactory.class);

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowFactory.toolWindow = toolWindow;
        flashToolWindow("NOT_DEFINED");
    }

    public static void flashToolWindow(String currentFilePath) {
        if(toolWindow == null) return;
        ContentManager contentManager = WindowFactory.getToolWindow().getContentManager();
        contentManager.removeAllContents(true);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(settingsPanel, BorderLayout.SOUTH);

        //设置标题面板
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        Question currentQuestionData = FilesUtils.getCurrentQuestionData(currentFilePath);
        if (currentQuestionData != null) {
            titleLabel.setText(currentQuestionData.getGroup() + " - " + currentQuestionData.getName());
            System.out.println("Current question: " + currentQuestionData.getGroup() + " - " + currentQuestionData.getName());
        }else{
            titleLabel.setText("Choose a question file to view details");
        }
        titlePanel.add(titleLabel);

        //添加主面板到工具窗口
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public static ToolWindow getToolWindow() {
        return toolWindow;
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
