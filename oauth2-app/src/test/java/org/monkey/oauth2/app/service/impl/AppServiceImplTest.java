package org.monkey.oauth2.app.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.monkey.oauth2.app.service.AppService;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AppServiceImplTest {

    @Autowired
    private AppServiceImpl appService;

    @Test
    public void getInfos() {
        String infos = appService.getInfos();
        System.out.println(infos);
        Assert.assertEquals(infos, "this is infos");
    }
}