package com.azathorpe.cci.settings;

import com.azathorpe.cci.utils.Infos;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 调整设置的地方
 * @author Azathorpe
 * @version 1.0
 */
public class CCISettingsConfigurable implements Configurable {
    boolean isModified = false;
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "CCI";
    }

    @Override
    public @Nullable JComponent createComponent() {
        //创建一个简单的设置界面，它包含一个下拉框，用户可以选择是否自动监听端口
        JPanel panel = new JPanel(new GridLayout(2,1));

        JPanel autoListenPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Auto Listen Port:");
        ComboBox<String> comboBox = new ComboBox<>(new String[]{"Enable", "Disable"});
        comboBox.setSelectedIndex(Infos.settings.getAutoFetchProblems().equals("true") ? 0 : 1);
        comboBox.addActionListener(e -> {
            boolean autoListenPort = comboBox.getSelectedIndex() == 0;
            Infos.settings.setAutoFetchProblems(autoListenPort ? "true" : "false");
            isModified = true;
        });
        autoListenPanel.add(label, BorderLayout.WEST);
        autoListenPanel.add(comboBox, BorderLayout.CENTER);
        panel.add(autoListenPanel);

        //再添加一个下拉框，用户可以选择默认的编程语言
        JLabel labelLanguage = new JLabel("Default Language:");
        ComboBox<String> comboBoxLanguage = new ComboBox<>(new String[]{Infos.JAVA, Infos.PYTHON, Infos.CPP, Infos.C});
        comboBoxLanguage.setSelectedIndex(
                        Infos.settings.getLanguage().equals(Infos.JAVA) ? 0 :
                        Infos.settings.getLanguage().equals(Infos.PYTHON) ? 1 :
                        Infos.settings.getLanguage().equals(Infos.C) ? 2 :
                        3);
        comboBoxLanguage.addActionListener(e -> {
            String language = Objects.requireNonNull(comboBoxLanguage.getSelectedItem()).toString();
            Infos.settings.setLanguage(language);
            isModified = true;
        });
        JPanel languagePanel = new JPanel(new BorderLayout());
        languagePanel.add(labelLanguage, BorderLayout.WEST);
        languagePanel.add(comboBoxLanguage, BorderLayout.CENTER);
        panel.add(languagePanel);

        return panel;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() {
        //在用户点击应用按钮时，我们将设置保存到磁盘
        Infos.saveSettings(Infos.settings);
        Infos.updateSettings();
        isModified = false;
    }
}
