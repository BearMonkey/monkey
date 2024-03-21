package org.monkey.oauth2.app.service.impl;

import org.monkey.oauth2.app.client.ResourceFeignClient;
import org.monkey.oauth2.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private ResourceFeignClient resourceClient;
    @Override
    public String getInfos() {
        return resourceClient.getResource().getData().toString();
    }
}
