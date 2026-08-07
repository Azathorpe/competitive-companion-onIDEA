package com.azathorpe.cci.window;

import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.utils.FilesUtils;
import com.azathorpe.cci.utils.JdkPathUtil;
import com.azathorpe.cci.utils.PersistentStorage;
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
    private static boolean updating = false;  // 防止 ChangeListener 递归

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

            //question panel的添加新的测试用例功能
            questionPanel.addChangeListener((e) -> {
                if (updating) return;
                int tabCount = questionPanel.getTabCount();
                if (tabCount > 0 && (tabCount - 1) == questionPanel.getSelectedIndex()) {
                    // 不能捕获外部的 currentQuestionData，因为 lambda 只注册一次，
                    // 捕获的是第一次 "NOT_DEFINED" 调用时的 null
                    String file = PersistentStorage.getLastChangedFilePath();
                    Question data = FilesUtils.getCurrentQuestionData(file);
                    if (data != null) {
                        data.addNewTestCase(new TestCase());
                        PersistentStorage.createTestDataFile(data);
                        updateQuestionPanel(data, file);
                    }
                }
            });

            ContentFactory contentFactory = ContentFactory.getInstance();
            Content content = contentFactory.createContent(mainPanel, "", false);
            contentManager.addContent(content);

            initialized = true;
        }

        updateQuestionPanel(currentQuestionData, currentFilePath);

        // Update title bar
        titlePanel.removeAll();
        titlePanel.setLayout(new BorderLayout());
        if (currentQuestionData != null) {
            titlePanel.add(new JLabel(currentQuestionData.getGroup()), BorderLayout.NORTH);
            titlePanel.add(new JLabel(currentQuestionData.getName()), BorderLayout.CENTER);
        }


        // Refresh UI
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static ToolWindow getToolWindow() {
        return toolWindow;
    }

    public static void updateQuestionPanel(Question currentQuestionData, String currentFilePath) {
        updating = true;
        try {
            questionPanel.removeAll();
            if (currentQuestionData != null) {
                LOGGER.info("Current question: " + currentQuestionData.getGroup() + " - " + currentQuestionData.getName());

                for (TestCase test : currentQuestionData.getTests()) {
                    LOGGER.info("new input :" + test.getInput());
                    LOGGER.info("new output :" + test.getOutput());

                    int index = questionPanel.getTabCount();
                    QuestionPane pane = QuestionPane.createPane(test);
                    JBScrollPane scrollPane = new JBScrollPane(pane);
                    questionPanel.addTab("Test " + (index + 1), scrollPane);
                }

                // "+" 标签用于添加新用例
                questionPanel.addTab("+", new JLabel());

                if (questionPanel.getTabCount() > 0) {
                    questionPanel.setSelectedIndex(0);
                }
            } else {
                JLabel placeholder = new JLabel("Please Choose Problem First>>", SwingConstants.CENTER);
                questionPanel.addTab("Info", placeholder);
                LOGGER.info("No question data found for file: " + currentFilePath);
            }
        } finally {
            updating = false;
        }
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
