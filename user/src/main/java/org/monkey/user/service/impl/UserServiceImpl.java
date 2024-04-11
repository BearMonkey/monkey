package org.monkey.user.service.impl;

import org.monkey.common.dto.Result;
import org.monkey.user.client.ProductClient;
import org.monkey.user.mapper.UserMapper;
import org.monkey.user.pojo.User;
import org.monkey.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductClient productClient;

    @Override
    public List<User> getAllUser() {
        Result lsit = productClient.lsit();
        log.info("===============================================");
        log.info("{}", lsit);
        log.info("===============================================");
        return userMapper.findAllUser();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
