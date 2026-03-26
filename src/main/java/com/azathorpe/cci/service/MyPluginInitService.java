package com.azathorpe.cci.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;

@Service
public final class MyPluginInitService {
    public static final Logger LOG = Logger.getInstance(MyPluginInitService.class);
    public MyPluginInitService() {
        System.out.println("MyPluginInitService initialized");
        LOG.info("MyPluginInitService initialized");
    }
}
