package com.azathorpe.cci.settings;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class PopupWindow extends DialogWrapper {

    private JPanel panel = new JPanel();
    private JLabel messageLabel;
    private JButton actionButton;

    protected PopupWindow(@Nullable Project project, boolean canBeParent) {
        super(project, canBeParent);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        panel = new JPanel(new BorderLayout());

        messageLabel = new JLabel("这是一个通过设置按钮弹出的新窗口");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        actionButton = new JButton("执行操作");
        actionButton.addActionListener(e -> {
            Messages.showMessageDialog("操作已执行！", "提示", Messages.getInformationIcon());
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(actionButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}
