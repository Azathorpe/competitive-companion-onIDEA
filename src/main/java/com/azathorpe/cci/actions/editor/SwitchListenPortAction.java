package com.azathorpe.cci.actions.editor;

import com.azathorpe.cci.service.SocketService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.ui.Messages;

/**
 * 切换开启监听端口的功能
 * @author Azathorpe
 * @version 1.0
 */
public class SwitchListenPortAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        if(SocketService.isIsRunning()) {
            SocketService.stopServer();
            Messages.showInfoMessage("Stopped the Service", "Listen Port Func Is Stopped..");
            System.out.println("Stopped the Service");
        }
        else {
            SocketService.startServer();
            Messages.showInfoMessage("Start the Service", "Listen Port Func Is Started..");
            System.out.println("Start the Service");
        }
    }
}
