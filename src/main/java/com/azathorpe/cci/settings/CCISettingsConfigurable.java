package com.azathorpe.cci.settings;

import com.azathorpe.cci.actions.utils.PersistenceStorage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBTabbedPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
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
        JComponent panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("CCI Settings");
        label.setVerticalTextPosition(SwingConstants.EAST);
        label.setBorder(BorderFactory.createEmptyBorder(5,5,50,5));
        panel.add(label);

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(mainPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JCheckBox checkBox = new JCheckBox("Auto fetch problem");
        checkBox.setSelected(PersistenceStorage.settings.getAutoFetchProblems().equals("true"));
//        mainPanel.add(checkBox);

        JComboBox<String> comboBox = new ComboBox<>();
        comboBox.addItem("Java");
        comboBox.addItem("Python");
        comboBox.setSelectedItem(PersistenceStorage.settings.getLanguage());
//        mainPanel.add(comboBox);

        mainPanel.add(new JPanel(){{
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(checkBox);
            add(Box.createRigidArea(new Dimension(20,0)));
            add(new JLabel("Default Language:"));
            add(comboBox);
        }});

        comboBox.addItemListener((e) -> {
            isModified = true;
            PersistenceStorage.settings.setLanguage((String) comboBox.getSelectedItem());
            //初始化一个代码模板到目标文件夹
            String language = (String) comboBox.getSelectedItem();
            PersistenceStorage.initCodeTemplateFile(language);
        });

        checkBox.addItemListener(e -> {
            isModified = true;
            PersistenceStorage.settings.setAutoFetchProblems(String.valueOf(checkBox.isSelected()));
        });

        return panel;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("Applying CCI Settings...");
        PersistenceStorage.savePropertiesFile();
    }
}
