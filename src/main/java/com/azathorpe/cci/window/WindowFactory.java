package com.azathorpe.cci.window;

import com.azathorpe.cci.actions.utils.PersistenceStorage;
import com.azathorpe.cci.service.SocketService;
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

/**
 * @author Azathorpe
 * @version 1.0
 */
public class WindowFactory implements ToolWindowFactory, DumbAware {
    private JPanel mainPanel = new JPanel(new GridLayout(2,1));
    private JButton buttonCreateProblem = new JButton("Create Problem");
    private JButton buttonHelp = new JButton("How to use this plugin?");

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        //获取项目文件夹路径
        PersistenceStorage.QUESTION_FOLDER_PATH = project.getBasePath() + "/.cci_questions/";
        PersistenceStorage.QUESTION_SRC_FOLDER_PATH = project.getBasePath() + "/src/";

        //设置按钮帮助信息
        mainPanel.add(buttonCreateProblem);
        mainPanel.add(buttonHelp);
        mainPanel.add(new JLabel(CommonDataKeys.PSI_FILE.getName()));

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);

        toolWindow.getContentManager().addContent(content);

        //Load settings
        PersistenceStorage.loadPropertiesFile();

        prepare();
    }

    void prepare(){
        //Auto fetch problems?
        if(PersistenceStorage.settings.getAutoFetchProblems().equals("true")){
            System.out.println("Auto fetch problems is enabled.");
            SocketService.startServer();
        }
    }


    void clearPanel(){
        mainPanel.removeAll();
    }

}
