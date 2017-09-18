package com.mocentre.tehui.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.system.service.IAreasService;

@Component
@Lazy(false)
public class InitWorker implements InitializingBean {

    @Autowired
    private IAreasService areasService;

    @Override
    public void afterPropertiesSet() throws Exception {
        areasService.getAllAreasToCache();
    }
}
