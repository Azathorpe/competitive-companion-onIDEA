package com.azathorpe.cci.listener

import com.azathorpe.cci.service.SocketService
import com.azathorpe.cci.utils.Infos
import com.azathorpe.cci.utils.PersistentStorage
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class StartUp : ProjectActivity {
    override suspend fun execute(project: Project) {
        PersistentStorage.setBasePath(project.basePath)
        // 加载Infos类
        Infos.updateSettings()
        // 加载Settings
        if (Infos.settings.getAutoFetchProblems() == "true") {
            SocketService.startServer()
        } else {
            SocketService.stopServer()
        }
    }
}