package org.monkey.oauth2.app.service.impl;

import org.monkey.oauth2.app.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "test service";
    }
}
