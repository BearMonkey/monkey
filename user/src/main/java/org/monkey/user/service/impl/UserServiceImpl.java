package org.monkey.user.service.impl;

import org.monkey.user.mapper.UserMapper;
import org.monkey.user.pojo.User;
import org.monkey.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
