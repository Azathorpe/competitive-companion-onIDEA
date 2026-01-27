package com.azathorpe.cci.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class AbstractAnAction extends AnAction {
    public AbstractAnAction() {
        super("First Action", "Description of first action", IconLoader.getIcon("/META-INF/pluginIcon__.svg"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Messages.showMessageDialog(anActionEvent.getProject(), "Hello from my plugin!", "Plugin Message", Messages.getInformationIcon());
    }
}
