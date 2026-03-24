package com.azathorpe.cci.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

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
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("This is the settings page for CCI plugin.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
    }
}
