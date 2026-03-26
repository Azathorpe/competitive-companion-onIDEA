package com.azathorpe.cci.window;

import com.azathorpe.cci.model.Question;
import com.azathorpe.cci.utils.FilesUtils;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * 调整单独的Panel的地方
 *
 * @author Azathorpe
 * @version 1.0
 */
public class WindowFactory implements ToolWindowFactory, DumbAware {
    private static final JPanel mainPanel = new JPanel(new BorderLayout());
    private static final JPanel titlePanel = new JPanel(new GridLayout(2,1));
    private static final JPanel questionPanel = new JPanel();
    private static final JPanel settingsPanel = new JPanel();
    private static ToolWindow toolWindow;

    // Single title label we update on each file switch
    private static final JLabel titleLabel = new JLabel();
    private static final JLabel questionLabel = new JLabel();
    // Single scroll pane we reuse for test cases
    private static final JBScrollPane questionScrollPane = new JBScrollPane(questionPanel);
    // track whether content has been added to the tool window
    private static boolean initialized = false;

    private static final Logger LOGGER = Logger.getInstance(WindowFactory.class);

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        WindowFactory.toolWindow = toolWindow;
        flashToolWindow("NOT_DEFINED");
    }

    public static void flashToolWindow(String currentFilePath) {
        if (toolWindow == null) return;
        ContentManager contentManager = WindowFactory.getToolWindow().getContentManager();

        // If content hasn't been added yet (first time), create and add it once.
        if (!initialized || contentManager.getContentCount() == 0) {
            // Ensure panels are clean the first time
            titlePanel.removeAll();
            questionPanel.removeAll();
            settingsPanel.removeAll();
            mainPanel.removeAll();

            // setup layout and add panels
            mainPanel.add(titlePanel, BorderLayout.NORTH);
            mainPanel.add(questionPanel, BorderLayout.CENTER);
            //TODO: settingsPanel的内容还没有设计好，先放在这里，等设计好了再添加进去
            mainPanel.add(settingsPanel, BorderLayout.SOUTH);

            // configure title label once
            titleLabel.setFont(new Font("Consolas", Font.BOLD, 16));
            questionLabel.setFont(new Font("Consolas", Font.BOLD, 16));
            titlePanel.add(titleLabel);
            titlePanel.add(questionLabel);

            //添加主面板到工具窗口
            ContentFactory contentFactory = ContentFactory.getInstance();
            Content content = contentFactory.createContent(mainPanel, "", false);
            toolWindow.getContentManager().addContent(content);

            initialized = true;
        }

        // Update title based on current file
        Question currentQuestionData = FilesUtils.getCurrentQuestionData(currentFilePath);
        if (currentQuestionData != null) {
            titleLabel.setText(currentQuestionData.getGroup());
            questionLabel.setText(currentQuestionData.getName());
            System.out.println("Current question: " + currentQuestionData.getGroup() + " - " + currentQuestionData.getName());
        } else {
            titleLabel.setText("Choose a question file to view details");
            System.out.println("No question data found for file: " + currentFilePath);
        }

        questionPanel.add(questionScrollPane);

        //添加所有的测试样例信息
        if (currentQuestionData != null && currentQuestionData.getTestCases() != null) {
            JPanel testCasesPanel = new JPanel(new GridLayout(currentQuestionData.getTests().length,2));
            for (int i = 0; i < currentQuestionData.getTests().length; i++) {
                StringBuilder testCasesInfo = new StringBuilder("<html><body style='width: 300px;'>");
                testCasesInfo.append("Test Case ").append(i + 1).append(":<br>");
                testCasesInfo.append("Input: ").append(currentQuestionData.getTests()[i].getInput()).append("<br>");
                testCasesInfo.append("Expected Output: ").append(currentQuestionData.getTests()[i].getOutput()).append("<br><br>");
                testCasesInfo.append("</body></html>");
                JLabel testCasesLabel = new JLabel(testCasesInfo.toString());
                JButton testCaseButton = new JButton("Copy Test Case " + (i + 1));
                int finalI = i;
                testCaseButton.addActionListener(e -> {
                    String clipboardContent = currentQuestionData.getTests()[finalI].getInput();
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clipboardContent), null);
                });
                testCasesPanel.add(testCasesLabel);
                testCasesPanel.add(testCaseButton);
            }
            questionScrollPane.setViewportView(testCasesPanel);
        } else {
            JLabel noTestCasesLabel = new JLabel("No test cases available for this question.");
            questionScrollPane.setViewportView(noTestCasesLabel);
        }

        // Refresh UI
        titlePanel.revalidate();
        titlePanel.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();
        questionPanel.revalidate();
        questionPanel.repaint();
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
