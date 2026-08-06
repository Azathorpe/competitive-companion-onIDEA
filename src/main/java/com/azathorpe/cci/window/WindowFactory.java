package com.azathorpe.cci.window;

import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.utils.FilesUtils;
import com.azathorpe.cci.utils.JdkPathUtil;
import com.azathorpe.cci.window.panels.QuestionPane;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
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
    private static final JBTabbedPane questionPanel = new JBTabbedPane();
    private static final JPanel titlePanel = new JPanel();

    private static ToolWindow toolWindow;
    private static boolean initialized = false;

    private static final Logger LOGGER = Logger.getInstance(WindowFactory.class);

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowFactory.toolWindow = toolWindow;
        JdkPathUtil.getProjectJdkHomePath(project);
        flashToolWindow("NOT_DEFINED");
    }

    public static void flashToolWindow(String currentFilePath) {
        if (toolWindow == null) return;
        ContentManager contentManager = toolWindow.getContentManager();
        Question currentQuestionData = FilesUtils.getCurrentQuestionData(currentFilePath);

        // First-time initialization: create content and add to tool window
        if (!initialized || contentManager.getContentCount() == 0) {
            mainPanel.removeAll();

            // Put questionPanel directly in CENTER — each tab's content wraps its own JBScrollPane
            mainPanel.add(questionPanel, BorderLayout.CENTER);
            mainPanel.add(titlePanel, BorderLayout.NORTH);

            ContentFactory contentFactory = ContentFactory.getInstance();
            Content content = contentFactory.createContent(mainPanel, "", false);
            contentManager.addContent(content);

            initialized = true;
        }

        // Update title bar
        titlePanel.removeAll();
        titlePanel.setLayout(new BorderLayout());
        if (currentQuestionData != null) {
            titlePanel.add(new JLabel(currentQuestionData.getGroup()), BorderLayout.NORTH);
            titlePanel.add(new JLabel(currentQuestionData.getName()), BorderLayout.CENTER);
        }

        // Update tabs
        questionPanel.removeAll();
        if (currentQuestionData != null) {
            LOGGER.info("Current question: " + currentQuestionData.getGroup() + " - " + currentQuestionData.getName());

            for (TestCase test : currentQuestionData.getTests()) {
                LOGGER.info("new input :" + test.getInput());
                LOGGER.info("new output :" + test.getOutput());

                int index = questionPanel.getTabCount();
                // Wrap each QuestionPane in a JBScrollPane so individual tabs can scroll
                QuestionPane pane = QuestionPane.createPane(test);
                JBScrollPane scrollPane = new JBScrollPane(pane);
                questionPanel.addTab("Test " + (index + 1), scrollPane);
            }

            // Auto-select the first tab
            if (questionPanel.getTabCount() > 0) {
                questionPanel.setSelectedIndex(0);
            }
        } else {
            // Show placeholder when no question data is available
            JLabel placeholder = new JLabel("Please Choose Problem First>>", SwingConstants.CENTER);
            questionPanel.addTab("Info", placeholder);
            LOGGER.info("No question data found for file: " + currentFilePath);
        }

        // Refresh UI
        mainPanel.revalidate();
        mainPanel.repaint();
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
