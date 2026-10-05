package com.azathorpe.cci.listener

import com.azathorpe.cci.utils.PersistentStorage
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class StartUp : ProjectActivity {
    override suspend fun execute(project: Project) {
        PersistentStorage.setBasePath(project.basePath)
    }
}